package com.iafenvoy.origins.classes.mixin.integration;

import com.iafenvoy.origins.classes.accessor.SlotWithPlayerAccessor;
import com.iafenvoy.origins.classes.event.ModifyCraftResultEvent;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SlotItemHandler.class)
public class SlotItemHandlerMixin {
    @ModifyReturnValue(method = "remove", at = @At("RETURN"))
    public ItemStack onRemove(ItemStack original) {
        return this.originsClasses$process(original);
    }

    @ModifyReturnValue(method = "getItem", at = @At("RETURN"))
    public ItemStack onGetItem(ItemStack original) {
        return this.originsClasses$process(original);
    }

    @Unique
    private ItemStack originsClasses$process(ItemStack original) {
        if (!(this instanceof SlotWithPlayerAccessor accessor)) return original;
        ModifyCraftResultEvent event = new ModifyCraftResultEvent(accessor.originsClasses$getPlayer(), original.copy(), ModifyCraftResultEvent.CraftingResultType.CRAFTING);
        NeoForge.EVENT_BUS.post(event);
        return event.getCrafted();
    }
}
