package com.iafenvoy.origins.classes.mixin.integration;

import com.iafenvoy.origins.classes.registry.OCDataComponents;
import com.iafenvoy.origins.data.power.Power;
import com.iafenvoy.origins.data.power.builtin.modify.ModifyFoodPower;
import com.iafenvoy.origins.util.wrapper.Mutable;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ModifyFoodPower.class)
public class ModifyFoodPowerMixin {
    @Inject(method = "modifyStack", at = @At("RETURN"))
    private static void originsClasses$bindModifyCraftedFoodPowers(Level level, Entity entity, Mutable.Stack input, CallbackInfo ci) {
        List<Holder<Power>> powers = input.get().getOrDefault(OCDataComponents.MODIFY_FOOD_POWERS, List.of());
        powers.stream()
                .map(Holder::value)
                .filter(ModifyFoodPower.class::isInstance)
                .map(ModifyFoodPower.class::cast)
                .filter(p -> p.getItemCondition().test(level, input.get()))
                .forEach(p -> {
                    p.getReplaceStack().ifPresent(stack -> input.set(stack.copy()));
                    p.getItemAction().execute(level, entity, input.toSlotAccess());
                });
    }
}
