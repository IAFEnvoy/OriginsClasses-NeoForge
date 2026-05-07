package com.iafenvoy.origins.classes.data.power;

import com.iafenvoy.origins.attachment.OriginDataHolder;
import com.iafenvoy.origins.classes.event.ModifyCraftResultEvent;
import com.iafenvoy.origins.classes.registry.OCDataComponents;
import com.iafenvoy.origins.data.condition.ItemCondition;
import com.iafenvoy.origins.data.power.Power;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModifyCraftedFoodPower extends Power {
    public static final MapCodec<ModifyCraftedFoodPower> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            BaseSettings.CODEC.forGetter(Power::getSettings),
            Power.CODEC.fieldOf("modify_food_power").forGetter(ModifyCraftedFoodPower::getModifyFoodPower),
            ModifyCraftResultEvent.CraftingResultType.CODEC.listOf().optionalFieldOf("crafting_result_type", List.of(ModifyCraftResultEvent.CraftingResultType.values())).forGetter(ModifyCraftedFoodPower::getCraftingResultTypes),
            ItemCondition.optionalCodec("item_condition").forGetter(ModifyCraftedFoodPower::getItemCondition)
    ).apply(i, ModifyCraftedFoodPower::new));
    private final Holder<Power> modifyFoodPower;
    private final List<ModifyCraftResultEvent.CraftingResultType> craftingResultTypes;
    private final ItemCondition itemCondition;

    public ModifyCraftedFoodPower(BaseSettings settings, Holder<Power> modifyFoodPower, List<ModifyCraftResultEvent.CraftingResultType> craftingResultTypes, ItemCondition itemCondition) {
        super(settings);
        this.modifyFoodPower = modifyFoodPower;
        this.craftingResultTypes = craftingResultTypes;
        this.itemCondition = itemCondition;
    }

    public Holder<Power> getModifyFoodPower() {
        return this.modifyFoodPower;
    }

    public List<ModifyCraftResultEvent.CraftingResultType> getCraftingResultTypes() {
        return this.craftingResultTypes;
    }

    public ItemCondition getItemCondition() {
        return this.itemCondition;
    }

    @Override
    public @NotNull MapCodec<? extends Power> codec() {
        return CODEC;
    }

    public static ItemStack modify(Player player, ItemStack stack, ModifyCraftResultEvent.CraftingResultType type) {
        List<Holder<Power>> powers = OriginDataHolder.get(player).streamActivePowers(ModifyCraftedFoodPower.class)
                .filter(p -> p.getCraftingResultTypes().contains(type) && p.getItemCondition().test(player.level(), stack))
                .map(ModifyCraftedFoodPower::getModifyFoodPower)
                .toList();
        if (!powers.isEmpty()) stack.set(OCDataComponents.MODIFY_FOOD_POWERS, powers);
        return stack;
    }
}
