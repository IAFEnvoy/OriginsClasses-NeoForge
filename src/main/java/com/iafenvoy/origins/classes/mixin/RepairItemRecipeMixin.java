package com.iafenvoy.origins.classes.mixin;

import com.iafenvoy.origins.accessor.PowerCraftingInventory;
import com.iafenvoy.origins.attachment.OriginDataHolder;
import com.iafenvoy.origins.classes.accessor.TransientCraftingContainerAccessor;
import com.iafenvoy.origins.classes.data.power.ModifyCombineRepairDurabilityPower;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RepairItemRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(RepairItemRecipe.class)
public class RepairItemRecipeMixin {
    @ModifyConstant(method = "assemble(Lnet/minecraft/world/item/crafting/CraftingInput;Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/world/item/ItemStack;", constant = @Constant(intValue = 5, ordinal = 0))
    private int doubleRepairDurabilityBonus(int original, CraftingInput container) {
        TransientCraftingContainer tr = ((PowerCraftingInventory) container).origins$getInventory();
        AbstractContainerMenu menu = ((TransientCraftingContainerAccessor) tr).originsClasses$getMenu();
        Player player = null;
        if (menu instanceof CraftingMenu craftingMenu) player = craftingMenu.player;
        else if (menu instanceof InventoryMenu inventoryMenu) player = inventoryMenu.owner;
        if (player == null) return original;
        return Mth.clamp(Mth.floor(OriginDataHolder.get(player).getHelper().modify(ModifyCombineRepairDurabilityPower.class, original)), 0, 100);
    }
}
