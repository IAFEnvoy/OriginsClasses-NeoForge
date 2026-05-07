package com.iafenvoy.origins.classes.data.power;

import com.iafenvoy.origins.attachment.OriginDataHolder;
import com.iafenvoy.origins.data._common.helper.ModifierPowerHelper;
import com.iafenvoy.origins.data.condition.ItemCondition;
import com.iafenvoy.origins.data.power.Power;
import com.iafenvoy.origins.util.codec.CombinedCodecs;
import com.iafenvoy.origins.util.math.Modifier;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModifySpeedOnItemUsePower extends Power implements ModifierPowerHelper {
    public static final MapCodec<ModifySpeedOnItemUsePower> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            BaseSettings.CODEC.forGetter(Power::getSettings),
            CombinedCodecs.MODIFIER.fieldOf("modifier").forGetter(ModifySpeedOnItemUsePower::getModifier),
            ItemCondition.optionalCodec("item_condition").forGetter(ModifySpeedOnItemUsePower::getItemCondition)
    ).apply(i, ModifySpeedOnItemUsePower::new));
    private final List<Modifier> modifier;
    private final ItemCondition itemCondition;

    public ModifySpeedOnItemUsePower(BaseSettings settings, List<Modifier> modifier, ItemCondition itemCondition) {
        super(settings);
        this.modifier = modifier;
        this.itemCondition = itemCondition;
    }

    @Override
    public List<Modifier> getModifier() {
        return this.modifier;
    }

    public ItemCondition getItemCondition() {
        return this.itemCondition;
    }

    @Override
    public @NotNull MapCodec<? extends Power> codec() {
        return CODEC;
    }

    public static float modifySlowDown(Player player, float slowdownMultiplier) {
        ItemStack stack = player.getItemInHand(player.getUsedItemHand());
        return Mth.clamp(OriginDataHolder.get(player).getHelper().modify(ModifySpeedOnItemUsePower.class, p -> p.itemCondition.test(player.level(), stack), slowdownMultiplier), 0.0F, 1.0F);
    }
}
