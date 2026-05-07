package com.iafenvoy.origins.classes.util;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.iafenvoy.origins.classes.mixin.accessor.LootTableAccessor;
import com.iafenvoy.origins.classes.registry.tag.OCItemTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.CompositeEntryBase;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

import java.util.*;
import java.util.stream.Collectors;

@EventBusSubscriber
public class MerchantHelper extends SimplePreparableReloadListener<Void> {
    private static MerchantHelper INSTANCE;
    private final HolderLookup.RegistryLookup<LootTable> lootTables;
    public Set<Holder<Item>> obtainableItems = ImmutableSet.of();
    public Set<Holder<Item>> blacklistItems = ImmutableSet.of();

    public MerchantHelper(HolderLookup.RegistryLookup<LootTable> lootTables) {
        this.lootTables = lootTables;
    }

    @Override
    protected Void prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        return null;
    }

    @Override
    protected void apply(Void nothing, ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableSet.Builder<Holder<Item>> builder = ImmutableSet.builder();
        for (LootTable lootTable : this.lootTables.listElements().map(Holder.Reference::value).toList()) {
            Queue<LootPoolEntryContainer> entryQueue = ((LootTableAccessor) lootTable).getPools().stream().flatMap(pool -> pool.entries.stream()).collect(Collectors.toCollection(LinkedList::new));
            while (!entryQueue.isEmpty()) {
                LootPoolEntryContainer entry = entryQueue.remove();
                if (entry instanceof LootItem li)
                    builder.add(li.item);
                else if (entry instanceof TagEntry te)
                    BuiltInRegistries.ITEM.getTag(te.tag).ifPresent(x -> x.forEach(builder::add));
                else if (entry instanceof CompositeEntryBase ceb)
                    entryQueue.addAll(ceb.children);
            }
        }
        this.obtainableItems = builder.build();
        this.blacklistItems = BuiltInRegistries.ITEM.getTag(OCItemTags.MERCHANT_BLACKLIST).map(x -> x.stream().collect(Collectors.toSet())).orElse(Set.of());
    }

    public Item randomObtainableItem(RandomSource random, Set<Holder<Item>> exclude) {
        List<Holder<Item>> obtainables;
        if (exclude.isEmpty()) {
            obtainables = List.copyOf(this.obtainableItems);
        } else {
            obtainables = List.copyOf(Sets.difference(this.obtainableItems, exclude));
        }
        return !obtainables.isEmpty() ? obtainables.get(random.nextInt(obtainables.size())).value() : Items.AIR;
    }

    public static ItemStack randomEnchantedItemStack(Item item, RandomSource random, float chance, int power, RegistryAccess access) {
        ItemStack stack = new ItemStack(item);
        if (item.isEnchantable(stack) && random.nextFloat() < chance)
            EnchantmentHelper.enchantItem(random, stack, 1 + random.nextInt(power), access, Optional.empty());
        return stack;
    }

    public static MerchantHelper instance() {
        if (INSTANCE == null) throw new IllegalStateException("MerchantHelper haven't been initialized yet!");
        return INSTANCE;
    }

    @SubscribeEvent
    public static void addSelfToReloadListeners(AddReloadListenerEvent event) {
        INSTANCE = new MerchantHelper(event.getServerResources().getRegistryLookup().lookupOrThrow(Registries.LOOT_TABLE));
        event.addListener(INSTANCE);
    }
}
