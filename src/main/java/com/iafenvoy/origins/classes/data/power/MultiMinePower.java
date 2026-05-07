package com.iafenvoy.origins.classes.data.power;

import com.iafenvoy.origins.attachment.OriginDataHolder;
import com.iafenvoy.origins.classes.OriginsClasses;
import com.iafenvoy.origins.classes.util.CommonUtils;
import com.iafenvoy.origins.data.action.EntityAction;
import com.iafenvoy.origins.data.condition.BlockCondition;
import com.iafenvoy.origins.data.condition.ItemCondition;
import com.iafenvoy.origins.data.power.Power;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MultiMinePower extends Power {
    public static final MapCodec<MultiMinePower> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            BaseSettings.CODEC.forGetter(Power::getSettings),
            BlockCondition.optionalCodec("block_condition").forGetter(MultiMinePower::getBlockCondition),
            ItemCondition.optionalCodec("item_condition").forGetter(MultiMinePower::getItemCondition),
            EntityAction.optionalCodec("entity_action").forGetter(MultiMinePower::getEntityAction)
    ).apply(i, MultiMinePower::new));
    private final BlockCondition blockCondition;
    private final ItemCondition itemCondition;
    private final EntityAction entityAction;

    public MultiMinePower(BaseSettings settings, BlockCondition blockCondition, ItemCondition itemCondition, EntityAction entityAction) {
        super(settings);
        this.blockCondition = blockCondition;
        this.itemCondition = itemCondition;
        this.entityAction = entityAction;
    }

    public BlockCondition getBlockCondition() {
        return this.blockCondition;
    }

    public ItemCondition getItemCondition() {
        return this.itemCondition;
    }

    public EntityAction getEntityAction() {
        return this.entityAction;
    }

    @Override
    public @NotNull MapCodec<? extends Power> codec() {
        return CODEC;
    }

    public static boolean shouldApply(MultiMinePower cp, Player player, BlockPos pos) {
        return cp.blockCondition.test(player.level(), pos) &&
                cp.itemCondition.test(player.level(), player.getMainHandItem());
    }

    public static List<BlockPos> apply(Player player, BlockPos pos, BlockState state) {
        List<MultiMinePower> powers = OriginDataHolder.get(player).streamActivePowers(MultiMinePower.class)
                .filter(cp -> shouldApply(cp, player, pos))
                .toList();
        OriginsClasses.LOGGER.debug(powers.size());
        for (MultiMinePower cmmp : powers) {
            List<BlockPos> affectBlocks = CommonUtils.lumberjackMultiMineRange(player, state, pos);
            if (!affectBlocks.isEmpty())
                return affectBlocks;
        }
        return new ArrayList<>();
    }
}
