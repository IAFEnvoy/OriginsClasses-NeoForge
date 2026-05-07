package com.iafenvoy.origins.classes.mixin;

import com.iafenvoy.origins.classes.accessor.AbstractArrowPotionBonusAccessor;
import com.iafenvoy.origins.classes.accessor.PotionContentsWithStackAccessor;
import com.iafenvoy.origins.classes.registry.OCDataComponents;
import com.iafenvoy.origins.classes.util.ClericHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(Arrow.class)
public class ArrowMixin {

    @Inject(method = "setPotionContents", at = @At("TAIL"))
    private void originsClasses$initFromAdditionalPotionNbt(PotionContents potionContents, CallbackInfo ci) {
        ((AbstractArrowPotionBonusAccessor) this).originsClasses$setPotionBonus(Objects.requireNonNullElse(((PotionContentsWithStackAccessor) (Object) potionContents).originsClasses$getStack(), ItemStack.EMPTY).has(OCDataComponents.POTION_BONUS));
    }

    @ModifyArg(method = "doPostHurtEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z"), index = 0)
    private MobEffectInstance originsClasses$handlePotionBonus(MobEffectInstance effect) {
        return ((AbstractArrowPotionBonusAccessor) this).originsClasses$hasPotionBonus() ? ClericHelper.applyPotionBonus(effect) : effect;
    }
}
