package com.iafenvoy.origins.classes.mixin.integration;

import com.iafenvoy.origins.classes.accessor.SlotWithPlayerAccessor;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import vectorwing.farmersdelight.common.block.entity.container.CookingPotResultSlot;

@Pseudo
@Mixin(CookingPotResultSlot.class)
public class FarmersDelightWithPlayerSlotMixin implements SlotWithPlayerAccessor {
    @Shadow(remap = false)
    @Final
    private Player player;

    @Override
    public Player originsClasses$getPlayer() {
        return this.player;
    }
}
