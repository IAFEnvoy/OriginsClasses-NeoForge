package com.iafenvoy.origins.classes.network;

import com.iafenvoy.origins.classes.OriginsClassesClient;
import com.iafenvoy.origins.classes.network.payload.InfiniteTraderS2CPayload;
import com.iafenvoy.origins.classes.network.payload.MultiMiningS2CPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientNetworkHandler {
    public static void onInfiniteTrader(InfiniteTraderS2CPayload payload, IPayloadContext context) {
        OriginsClassesClient.INFINITE_TRADER = payload.infiniteTrader();
    }

    public static void onMultiMining(MultiMiningS2CPayload payload, IPayloadContext context) {
        OriginsClassesClient.MULTI_MINING = payload.multiMining();
    }
}
