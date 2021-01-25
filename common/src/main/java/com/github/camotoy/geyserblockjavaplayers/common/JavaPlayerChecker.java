package com.github.camotoy.geyserblockjavaplayers.common;

import java.util.UUID;

public interface JavaPlayerChecker {
    /**
     * @param uuid the UUID of the player joining to verify
     * @return true if this player is a Bedrock player and therefore should be allowed on the server.
     */
    boolean isBedrockPlayer(UUID uuid);
}
