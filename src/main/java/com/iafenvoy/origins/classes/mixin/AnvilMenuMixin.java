package com.iafenvoy.origins.classes.mixin;

import com.iafenvoy.origins.attachment.OriginDataHolder;
import com.iafenvoy.origins.classes.data.power.ModifyCombineRepairDurabilityPower;
import com.iafenvoy.origins.classes.data.power.ModifyRepairMaterialCostPower;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
    public AnvilMenuMixin(MenuType<?> type, int syncId, Inventory playerInventory, ContainerLevelAccess context) {
        super(type, syncId, playerInventory, context);
    }

    @ModifyConstant(method = "createResult", constant = @Constant(intValue = 4, ordinal = 0))
    private int originsClasses$modfifyRepairMaterialCost0(int original) {
        return Mth.clamp(Mth.floor(OriginDataHolder.get(this.player).getHelper().modify(ModifyRepairMaterialCostPower.class, original)), 1, Integer.MAX_VALUE);
    }

    @ModifyConstant(method = "createResult", constant = @Constant(intValue = 4, ordinal = 1))
    private int originsClasses$modfifyRepairMaterialCost1(int original) {
        return Mth.clamp(Mth.floor(OriginDataHolder.get(this.player).getHelper().modify(ModifyRepairMaterialCostPower.class, original)), 1, Integer.MAX_VALUE);
    }

    @ModifyConstant(method = "createResult", constant = @Constant(intValue = 12, ordinal = 0))
    private int originsClasses$modifyCombineRepairDurabilityBonus(int original) {
        return Mth.clamp(Mth.floor(OriginDataHolder.get(this.player).getHelper().modify(ModifyCombineRepairDurabilityPower.class, original)), 0, 100);
    }
}