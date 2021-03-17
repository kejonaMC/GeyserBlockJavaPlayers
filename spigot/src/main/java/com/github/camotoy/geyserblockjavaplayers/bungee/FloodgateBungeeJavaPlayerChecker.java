package com.github.camotoy.geyserblockjavaplayers.bungee;

import com.github.camotoy.geyserblockjavaplayers.common.JavaPlayerChecker;
import org.geysermc.floodgate.FloodgateAPI;

import java.util.UUID;

public class FloodgateBungeeJavaPlayerChecker implements JavaPlayerChecker {
    @Override
    public boolean isBedrockPlayer(UUID uuid) {
        return FloodgateAPI.isBedrockPlayer(uuid);
    }
}
