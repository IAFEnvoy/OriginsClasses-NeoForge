package com.iafenvoy.origins.classes.mixin;

import com.iafenvoy.origins.classes.registry.OCDataComponents;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.neoforged.neoforge.common.brewing.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("UnstableApiUsage")
@Mixin(BrewingRecipeRegistry.class)
public class BrewingRecipeRegistryMixin {
    @ModifyReturnValue(method = "getOutput", at = @At("RETURN"), remap = false)
    private static ItemStack originsClasses$handleAdditionalPotionNbt(ItemStack output, ItemStack input, ItemStack ingredient) {
        if (output.getItem() instanceof PotionItem && input.has(OCDataComponents.POTION_BONUS))
            output.set(OCDataComponents.POTION_BONUS, Unit.INSTANCE);
        return output;
    }
}
