package com.iafenvoy.origins.classes.registry;

import com.iafenvoy.origins.classes.OriginsClasses;
import com.iafenvoy.origins.data.power.Power;
import com.iafenvoy.origins.util.codec.AutoIgnoreListCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Unit;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.UUID;

public final class OCDataComponents {
    public static final DeferredRegister<DataComponentType<?>> REGISTRY = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, OriginsClasses.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<UUID>> ENCHANTER = REGISTRY.register("enchanter", () -> DataComponentType.<UUID>builder().persistent(UUIDUtil.CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<Holder<Power>>>> MODIFY_FOOD_POWERS = REGISTRY.register("modify_food_powers", () -> DataComponentType.<List<Holder<Power>>>builder().persistent(new AutoIgnoreListCodec<>(Power.CODEC)).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> POTION_BONUS = REGISTRY.register("potion_bonus", () -> DataComponentType.<Unit>builder().persistent(Unit.CODEC).build());
}
