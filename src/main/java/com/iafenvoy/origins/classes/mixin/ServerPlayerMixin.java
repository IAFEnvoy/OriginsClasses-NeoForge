package com.iafenvoy.origins.classes.mixin;

import com.iafenvoy.origins.attachment.OriginDataHolder;
import com.iafenvoy.origins.classes.data.power.NoSprintExhaustionPower;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends LivingEntity {
    protected ServerPlayerMixin(EntityType<? extends LivingEntity> type, Level world) {
        super(type, world);
    }

    @ModifyConstant(method = "checkMovementStatistics", constant = @Constant(floatValue = 0.1F, ordinal = 0))
    private float originsClasses$removeSprintExaustion(float orginal) {
        return OriginDataHolder.get(this).hasActivePower(NoSprintExhaustionPower.class) ? 0 : orginal;
    }
}
