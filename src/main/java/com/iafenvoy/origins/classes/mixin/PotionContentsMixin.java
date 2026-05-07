package com.iafenvoy.origins.classes.mixin;

import com.iafenvoy.origins.classes.accessor.PotionContentsWithStackAccessor;
import com.iafenvoy.origins.classes.registry.OCDataComponents;
import com.iafenvoy.origins.classes.util.ClericHelper;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PotionContents.class)
public class PotionContentsMixin implements PotionContentsWithStackAccessor {
    @Unique
    @Nullable
    private ItemStack originsClasses$stack;

    @Override
    public void originsClasses$setStack(ItemStack stack) {
        this.originsClasses$stack = stack;
    }

    @Override
    public ItemStack originsClasses$getStack() {
        return this.originsClasses$stack;
    }

    @ModifyReturnValue(method = "getAllEffects", at = @At("RETURN"))
    private Iterable<MobEffectInstance> applyClericPotion(Iterable<MobEffectInstance> original) {
        if (this.originsClasses$stack != null && this.originsClasses$stack.has(OCDataComponents.POTION_BONUS))
            original.forEach(ClericHelper::applyPotionBonus);
        return original;
    }
}
