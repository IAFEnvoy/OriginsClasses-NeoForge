package com.iafenvoy.origins.classes.mixin;

import com.iafenvoy.origins.classes.accessor.TransientCraftingContainerAccessor;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.TransientCraftingContainer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TransientCraftingContainer.class)
public class TransientCraftingContainerMixin implements TransientCraftingContainerAccessor {
    @Final
    @Shadow
    private AbstractContainerMenu menu;

    @Override
    public AbstractContainerMenu originsClasses$getMenu() {
        return this.menu;
    }
}
