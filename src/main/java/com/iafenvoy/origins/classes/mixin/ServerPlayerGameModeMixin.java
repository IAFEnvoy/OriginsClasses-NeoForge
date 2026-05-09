package com.iafenvoy.origins.classes.mixin;

import com.iafenvoy.origins.classes.data.power.MultiMinePower;
import com.iafenvoy.origins.classes.network.payload.MultiMiningS2CPayload;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerGameMode.class)
public abstract class ServerPlayerGameModeMixin {
    @Shadow
    protected ServerLevel level;
    @Final
    @Shadow
    protected ServerPlayer player;
    @Unique
    private BlockState originsClasses$justMinedState;
    @Unique
    private boolean originsClasses$performingMultiMine = false;
    @Unique
    private boolean originsClasses$wasSneakingWhenBlockBreakStarted = false;

    @Shadow
    public abstract void destroyAndAck(BlockPos pPos, int i, String reason);

    @Inject(method = "handleBlockBreakAction", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getDestroyProgress(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)F", ordinal = 0))
    private void saveSneakingState(BlockPos pPos, ServerboundPlayerActionPacket.Action pAction, Direction pDirection, int p_215123_, int p_215124_, CallbackInfo ci) {
        this.originsClasses$wasSneakingWhenBlockBreakStarted = this.player.isShiftKeyDown();
        PacketDistributor.sendToPlayer(this.player, new MultiMiningS2CPayload(!this.originsClasses$wasSneakingWhenBlockBreakStarted));
    }

    @Inject(method = "destroyAndAck", at = @At("HEAD"))
    private void saveBlockStateForMultiMine(BlockPos pos, int i, String reason, CallbackInfo ci) {
        this.originsClasses$justMinedState = this.level.getBlockState(pos);
    }

    @Inject(method = "destroyAndAck", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayerGameMode;debugLogging(Lnet/minecraft/core/BlockPos;ZILjava/lang/String;)V", ordinal = 0))
    private void multiMinePower(BlockPos pos, int i, String reason, CallbackInfo ci) {
        if (!this.originsClasses$wasSneakingWhenBlockBreakStarted && !this.originsClasses$performingMultiMine) {
            this.originsClasses$performingMultiMine = true;
            ItemStack tool = this.player.getMainHandItem().copy();
            for (BlockPos bp : MultiMinePower.apply(this.player, pos, this.originsClasses$justMinedState)) {
                this.destroyAndAck(bp, i, reason);
                if (!this.player.getMainHandItem().is(tool.getItem())) break;
            }
            this.originsClasses$performingMultiMine = false;
        }
    }
}
