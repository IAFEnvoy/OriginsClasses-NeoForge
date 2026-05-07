package com.iafenvoy.origins.classes.event;

import com.iafenvoy.origins.attachment.OriginDataHolder;
import com.iafenvoy.origins.classes.config.OCClientConfig;
import com.iafenvoy.origins.classes.data.power.*;
import com.iafenvoy.origins.classes.mixin.accessor.LivingEntityAccessor;
import com.iafenvoy.origins.classes.network.payload.InfiniteTraderS2CPayload;
import com.iafenvoy.origins.classes.registry.OCDataComponents;
import com.iafenvoy.origins.classes.registry.tag.OCEntityTags;
import com.iafenvoy.origins.classes.util.ClientUtils;
import com.iafenvoy.origins.classes.util.CommonUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.TippedArrowItem;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.enchanting.EnchantmentLevelSetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.BonemealEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@EventBusSubscriber
public class PowerEventHandler {
    public static final String POTION_BONUS_TRANSLATION_KEY = "tooltip.origins_classes.potion_bonus";
    public static final String FOOD_TRANSLATION_KEY = "tooltip.origins_classes.food";
    public static final String SATURATION_MODIFIER_TRANSLATION_KEY = "tooltip.origins_classes.saturation_modifier";

    //TamedPotionDiffusal
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPotionGained(MobEffectEvent.Added event) {
        MobEffectInstance effect = event.getEffectInstance();
        LivingEntity owner = event.getEntity();
        Entity source = event.getEffectSource() == null ? owner : event.getEffectSource();
        if (!effect.isAmbient() && OriginDataHolder.get(owner).hasActivePower(TamedPotionDiffusalPower.class)) {
            owner.level().getEntitiesOfClass(LivingEntity.class,
                    owner.getBoundingBox().expandTowards(8F, 2F, 8F).expandTowards(-8F, -2F, -8F),
                    e -> e instanceof OwnableEntity ownable && Objects.equals(ownable.getOwnerUUID(), owner.getUUID())
            ).forEach(e -> e.addEffect(effect, source));
        }
    }

    //ModifyEnchantingLevel
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEnchantmentLevel(EnchantmentLevelSetEvent event) {
        ItemStack stack = event.getItem();
        UUID uuid = stack.get(OCDataComponents.ENCHANTER);
        if (uuid == null) return;
        Player player = event.getLevel().getPlayerByUUID(uuid);
        if (player != null)
            event.setEnchantLevel(Mth.floor(OriginDataHolder.get(player).getHelper().modify(ModifyEnchantingLevelPower.class, event.getEnchantLevel())));
    }

    //ModifyBoneMeal
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBoneMeal(BonemealEvent event) {
        Player player = event.getPlayer();
        if (player == null) return;
        BlockState state = event.getState();
        int count = CommonUtils.rollInt(OriginDataHolder.get(player).getHelper().modify(ModifyBoneMealPower.class, 1.0D), player.getRandom());
        if (count == 0) event.setCanceled(true);
        else if (count > 1 && state.getBlock() instanceof BonemealableBlock fertilizable) {
            BlockPos pos = event.getPos();
            if (event.getLevel() instanceof ServerLevel serverWorld && fertilizable.isValidBonemealTarget(serverWorld, pos, state))
                for (int i = 0; i < count - 1; i++)
                    if (fertilizable.isBonemealSuccess(serverWorld, serverWorld.random, pos, state))
                        fertilizable.performBonemeal(serverWorld, serverWorld.random, pos, state);
        }
    }

    //InfiniteTrade
    @SubscribeEvent
    public static void onInteractEntity(PlayerInteractEvent.EntityInteract event) {
        if (event.getEntity() instanceof ServerPlayer player)
            PacketDistributor.sendToPlayer(player, new InfiniteTraderS2CPayload(event.getTarget().getType().is(OCEntityTags.INFINITE_TRADER) && OriginDataHolder.get(player).hasActivePower(InfiniteTradePower.class)));
    }

    //ModifyEntityLoot
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (event.getSource().isDirect() && event.getEntity() instanceof Player player) {
            LivingEntity target = event.getEntity();
            int amount = CommonUtils.rollInt(OriginDataHolder.get(player).getHelper().modify(ModifyEntityLootPower.class, cp -> cp.getCondition().test(player, target), 1.0F), player.getRandom());
            for (int i = 1; i < amount; ++i)
                ((LivingEntityAccessor) target).invokeDropFromLootTable(event.getSource(), true);
        }
    }

    //ModifyCraftedFood, ModifyCraftResult
    @SubscribeEvent
    public static void onItemCrafted(ModifyCraftResultEvent event) {
        Player player = event.getEntity();
        ItemStack stack = event.getCrafted();
        if (stack.getFoodProperties(player) != null) ModifyCraftedFoodPower.modify(player, stack, event.getType());
        stack = ModifyCraftResultPower.modify(player, stack, event.getType());
        event.setCrafted(stack);
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        Player player = event.getEntity();
        if (player == null) return;
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();

//        if(ClientConfig.CONFIG.showModifyFoodTooltip.get() &&
//            stack.getFoodProperties(player) != null)
//        {
//            List<AttributeModifier> foodModifiers = new ArrayList<>();
//            List<AttributeModifier> saturationModifiers = new ArrayList<>();
//            ModifyFoodPower.getValidPowers(player, stack).stream()
//                .map(ConfiguredPower::getConfiguration)
//                .forEach(config -> {
//                    foodModifiers.addAll(config.foodModifiers().entries());
//                    saturationModifiers.addAll(config.saturationModifiers().entries());
//                });
//            foodModifiers.forEach(mod -> tooltip.add(ClientUtils.modifierTooltip(mod, FOOD_TRANSLATION_KEY)));
//            saturationModifiers.forEach(mod -> tooltip.add(ClientUtils.modifierTooltip(mod, SATURATION_MODIFIER_TRANSLATION_KEY)));
//        }

        if (OCClientConfig.INSTANCE.general.showPotionBonusTooltip.getValue() && (stack.getItem() instanceof PotionItem || stack.getItem() instanceof TippedArrowItem) && stack.has(OCDataComponents.POTION_BONUS))
            tooltip.add(ClientUtils.translate(POTION_BONUS_TRANSLATION_KEY).withStyle(ChatFormatting.BLUE));
    }
}