package com.iafenvoy.origins.classes.mixin.integration;

import com.iafenvoy.origins.classes.event.ModifyCraftResultEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import vectorwing.farmersdelight.common.item.SkilletItem;

@Pseudo
@Mixin(SkilletItem.class)
public class SkilletItemMixin {
    @Unique
    private static Player originsClasses$player;

    @Inject(method = "lambda$finishUsingItem$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/crafting/CampfireCookingRecipe;assemble(Lnet/minecraft/world/item/crafting/SingleRecipeInput;Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/world/item/ItemStack;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void storeCraftResult(ItemStack cookingStack, Level level, Player player, ItemStack stack, RecipeHolder<CampfireCookingRecipe> recipe, CallbackInfo ci) {
        originsClasses$player = player;
    }

    @ModifyVariable(method = "lambda$finishUsingItem$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;add(Lnet/minecraft/world/item/ItemStack;)Z"), name = "resultStack")
    private static ItemStack modifyCraftResult(ItemStack resultStack) {
        ModifyCraftResultEvent event = new ModifyCraftResultEvent(originsClasses$player, resultStack, ModifyCraftResultEvent.CraftingResultType.SKILLET);
        NeoForge.EVENT_BUS.post(event);
        return event.getCrafted();
    }
}
