package com.iafenvoy.origins.classes.registry;

import com.iafenvoy.origins.classes.OriginsClasses;
import com.iafenvoy.origins.data.condition.ConditionRegistries;
import com.iafenvoy.origins.data.condition.EntityCondition;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.animal.Animal;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.iafenvoy.origins.data.condition.SimpleConditions.createEntity;

@SuppressWarnings("unused")
public final class OCEntityConditions {
    public static final DeferredRegister<MapCodec<? extends EntityCondition>> REGISTRY = DeferredRegister.create(ConditionRegistries.ENTITY_CONDITION, OriginsClasses.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends EntityCondition>, MapCodec<? extends EntityCondition>> ANIMAL = REGISTRY.register("animal", () -> createEntity(entity -> entity instanceof Animal));
}
