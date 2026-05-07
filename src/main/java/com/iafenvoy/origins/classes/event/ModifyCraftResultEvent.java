package com.iafenvoy.origins.classes.event;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ModifyCraftResultEvent extends PlayerEvent {
    private ItemStack crafted;
    private final CraftingResultType type;

    public ModifyCraftResultEvent(Player player, ItemStack crafted, CraftingResultType type) {
        super(player);
        this.crafted = crafted;
        this.type = type;
    }

    public void setCrafted(ItemStack crafted) {
        this.crafted = crafted;
    }

    public ItemStack getCrafted() {
        return this.crafted;
    }

    public CraftingResultType getType() {
        return this.type;
    }

    public enum CraftingResultType implements StringRepresentable {
        CRAFTING, SMELTING, COOKING_POT, SKILLET, TETRA;
        public static final Codec<CraftingResultType> CODEC = StringRepresentable.fromEnum(CraftingResultType::values);

        @Override
        public @NotNull String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
