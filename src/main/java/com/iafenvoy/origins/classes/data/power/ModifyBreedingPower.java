package com.iafenvoy.origins.classes.data.power;

import com.iafenvoy.origins.attachment.OriginDataHolder;
import com.iafenvoy.origins.classes.util.CommonUtils;
import com.iafenvoy.origins.data._common.helper.ModifierPowerHelper;
import com.iafenvoy.origins.data.action.BiEntityAction;
import com.iafenvoy.origins.data.action.EntityAction;
import com.iafenvoy.origins.data.condition.BiEntityCondition;
import com.iafenvoy.origins.data.power.Power;
import com.iafenvoy.origins.util.codec.CombinedCodecs;
import com.iafenvoy.origins.util.math.Modifier;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModifyBreedingPower extends Power implements ModifierPowerHelper {
    public static final MapCodec<ModifyBreedingPower> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            BaseSettings.CODEC.forGetter(Power::getSettings),
            CombinedCodecs.MODIFIER.fieldOf("modifier").forGetter(ModifyBreedingPower::getModifier),
            BiEntityCondition.optionalCodec("parents_condition").forGetter(ModifyBreedingPower::getParentsCondition),
            BiEntityAction.optionalCodec("parents_action").forGetter(ModifyBreedingPower::getParentsAction),
            EntityAction.optionalCodec("player_action").forGetter(ModifyBreedingPower::getPlayerAction)
    ).apply(i, ModifyBreedingPower::new));
    private final List<Modifier> modifier;
    private final BiEntityCondition parentsCondition;
    private final BiEntityAction parentsAction;
    private final EntityAction playerAction;

    public ModifyBreedingPower(BaseSettings settings, List<Modifier> modifier, BiEntityCondition parentsCondition, BiEntityAction parentsAction, EntityAction playerAction) {
        super(settings);
        this.modifier = modifier;
        this.parentsCondition = parentsCondition;
        this.parentsAction = parentsAction;
        this.playerAction = playerAction;
    }

    @Override
    public List<Modifier> getModifier() {
        return this.modifier;
    }

    public BiEntityCondition getParentsCondition() {
        return this.parentsCondition;
    }

    public BiEntityAction getParentsAction() {
        return this.parentsAction;
    }

    public EntityAction getPlayerAction() {
        return this.playerAction;
    }

    @Override
    public @NotNull MapCodec<? extends Power> codec() {
        return CODEC;
    }

    public static int getBreedAmount(Player player, Animal actor, Animal target) {
        OriginDataHolder holder = OriginDataHolder.get(player);
        return CommonUtils.rollInt(holder.streamActivePowers(ModifyBreedingPower.class)
                .filter(p -> p.parentsCondition.test(actor, target))
                .reduce(1.0, (p, c) -> {
                    c.parentsAction.execute(actor, target);
                    c.playerAction.execute(player);
                    return c.modify(holder, p);
                }, Double::sum), player.getRandom());
    }
}