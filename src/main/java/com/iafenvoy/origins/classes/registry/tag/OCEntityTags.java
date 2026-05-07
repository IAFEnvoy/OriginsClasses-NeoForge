package com.iafenvoy.origins.classes.registry.tag;

import com.iafenvoy.origins.classes.OriginsClasses;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class OCEntityTags {
    public static final TagKey<EntityType<?>> INFINITE_TRADER = tag(OriginsClasses.identifier("infinite_trader"));

    private static TagKey<EntityType<?>> tag(ResourceLocation id) {
        return TagKey.create(Registries.ENTITY_TYPE, id);
    }
}
