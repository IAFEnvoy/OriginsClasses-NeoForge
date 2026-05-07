package com.iafenvoy.origins.classes.mixin.integration;

import com.iafenvoy.origins.classes.registry.OCDataComponents;
import com.iafenvoy.origins.data.power.Power;
import com.iafenvoy.origins.data.power.builtin.modify.ModifyFoodPower;
import com.iafenvoy.origins.util.wrapper.Mutable;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.stream.Stream;

@Mixin(ModifyFoodPower.class)
public class ModifyFoodPowerMixin {
    @ModifyExpressionValue(method = "modifyStack", at = @At(value = "INVOKE", target = "Lcom/iafenvoy/origins/attachment/OriginDataHolder;streamActivePowers(Ljava/lang/Class;)Ljava/util/stream/Stream;"))
    private static Stream<ModifyFoodPower> originsClasses$bindModifyCraftedFoodPowers(@NotNull Stream<ModifyFoodPower> original, @Local(argsOnly = true) Mutable.Stack input) {
        List<Holder<Power>> powers = input.get().getOrDefault(OCDataComponents.MODIFY_FOOD_POWERS, List.of());
        if (powers.isEmpty()) return original;
        return Stream.concat(original, powers.stream().map(Holder::value).filter(ModifyFoodPower.class::isInstance).map(ModifyFoodPower.class::cast));
    }
}
