package com.iafenvoy.origins.classes.mixin;

import com.iafenvoy.origins.attachment.OriginDataHolder;
import com.iafenvoy.origins.classes.data.power.ModifyFurnaceXPPower;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin extends BaseContainerBlockEntity {
    @Unique
    private static ServerPlayer CACHED_PLAYER;
    @Unique
    private static BlockPos CACHED_BLOCK_POS;

    protected AbstractFurnaceBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "awardUsedRecipesAndPopExperience", at = @At("HEAD"))
    private void originsClasses$cachePlayerAndPos(ServerPlayer player, CallbackInfo ci) {
        CACHED_PLAYER = player;
        CACHED_BLOCK_POS = this.worldPosition;
    }

    @Inject(method = "awardUsedRecipesAndPopExperience", at = @At("TAIL"))
    private void originsClasses$resetPlayerAndPos(ServerPlayer player, CallbackInfo ci) {
        CACHED_PLAYER = null;
        CACHED_BLOCK_POS = null;
    }

    @ModifyVariable(method = "createExperience", at = @At("HEAD"), argsOnly = true)
    private static float originsClasses$modifyRecipeXp(float xp) {
        if (CACHED_PLAYER != null && CACHED_BLOCK_POS != null)
            return OriginDataHolder.get(CACHED_PLAYER).getHelper().modify(ModifyFurnaceXPPower.class, cp -> cp.getBlockCondition().test(CACHED_PLAYER.level(), CACHED_BLOCK_POS), xp);
        return xp;
    }
}
