package com.iafenvoy.origins.classes;

import com.iafenvoy.jupiter.ConfigManager;
import com.iafenvoy.origins.classes.config.OCClientConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = OriginsClasses.MOD_ID, dist = Dist.CLIENT)
public final class OriginsClassesClient {
    public static boolean INFINITE_TRADER;
    public static boolean MULTI_MINING;

    public OriginsClassesClient() {
        ConfigManager.getInstance().registerConfigHandler(OCClientConfig.INSTANCE);
    }
}
