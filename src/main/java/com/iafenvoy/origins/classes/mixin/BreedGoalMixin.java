package com.iafenvoy.origins.classes.mixin;

import com.iafenvoy.origins.classes.data.power.ModifyBreedingPower;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(BreedGoal.class)
public class BreedGoalMixin {
    @Shadow
    @Final
    protected Animal animal;
    @Shadow
    @Nullable
    protected Animal partner;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/BreedGoal;breed()V"), cancellable = true)
    private void modifyBreeding(CallbackInfo ci) {
        ServerPlayer player = this.animal.getLoveCause();
        if (player != null && this.partner != null) {
            int amount = ModifyBreedingPower.getBreedAmount(player, this.animal, this.partner);
            if (amount < 1) {
                this.animal.setAge(6000);
                this.partner.setAge(6000);
                this.animal.resetLove();
                this.partner.resetLove();
                ci.cancel();
            } else if (amount > 1) for (int i = 1; i < amount; i++)
                this.animal.spawnChildFromBreeding(player.serverLevel(), this.partner);
        }
    }
}
