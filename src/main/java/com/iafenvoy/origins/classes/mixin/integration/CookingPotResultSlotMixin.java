package com.iafenvoy.origins.classes.mixin.integration;

import com.iafenvoy.origins.classes.event.ModifyCraftResultEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import vectorwing.farmersdelight.common.block.entity.container.CookingPotResultSlot;

import javax.annotation.Nonnull;

@Pseudo
@Mixin(CookingPotResultSlot.class)
public class CookingPotResultSlotMixin extends SlotItemHandler {
    @Shadow
    @Final
    private Player player;

    public CookingPotResultSlotMixin(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    @Nonnull
    public ItemStack remove(int amount) {
        ModifyCraftResultEvent event = new ModifyCraftResultEvent(this.player, super.remove(amount).copy(), ModifyCraftResultEvent.CraftingResultType.COOKING_POT);
        NeoForge.EVENT_BUS.post(event);
        return event.getCrafted();
    }

    @Override
    @Nonnull
    public ItemStack getItem() {
        ModifyCraftResultEvent event = new ModifyCraftResultEvent(this.player, super.getItem().copy(), ModifyCraftResultEvent.CraftingResultType.COOKING_POT);
        NeoForge.EVENT_BUS.post(event);
        return event.getCrafted();
    }

}
