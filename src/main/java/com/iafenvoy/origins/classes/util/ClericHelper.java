package com.iafenvoy.origins.classes.util;

import net.minecraft.world.effect.MobEffectInstance;

public class ClericHelper {
    public static MobEffectInstance applyPotionBonus(MobEffectInstance effect) {
        boolean instant = effect.getEffect().value().isInstantenous();
        return new MobEffectInstance(effect.getEffect(), instant ? effect.getDuration() : effect.getDuration() * 2, instant ? effect.getAmplifier() + 1 : effect.getAmplifier(), effect.isAmbient(), effect.isVisible(), effect.showIcon());
    }
}
