package com.github.camotoy.geyserblockjavaplayers.common;

import org.geysermc.floodgate.api.FloodgateApi;

import java.util.UUID;

public class FloodgateJavaPlayerChecker implements JavaPlayerChecker {
    @Override
    public boolean isBedrockPlayer(UUID uuid) {
        return FloodgateApi.getInstance().isFloodgatePlayer(uuid);
    }
}
