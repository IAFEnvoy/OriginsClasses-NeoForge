package com.iafenvoy.origins.classes.network.payload;

import com.iafenvoy.origins.classes.OriginsClasses;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record MultiMiningS2CPayload(boolean multiMining) implements CustomPacketPayload {
    public static final Type<MultiMiningS2CPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(OriginsClasses.MOD_ID, "multi_mining_s2c"));
    public static final StreamCodec<RegistryFriendlyByteBuf, MultiMiningS2CPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, MultiMiningS2CPayload::multiMining,
            MultiMiningS2CPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
