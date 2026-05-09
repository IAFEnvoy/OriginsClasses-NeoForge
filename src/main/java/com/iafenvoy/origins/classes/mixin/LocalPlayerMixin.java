package com.iafenvoy.origins.classes.mixin;

import com.iafenvoy.origins.classes.data.power.ModifySpeedOnItemUsePower;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import javax.annotation.Nonnull;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {
    @Override
    @Shadow
    public abstract @Nonnull InteractionHand getUsedItemHand();

    public LocalPlayerMixin(ClientLevel world, GameProfile profile) {
        super(world, profile);
    }

    @ModifyConstant(method = "aiStep", constant = @Constant(floatValue = 0.2F))
    private float modifyItemUseSlowdown(float originalSlowdown) {
        return ModifySpeedOnItemUsePower.modifySlowDown(this, originalSlowdown);
    }
}