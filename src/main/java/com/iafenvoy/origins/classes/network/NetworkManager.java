package com.iafenvoy.origins.classes.network;

import com.iafenvoy.origins.classes.network.payload.InfiniteTraderS2CPayload;
import com.iafenvoy.origins.classes.network.payload.MultiMiningS2CPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.MainThreadPayloadHandler;

@EventBusSubscriber
public final class NetworkManager {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        event.registrar("1")
                .playToClient(InfiniteTraderS2CPayload.TYPE, InfiniteTraderS2CPayload.STREAM_CODEC, new MainThreadPayloadHandler<>(ClientNetworkHandler::onInfiniteTrader))
                .playToClient(MultiMiningS2CPayload.TYPE, MultiMiningS2CPayload.STREAM_CODEC, new MainThreadPayloadHandler<>(ClientNetworkHandler::onMultiMining));
    }
}
