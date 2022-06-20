package com.github.camotoy.geyserblockjavaplayers.common;

import org.geysermc.geyser.GeyserImpl;

import java.util.Objects;
import java.util.UUID;

public class GeyserJavaPlayerChecker implements JavaPlayerChecker {
    private final GeyserImpl connector;

    public GeyserJavaPlayerChecker() {
        this.connector = Objects.requireNonNull(GeyserImpl.getInstance());
    }

    @Override
    public boolean isBedrockPlayer(UUID uuid) {
        return this.connector.connectionByUuid(uuid) != null;
    }
}
