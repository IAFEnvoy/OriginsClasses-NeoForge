package com.iafenvoy.origins.classes;

import com.iafenvoy.origins.classes.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(OriginsClasses.MOD_ID)
public final class OriginsClasses {
    public static final String MOD_ID = "origins_classes";
    public static final Logger LOGGER = LogManager.getLogger();

    public OriginsClasses(IEventBus bus) {
        OCBiEntityConditions.REGISTRY.register(bus);
        OCBlockConditions.REGISTRY.register(bus);
        OCEntityConditions.REGISTRY.register(bus);
        OCItemConditions.REGISTRY.register(bus);
        OCPowers.REGISTRY.register(bus);
    }

    public static ResourceLocation identifier(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
