package com.github.camotoy.geyserblockjavaplayers.bungeecord;

import com.github.camotoy.geyserblockjavaplayers.common.JavaPlayerChecker;
import org.geysermc.floodgate.api.FloodgateApi;

import java.util.UUID;

public class FloodgateBungeeJavaPlayerChecker implements JavaPlayerChecker {
    @Override
    public boolean isBedrockPlayer(UUID uuid) {
        return FloodgateApi.getInstance().isFloodgatePlayer(uuid);
    }
}
