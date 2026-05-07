package com.iafenvoy.origins.classes.mixin;

import com.iafenvoy.origins.classes.accessor.AbstractArrowPotionBonusAccessor;
import com.iafenvoy.origins.classes.registry.OCDataComponents;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin implements AbstractArrowPotionBonusAccessor {
    @Unique
    private static final String POTION_BONUS_NBT_KEY = "OriginsClassesPotionBonus";
    @Unique
    private boolean originsClasses$hasPotionBonus;

    @Override
    public void originsClasses$setPotionBonus(boolean b) {
        this.originsClasses$hasPotionBonus = b;
    }

    @Override
    public boolean originsClasses$hasPotionBonus() {
        return this.originsClasses$hasPotionBonus;
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void originsClasses$writeAdditionalPotionNbt(CompoundTag nbt, CallbackInfo ci) {
        if (this.originsClasses$hasPotionBonus) nbt.putBoolean(POTION_BONUS_NBT_KEY, true);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void originsClasses$readAdditionalPotionNbt(CompoundTag nbt, CallbackInfo ci) {
        this.originsClasses$hasPotionBonus = nbt.getBoolean(POTION_BONUS_NBT_KEY);
    }

    @ModifyReturnValue(method = "getPickupItem", at = @At("RETURN"))
    private ItemStack originsClasses$storeAdditionalPotionNbt(ItemStack original) {
        if (this.originsClasses$hasPotionBonus) original.set(OCDataComponents.POTION_BONUS, Unit.INSTANCE);
        return original;
    }
}
