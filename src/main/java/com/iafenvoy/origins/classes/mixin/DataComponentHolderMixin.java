package com.iafenvoy.origins.classes.mixin;

import com.iafenvoy.origins.classes.accessor.PotionContentsWithStackAccessor;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DataComponentHolder.class)
public interface DataComponentHolderMixin {
    @ModifyReturnValue(method = "get", at = @At("RETURN"))
    private <T> T attackStack1(T original, DataComponentType<? extends T> component) {
        if ((DataComponentHolder) this instanceof ItemStack stack)
            if (original instanceof PotionContents contents)
                ((PotionContentsWithStackAccessor) (Object) contents).originsClasses$setStack(stack);
        return original;
    }
    @ModifyReturnValue(method = "getOrDefault", at = @At("RETURN"))
    private <T> T attackStack2(T original, DataComponentType<? extends T> component) {
        if ((DataComponentHolder) this instanceof ItemStack stack)
            if (original instanceof PotionContents contents)
                ((PotionContentsWithStackAccessor) (Object) contents).originsClasses$setStack(stack);
        return original;
    }
}
