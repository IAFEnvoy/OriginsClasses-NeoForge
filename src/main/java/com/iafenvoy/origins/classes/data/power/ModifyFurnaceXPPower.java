package com.iafenvoy.origins.classes.data.power;

import com.iafenvoy.origins.data._common.helper.ModifierPowerHelper;
import com.iafenvoy.origins.data.condition.BlockCondition;
import com.iafenvoy.origins.data.power.Power;
import com.iafenvoy.origins.util.codec.CombinedCodecs;
import com.iafenvoy.origins.util.math.Modifier;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModifyFurnaceXPPower extends Power implements ModifierPowerHelper {
    public static final MapCodec<ModifyFurnaceXPPower> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            BaseSettings.CODEC.forGetter(Power::getSettings),
            CombinedCodecs.MODIFIER.fieldOf("modifier").forGetter(ModifyFurnaceXPPower::getModifier),
            BlockCondition.optionalCodec("block_condition").forGetter(ModifyFurnaceXPPower::getBlockCondition)
    ).apply(i, ModifyFurnaceXPPower::new));
    private final List<Modifier> modifier;
    private final BlockCondition blockCondition;

    public ModifyFurnaceXPPower(BaseSettings settings, List<Modifier> modifier, BlockCondition blockCondition) {
        super(settings);
        this.modifier = modifier;
        this.blockCondition = blockCondition;
    }

    @Override
    public List<Modifier> getModifier() {
        return this.modifier;
    }

    public BlockCondition getBlockCondition() {
        return this.blockCondition;
    }

    @Override
    public @NotNull MapCodec<? extends Power> codec() {
        return CODEC;
    }
}
