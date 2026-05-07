package com.iafenvoy.origins.classes.data.power;

import com.iafenvoy.origins.attachment.OriginDataHolder;
import com.iafenvoy.origins.data.action.BiEntityAction;
import com.iafenvoy.origins.data.condition.BiEntityCondition;
import com.iafenvoy.origins.data.power.Power;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.OwnableEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.AnimalTameEvent;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;

import java.util.Objects;

@EventBusSubscriber
public class ActionOnTamePower extends Power {
    public static final MapCodec<ActionOnTamePower> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            BaseSettings.CODEC.forGetter(Power::getSettings),
            BiEntityAction.CODEC.fieldOf("bientity_action").forGetter(ActionOnTamePower::getBiEntityAction),
            BiEntityCondition.optionalCodec("bientity_condition").forGetter(ActionOnTamePower::getBiEntityCondition)
    ).apply(i, ActionOnTamePower::new));
    private final BiEntityAction biEntityAction;
    private final BiEntityCondition biEntityCondition;

    public ActionOnTamePower(BaseSettings settings, BiEntityAction biEntityAction, BiEntityCondition biEntityCondition) {
        super(settings);
        this.biEntityAction = biEntityAction;
        this.biEntityCondition = biEntityCondition;
    }

    public BiEntityAction getBiEntityAction() {
        return this.biEntityAction;
    }

    public BiEntityCondition getBiEntityCondition() {
        return this.biEntityCondition;
    }

    @Override
    public MapCodec<? extends Power> codec() {
        return CODEC;
    }

    public static void apply(Entity player, Entity tameable) {
        OriginDataHolder.get(player).getHelper().execute(ActionOnTamePower.class,
                p -> p.biEntityCondition.test(player, tameable),
                p -> p.biEntityAction.execute(player, tameable)
        );
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onAnimalTame(AnimalTameEvent event) {
        ActionOnTamePower.apply(event.getTamer(), event.getAnimal());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBabyEntitySpawn(BabyEntitySpawnEvent event) {
        AgeableMob child = event.getChild();
        if (child != null &&
                event.getParentA() instanceof OwnableEntity a &&
                event.getParentB() instanceof OwnableEntity b &&
                a.getOwner() != null && Objects.equals(a.getOwnerUUID(), b.getOwnerUUID()))
            ActionOnTamePower.apply(a.getOwner(), child);
    }
}
