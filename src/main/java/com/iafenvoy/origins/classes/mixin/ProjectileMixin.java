package com.iafenvoy.origins.classes.mixin;

import com.iafenvoy.origins.attachment.OriginDataHolder;
import com.iafenvoy.origins.classes.data.power.ModifyProjectileDivergencePower;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public class ProjectileMixin {
    @Unique
    private Entity originsClasses$cachedShooter;

    @Inject(method = "shootFromRotation", at = @At("HEAD"))
    private void originsClasses$cacheShooter(Entity entity, float p, float y, float r, float s, float d, CallbackInfo ci) {
        this.originsClasses$cachedShooter = entity;
    }

    @ModifyVariable(method = "shoot", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    private float originsClasses$modifyDivergence(float oldDivergence) {
        if (this.originsClasses$cachedShooter != null) {
            float newDivergence = OriginDataHolder.get(this.originsClasses$cachedShooter).getHelper().modify(ModifyProjectileDivergencePower.class, oldDivergence);
            this.originsClasses$cachedShooter = null;
            return newDivergence;
        }
        return oldDivergence;
    }
}