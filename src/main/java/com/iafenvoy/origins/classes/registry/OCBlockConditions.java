package com.iafenvoy.origins.classes.registry;

import com.iafenvoy.origins.classes.OriginsClasses;
import com.iafenvoy.origins.data.condition.BlockCondition;
import com.iafenvoy.origins.data.condition.ConditionRegistries;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.iafenvoy.origins.data.condition.SimpleConditions.createBlock;

@SuppressWarnings("unused")
public final class OCBlockConditions {
    public static final DeferredRegister<MapCodec<? extends BlockCondition>> REGISTRY = DeferredRegister.create(ConditionRegistries.BLOCK_CONDITION, OriginsClasses.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends BlockCondition>, MapCodec<? extends BlockCondition>> HARVESTABLE_CROPS = REGISTRY.register("harvestable_crops", () ->
            createBlock((level, pos) -> {
                BlockState state = level.getBlockState(pos);
                return state.getBlock() instanceof CropBlock crop && crop.isMaxAge(state);
            }));
}
