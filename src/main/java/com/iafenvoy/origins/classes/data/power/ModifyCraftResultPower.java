package com.iafenvoy.origins.classes.data.power;

import com.iafenvoy.origins.attachment.OriginDataHolder;
import com.iafenvoy.origins.classes.event.ModifyCraftResultEvent;
import com.iafenvoy.origins.data.action.ItemAction;
import com.iafenvoy.origins.data.condition.ItemCondition;
import com.iafenvoy.origins.data.power.Power;
import com.iafenvoy.origins.util.wrapper.Mutable;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModifyCraftResultPower extends Power {
    public static final MapCodec<ModifyCraftResultPower> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            BaseSettings.CODEC.forGetter(Power::getSettings),
            ModifyCraftResultEvent.CraftingResultType.CODEC.listOf().optionalFieldOf("crafting_result_type", List.of(ModifyCraftResultEvent.CraftingResultType.values())).forGetter(ModifyCraftResultPower::getCraftingResultTypes),
            ItemCondition.optionalCodec("item_condition").forGetter(ModifyCraftResultPower::getItemCondition),
            ItemAction.optionalCodec("item_action").forGetter(ModifyCraftResultPower::getItemAction)
    ).apply(i, ModifyCraftResultPower::new));
    private final List<ModifyCraftResultEvent.CraftingResultType> craftingResultTypes;
    private final ItemCondition itemCondition;
    private final ItemAction itemAction;

    public ModifyCraftResultPower(BaseSettings settings, List<ModifyCraftResultEvent.CraftingResultType> craftingResultTypes, ItemCondition itemCondition, ItemAction itemAction) {
        super(settings);
        this.craftingResultTypes = craftingResultTypes;
        this.itemCondition = itemCondition;
        this.itemAction = itemAction;
    }

    public List<ModifyCraftResultEvent.CraftingResultType> getCraftingResultTypes() {
        return this.craftingResultTypes;
    }

    public ItemCondition getItemCondition() {
        return this.itemCondition;
    }

    public ItemAction getItemAction() {
        return this.itemAction;
    }

    @Override
    public @NotNull MapCodec<? extends Power> codec() {
        return CODEC;
    }

    public static ItemStack modify(Player player, ItemStack stack, ModifyCraftResultEvent.CraftingResultType type) {
        Mutable.Stack mutable = Mutable.stack(stack);
        OriginDataHolder.get(player).getHelper().execute(ModifyCraftResultPower.class,
                p -> p.craftingResultTypes.contains(type) && p.itemCondition.test(player.level(), stack),
                p -> p.itemAction.execute(player.level(), player, mutable.toSlotAccess()));
        return mutable.get();
    }
}
