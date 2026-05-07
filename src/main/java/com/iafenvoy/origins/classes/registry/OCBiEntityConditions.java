package com.iafenvoy.origins.classes.registry;

import com.iafenvoy.origins.classes.OriginsClasses;
import com.iafenvoy.origins.classes.data.condition.YawDiffCondition;
import com.iafenvoy.origins.data.condition.BiEntityCondition;
import com.iafenvoy.origins.data.condition.ConditionRegistries;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("unused")
public final class OCBiEntityConditions {
    public static final DeferredRegister<MapCodec<? extends BiEntityCondition>> REGISTRY = DeferredRegister.create(ConditionRegistries.BI_ENTITY_CONDITION, OriginsClasses.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends BiEntityCondition>, MapCodec<? extends BiEntityCondition>> YAW_DIFF = REGISTRY.register("yaw_diff", () -> YawDiffCondition.CODEC);
}
