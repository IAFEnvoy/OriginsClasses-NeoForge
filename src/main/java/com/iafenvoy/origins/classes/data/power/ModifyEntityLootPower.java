package com.iafenvoy.origins.classes.data.power;

import com.iafenvoy.origins.data._common.helper.ModifierPowerHelper;
import com.iafenvoy.origins.data.condition.BiEntityCondition;
import com.iafenvoy.origins.data.power.Power;
import com.iafenvoy.origins.util.codec.CombinedCodecs;
import com.iafenvoy.origins.util.math.Modifier;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModifyEntityLootPower extends Power implements ModifierPowerHelper {
    public static final MapCodec<ModifyEntityLootPower> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            BaseSettings.CODEC.forGetter(Power::getSettings),
            CombinedCodecs.MODIFIER.fieldOf("modifier").forGetter(ModifyEntityLootPower::getModifier),
            BiEntityCondition.optionalCodec("bientity_condition").forGetter(ModifyEntityLootPower::getCondition)
    ).apply(i, ModifyEntityLootPower::new));
    private final List<Modifier> modifier;
    private final BiEntityCondition condition;

    public ModifyEntityLootPower(BaseSettings settings, List<Modifier> modifier, BiEntityCondition condition) {
        super(settings);
        this.modifier = modifier;
        this.condition = condition;
    }

    @Override
    public List<Modifier> getModifier() {
        return this.modifier;
    }

    public BiEntityCondition getCondition() {
        return this.condition;
    }

    @Override
    public @NotNull MapCodec<? extends Power> codec() {
        return CODEC;
    }
}
