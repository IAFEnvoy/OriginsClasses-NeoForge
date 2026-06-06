package com.iafenvoy.origins.classes.mixin;

import com.iafenvoy.origins.attachment.OriginDataHolder;
import com.iafenvoy.origins.classes.data.power.InfiniteTradePower;
import com.iafenvoy.origins.classes.data.power.RareWanderingLootPower;
import com.iafenvoy.origins.classes.util.MerchantHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.RegistryOps;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractVillager.class)
public abstract class AbstractVillagerMixin extends AgeableMob {
    @Shadow
    protected MerchantOffers offers;
    @javax.annotation.Nullable
    @Shadow
    private Player tradingPlayer;

    @Unique
    private int originsClasses$offerCountWithoutAdditional;
    @Unique
    private MerchantOffers originsClasses$additionalOffers;

    protected AbstractVillagerMixin(EntityType<? extends AgeableMob> type, Level world) {
        super(type, world);
    }

    @Inject(method = "notifyTrade", at = @At("TAIL"))
    private void infiniteTrade(MerchantOffer offer, CallbackInfo ci) {
        if (this.tradingPlayer != null && OriginDataHolder.get(this.tradingPlayer).hasActivePower(InfiniteTradePower.class))
            --offer.uses;
    }

    @Inject(method = "setTradingPlayer", at = @At("HEAD"))
    private void addAdditionalOffers(@Nullable Player customer, CallbackInfo ci) {
        if ((Object) this instanceof WanderingTrader) {
            if (customer != null && OriginDataHolder.get(customer).hasActivePower(RareWanderingLootPower.class)) {
                if (this.originsClasses$additionalOffers == null) {
                    this.originsClasses$offerCountWithoutAdditional = this.offers.size();
                    this.originsClasses$additionalOffers = this.originsClasses$buildAdditionalOffers();
                }
                this.offers.addAll(this.originsClasses$additionalOffers);
            } else if (this.originsClasses$additionalOffers != null) {
                while (this.offers.size() > this.originsClasses$offerCountWithoutAdditional) {
                    this.offers.removeLast();
                }
            }
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void writeAdditionalOffersToTag(CompoundTag tag, CallbackInfo ci) {
        if (this.originsClasses$additionalOffers != null && this.tradingPlayer != null) {
            tag.put("AdditionalOffers", MerchantOffers.CODEC.encodeStart(RegistryOps.create(NbtOps.INSTANCE, this.tradingPlayer.registryAccess()), this.originsClasses$additionalOffers).getOrThrow());
            tag.putInt("OfferCountNoAdditional", this.originsClasses$offerCountWithoutAdditional);
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readAdditionalOffersFromTag(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("AdditionalOffers") && this.tradingPlayer != null) {
            this.originsClasses$additionalOffers = MerchantOffers.CODEC.parse(RegistryOps.create(NbtOps.INSTANCE, this.tradingPlayer.registryAccess()), tag.getCompound("AdditionalOffers")).getOrThrow();
            this.originsClasses$offerCountWithoutAdditional = tag.getInt("OfferCountNoAdditional");
        }
    }

    @Unique
    private MerchantOffers originsClasses$buildAdditionalOffers() {
        MerchantOffers offers = new MerchantOffers();
        MerchantHelper helper = MerchantHelper.instance();
        RandomSource random = RandomSource.create();
        Item desireditem = helper.randomObtainableItem(random, helper.blacklistItems);
        offers.add(new MerchantOffer(
                new ItemCost(Items.EMERALD, random.nextInt(12) + 6),
                MerchantHelper.randomEnchantedItemStack(
                        helper.randomObtainableItem(random, helper.blacklistItems),
                        random, 0.5f, 30, this.registryAccess()
                ),
                1,
                5,
                0.05F)
        );
        offers.add(new MerchantOffer(
                new ItemCost(desireditem, 1 + random.nextInt(Math.min(16, desireditem.getDefaultInstance().getMaxStackSize()))),
                MerchantHelper.randomEnchantedItemStack(helper.randomObtainableItem(random, helper.blacklistItems)
                        , random, 0.5F, 30, this.registryAccess()),
                1,
                5,
                0.05F)
        );
        return offers;
    }
}
