package com.github.camotoy.geyserblockjavaplayers.common;

import org.geysermc.connector.GeyserConnector;

import java.util.UUID;

public class GeyserJavaPlayerChecker implements JavaPlayerChecker {
    private final GeyserConnector connector;

    public GeyserJavaPlayerChecker() {
        this.connector = GeyserConnector.getInstance();
    }

    @Override
    public boolean isBedrockPlayer(UUID uuid) {
        return this.connector.getPlayerByUuid(uuid) != null;
    }
}
