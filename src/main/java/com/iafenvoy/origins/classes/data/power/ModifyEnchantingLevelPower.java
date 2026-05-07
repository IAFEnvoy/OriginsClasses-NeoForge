package com.iafenvoy.origins.classes.data.power;

import com.iafenvoy.origins.data._common.helper.ModifierPowerHelper;
import com.iafenvoy.origins.data.power.Power;
import com.iafenvoy.origins.util.codec.CombinedCodecs;
import com.iafenvoy.origins.util.math.Modifier;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModifyEnchantingLevelPower extends Power implements ModifierPowerHelper {
    public static final MapCodec<ModifyEnchantingLevelPower> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            BaseSettings.CODEC.forGetter(Power::getSettings),
            CombinedCodecs.MODIFIER.fieldOf("modifier").forGetter(ModifyEnchantingLevelPower::getModifier)
    ).apply(i, ModifyEnchantingLevelPower::new));
    private final List<Modifier> modifier;

    public ModifyEnchantingLevelPower(BaseSettings settings, List<Modifier> modifier) {
        super(settings);
        this.modifier = modifier;
    }

    @Override
    public List<Modifier> getModifier() {
        return this.modifier;
    }

    @Override
    public @NotNull MapCodec<? extends Power> codec() {
        return CODEC;
    }
}
