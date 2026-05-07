package com.iafenvoy.origins.classes.data.condition;

import com.iafenvoy.origins.classes.util.CommonUtils;
import com.iafenvoy.origins.data.condition.BiEntityCondition;
import com.iafenvoy.origins.util.math.Comparison;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public record YawDiffCondition(Comparison comparison) implements BiEntityCondition {
    public static final MapCodec<YawDiffCondition> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Comparison.CODEC.forGetter(YawDiffCondition::comparison)
    ).apply(i, YawDiffCondition::new));

    @Override
    public @NotNull MapCodec<? extends BiEntityCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@NotNull Entity source, @NotNull Entity target) {
        return this.comparison.compare(CommonUtils.calcYawDiff(source, target));
    }
}
