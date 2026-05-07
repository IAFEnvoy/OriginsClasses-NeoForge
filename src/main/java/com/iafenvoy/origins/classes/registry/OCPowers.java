package com.iafenvoy.origins.classes.registry;

import com.iafenvoy.origins.classes.OriginsClasses;
import com.iafenvoy.origins.classes.data.power.*;
import com.iafenvoy.origins.data.power.Power;
import com.iafenvoy.origins.data.power.PowerRegistries;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("unused")
public final class OCPowers {
    public static final DeferredRegister<MapCodec<? extends Power>> REGISTRY = DeferredRegister.create(PowerRegistries.POWER_TYPE, OriginsClasses.MOD_ID);
    // Warrior
    public static final DeferredHolder<MapCodec<? extends Power>, MapCodec<ModifySpeedOnItemUsePower>> MODIFY_SPEED_ON_ITEM_USE = REGISTRY.register("modify_speed_on_item_use", () -> ModifySpeedOnItemUsePower.CODEC);
    // Ranger
    public static final DeferredHolder<MapCodec<? extends Power>, MapCodec<ModifyProjectileDivergencePower>> MODIFY_PROJECTILE_DIVERGENCE = REGISTRY.register("modify_projectile_divergence", () -> ModifyProjectileDivergencePower.CODEC);
    // Beastmaster
    public static final DeferredHolder<MapCodec<? extends Power>, MapCodec<ActionOnTamePower>> ACTION_ON_TAME = REGISTRY.register("action_on_tame", () -> ActionOnTamePower.CODEC);
    public static final DeferredHolder<MapCodec<? extends Power>, MapCodec<TamedPotionDiffusalPower>> TAMED_POTION_DIFFUSAL = REGISTRY.register("tamed_potion_diffusal", () -> TamedPotionDiffusalPower.CODEC);
    // Cook
    public static final DeferredHolder<MapCodec<? extends Power>, MapCodec<ModifyCraftedFoodPower>> MODIFY_CRAFTED_FOOD = REGISTRY.register("modify_crafted_food", () -> ModifyCraftedFoodPower.CODEC);
    public static final DeferredHolder<MapCodec<? extends Power>, MapCodec<ModifyFurnaceXPPower>> MODIFY_FURNACE_XP = REGISTRY.register("modify_furnace_xp", () -> ModifyFurnaceXPPower.CODEC);
    // Cleric
    public static final DeferredHolder<MapCodec<? extends Power>, MapCodec<PotionBonusPower>> POTION_BONUS = REGISTRY.register("potion_bonus", () -> PotionBonusPower.CODEC);
    public static final DeferredHolder<MapCodec<? extends Power>, MapCodec<ModifyEnchantingLevelPower>> MODIFY_ENCHANTING_LEVEL = REGISTRY.register("modify_enchanting_level", () -> ModifyEnchantingLevelPower.CODEC);
    // Blacksmith
    public static final DeferredHolder<MapCodec<? extends Power>, MapCodec<ModifyCraftResultPower>> MODIFY_CRAFT_RESULT = REGISTRY.register("modify_craft_result", () -> ModifyCraftResultPower.CODEC);
    public static final DeferredHolder<MapCodec<? extends Power>, MapCodec<ModifyRepairMaterialCostPower>> MODIFY_REPAIR_MATERIAL_COST = REGISTRY.register("modify_repair_material_cost", () -> ModifyRepairMaterialCostPower.CODEC);
    public static final DeferredHolder<MapCodec<? extends Power>, MapCodec<ModifyCombineRepairDurabilityPower>> MODIFY_COMBINE_REPAIR_DURABILITY = REGISTRY.register("modify_combine_repair_durability", () -> ModifyCombineRepairDurabilityPower.CODEC);
    // Farmer
    public static final DeferredHolder<MapCodec<? extends Power>, MapCodec<ModifyBlockLootPower>> MODIFY_BLOCK_LOOT = REGISTRY.register("modify_block_loot", () -> ModifyBlockLootPower.CODEC);
    public static final DeferredHolder<MapCodec<? extends Power>, MapCodec<ModifyBoneMealPower>> MODIFY_BONE_MEAL = REGISTRY.register("modify_bone_meal", () -> ModifyBoneMealPower.CODEC);
    // Rancher
    public static final DeferredHolder<MapCodec<? extends Power>, MapCodec<ModifyEntityLootPower>> MODIFY_ENTITY_LOOT = REGISTRY.register("modify_entity_loot", () -> ModifyEntityLootPower.CODEC);
    public static final DeferredHolder<MapCodec<? extends Power>, MapCodec<ModifyBreedingPower>> MODIFY_BREEDING = REGISTRY.register("modify_breeding", () -> ModifyBreedingPower.CODEC);
    // Merchant
    public static final DeferredHolder<MapCodec<? extends Power>, MapCodec<InfiniteTradePower>> INFINITE_TRADE = REGISTRY.register("infinite_trade", () -> InfiniteTradePower.CODEC);
    public static final DeferredHolder<MapCodec<? extends Power>, MapCodec<RareWanderingLootPower>> RARE_WANDERING_LOOT = REGISTRY.register("rare_wandering_loot", () -> RareWanderingLootPower.CODEC);
    // Lumberjack
    public static final DeferredHolder<MapCodec<? extends Power>, MapCodec<MultiMinePower>> LUMBERJACK = REGISTRY.register("lumberjack", () -> MultiMinePower.CODEC);
    // Miner
    public static final DeferredHolder<MapCodec<? extends Power>, MapCodec<NoMiningExhaustionPower>> NO_MINING_EXHAUSTION = REGISTRY.register("no_mining_exhaustion", () -> NoMiningExhaustionPower.CODEC);
    // Adventurer
    public static final DeferredHolder<MapCodec<? extends Power>, MapCodec<NoSprintExhaustionPower>> NO_SPRINT_EXHAUSTION = REGISTRY.register("no_sprint_exhaustion", () -> NoSprintExhaustionPower.CODEC);

}