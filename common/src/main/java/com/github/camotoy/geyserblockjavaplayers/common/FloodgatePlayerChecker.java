package com.github.camotoy.geyserblockjavaplayers.common;

import org.geysermc.floodgate.api.FloodgateApi;

import java.util.UUID;

public class FloodgatePlayerChecker {

    public boolean isBedrockPlayer(UUID uuid) {
        return FloodgateApi.getInstance().isFloodgatePlayer(uuid);
    }
}
