package com.iafenvoy.origins.classes.registry;

import com.iafenvoy.origins.classes.OriginsClasses;
import com.iafenvoy.origins.classes.registry.tag.OCItemTags;
import com.iafenvoy.origins.data.condition.ConditionRegistries;
import com.iafenvoy.origins.data.condition.ItemCondition;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.iafenvoy.origins.data.condition.SimpleConditions.createItem;
import static net.neoforged.neoforge.common.ItemAbilities.*;

@SuppressWarnings("unused")
public final class OCItemConditions {
    public static final DeferredRegister<MapCodec<? extends ItemCondition>> REGISTRY = DeferredRegister.create(ConditionRegistries.ITEM_CONDITION, OriginsClasses.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends ItemCondition>, MapCodec<? extends ItemCondition>> DIGGERS = REGISTRY.register("diggers", () ->
            createItem((level, stack) ->
                    stack.getItem() instanceof DiggerItem ||
                            stack.is(OCItemTags.DIGGERS) ||
                            stack.canPerformAction(HOE_DIG) ||
                            stack.canPerformAction(PICKAXE_DIG) ||
                            stack.canPerformAction(AXE_DIG) ||
                            stack.canPerformAction(SHOVEL_DIG) ||
                            stack.canPerformAction(SHEARS_DIG)
            ));
    public static final DeferredHolder<MapCodec<? extends ItemCondition>, MapCodec<? extends ItemCondition>> MELEE = REGISTRY.register("melee", () ->
            createItem((level, stack) ->
                    stack.supportsEnchantment(level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.SHARPNESS)) ||
                            stack.is(OCItemTags.MELEE_WEAPONS)
            ));
    public static final DeferredHolder<MapCodec<? extends ItemCondition>, MapCodec<? extends ItemCondition>> RANGE = REGISTRY.register("range", () ->
            createItem((level, stack) ->
                    stack.getItem() instanceof ProjectileWeaponItem ||
                            stack.is(OCItemTags.RANGE_WEAPONS)
            ));
    public static final DeferredHolder<MapCodec<? extends ItemCondition>, MapCodec<? extends ItemCondition>> ARMORS = REGISTRY.register("armors", () ->
            createItem((level, stack) ->
                    stack.getItem() instanceof ArmorItem ||
                            stack.is(OCItemTags.ARMORS)
            ));
    public static final DeferredHolder<MapCodec<? extends ItemCondition>, MapCodec<? extends ItemCondition>> HOE = REGISTRY.register("hoe", () ->
            createItem((level, stack) ->
                    stack.canPerformAction(HOE_DIG) ||
                            stack.canPerformAction(HOE_TILL) ||
                            stack.is(OCItemTags.HOES)
            ));
    public static final DeferredHolder<MapCodec<? extends ItemCondition>, MapCodec<? extends ItemCondition>> PICKAXE = REGISTRY.register("pickaxe", () ->
            createItem((level, stack) ->
                    stack.canPerformAction(PICKAXE_DIG) ||
                            stack.is(OCItemTags.PICKAXES)
            ));
    public static final DeferredHolder<MapCodec<? extends ItemCondition>, MapCodec<? extends ItemCondition>> AXE = REGISTRY.register("axe", () ->
            createItem((level, stack) ->
                    stack.canPerformAction(AXE_DIG) ||
                            stack.canPerformAction(AXE_STRIP) ||
                            stack.canPerformAction(AXE_SCRAPE) ||
                            stack.canPerformAction(AXE_WAX_OFF) ||
                            stack.is(OCItemTags.AXES)
            ));
    public static final DeferredHolder<MapCodec<? extends ItemCondition>, MapCodec<? extends ItemCondition>> SHOVEL = REGISTRY.register("shovel", () ->
            createItem((level, stack) ->
                    stack.canPerformAction(SHOVEL_DIG) ||
                            stack.canPerformAction(SHOVEL_FLATTEN) ||
                            stack.is(OCItemTags.SHOVELS)
            ));
    public static final DeferredHolder<MapCodec<? extends ItemCondition>, MapCodec<? extends ItemCondition>> SHEARS = REGISTRY.register("shears", () ->
            createItem((level, stack) ->
                    stack.canPerformAction(SHEARS_DIG) ||
                            stack.canPerformAction(SHEARS_CARVE) ||
                            stack.canPerformAction(SHEARS_DISARM) ||
                            stack.canPerformAction(SHEARS_HARVEST) ||
                            stack.is(OCItemTags.SHEARS)
            ));
    public static final DeferredHolder<MapCodec<? extends ItemCondition>, MapCodec<? extends ItemCondition>> SWORD = REGISTRY.register("sword", () ->
            createItem((level, stack) ->
                    stack.canPerformAction(SWORD_DIG) ||
                            stack.canPerformAction(SWORD_SWEEP) ||
                            stack.getItem() instanceof SwordItem ||
                            stack.is(OCItemTags.SWORDS)
            ));
    public static final DeferredHolder<MapCodec<? extends ItemCondition>, MapCodec<? extends ItemCondition>> BOW = REGISTRY.register("bow", () ->
            createItem((level, stack) ->
                    stack.getItem() instanceof BowItem ||
                            stack.is(OCItemTags.BOWS)
            ));
    public static final DeferredHolder<MapCodec<? extends ItemCondition>, MapCodec<? extends ItemCondition>> CROSSBOW = REGISTRY.register("crossbow", () ->
            createItem((level, stack) ->
                    stack.getItem() instanceof CrossbowItem ||
                            stack.is(OCItemTags.CROSSBOWS)
            ));
    public static final DeferredHolder<MapCodec<? extends ItemCondition>, MapCodec<? extends ItemCondition>> SHIELD = REGISTRY.register("shield", () ->
            createItem((level, stack) ->
                    stack.canPerformAction(SHIELD_BLOCK) ||
                            stack.getItem() instanceof ShieldItem ||
                            stack.is(OCItemTags.SHIELDS)
            ));
    public static final DeferredHolder<MapCodec<? extends ItemCondition>, MapCodec<? extends ItemCondition>> HELMET = REGISTRY.register("helmet", () ->
            createItem((level, stack) ->
                    (stack.getItem() instanceof ArmorItem armor && armor.getEquipmentSlot() == EquipmentSlot.HEAD) ||
                            stack.is(OCItemTags.HELMETS)
            ));
    public static final DeferredHolder<MapCodec<? extends ItemCondition>, MapCodec<? extends ItemCondition>> CHESTPLATE = REGISTRY.register("chestplate", () ->
            createItem((level, stack) ->
                    (stack.getItem() instanceof ArmorItem armor && armor.getEquipmentSlot() == EquipmentSlot.CHEST) ||
                            stack.is(OCItemTags.CHESTPLATES)
            ));
    public static final DeferredHolder<MapCodec<? extends ItemCondition>, MapCodec<? extends ItemCondition>> LEGGINGS = REGISTRY.register("leggings", () ->
            createItem((level, stack) ->
                    (stack.getItem() instanceof ArmorItem armor && armor.getEquipmentSlot() == EquipmentSlot.LEGS) ||
                            stack.is(OCItemTags.LEGGINGS)
            ));
    public static final DeferredHolder<MapCodec<? extends ItemCondition>, MapCodec<? extends ItemCondition>> SHOES = REGISTRY.register("shoes", () ->
            createItem((level, stack) ->
                    (stack.getItem() instanceof ArmorItem armor && armor.getEquipmentSlot() == EquipmentSlot.FEET) ||
                            stack.is(OCItemTags.SHOES)
            ));
}
