package com.github.camotoy.geyserblockjavaplayers.common;

import org.geysermc.geyser.api.GeyserApi;
import org.geysermc.geyser.api.connection.GeyserConnection;

import java.util.UUID;

public class GeyserJavaPlayerChecker implements JavaPlayerChecker {
    @Override
    public boolean isBedrockPlayer(UUID uuid) {
        if (GeyserApi.api() == null) {
            return false;
        }

        GeyserConnection connection = GeyserApi.api().connectionByUuid(uuid);
        return connection != null;
    }
}
