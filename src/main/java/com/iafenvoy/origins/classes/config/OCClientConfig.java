package com.iafenvoy.origins.classes.config;

import com.iafenvoy.jupiter.config.container.AutoInitConfigContainer;
import com.iafenvoy.jupiter.config.entry.BooleanEntry;
import com.iafenvoy.origins.classes.OriginsClasses;
import net.minecraft.resources.ResourceLocation;

public class OCClientConfig extends AutoInitConfigContainer {
    public static final OCClientConfig INSTANCE = new OCClientConfig();
    public final General general = new General();

    public OCClientConfig() {
        super(ResourceLocation.fromNamespaceAndPath(OriginsClasses.MOD_ID, "client"), "config.origins_classes.title", "./config/origins_classes.json");
    }

    public static class General extends AutoInitConfigCategoryBase {
        public final BooleanEntry showModifyFoodTooltip = BooleanEntry.builder("config.origins_classes.show_modify_food_tooltip", true).key("modify_food_tooltip").build();
        public final BooleanEntry showPotionBonusTooltip = BooleanEntry.builder("config.origins_classes.show_potion_bonus_tooltip", true).key("potion_bonus_tooltip").build();

        public General() {
            super("general", "config.origins_classes.general.title");
        }
    }
}
