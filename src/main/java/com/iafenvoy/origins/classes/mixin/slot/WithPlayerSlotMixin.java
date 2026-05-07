package com.iafenvoy.origins.classes.mixin.slot;

import com.iafenvoy.origins.classes.accessor.SlotWithPlayerAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.ResultSlot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ResultSlot.class, FurnaceResultSlot.class})
public class WithPlayerSlotMixin implements SlotWithPlayerAccessor {
    @Shadow(remap = false)
    @Final
    private Player player;

    @Override
    public Player originsClasses$getPlayer() {
        return this.player;
    }
}
