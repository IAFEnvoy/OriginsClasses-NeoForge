package com.iafenvoy.origins.classes.network.payload;

import com.iafenvoy.origins.classes.OriginsClasses;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record InfiniteTraderS2CPayload(boolean infiniteTrader) implements CustomPacketPayload {
    public static final Type<InfiniteTraderS2CPayload> TYPE = new Type<>(OriginsClasses.identifier("infinite_trader_s2c"));
    public static final StreamCodec<RegistryFriendlyByteBuf, InfiniteTraderS2CPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, InfiniteTraderS2CPayload::infiniteTrader,
            InfiniteTraderS2CPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
