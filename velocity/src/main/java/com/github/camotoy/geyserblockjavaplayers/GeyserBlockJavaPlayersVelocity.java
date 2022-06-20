package com.github.camotoy.geyserblockjavaplayers;

import com.github.camotoy.geyserblockjavaplayers.common.*;
import com.google.inject.Inject;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "geyserblockjavaplayers",
        name = "GeyserBlockJavaPlayers",
        version = "1.2-SNAPSHOT"
)
public class GeyserBlockJavaPlayersVelocity {
    private final Logger logger;
    private final ProxyServer server;
    private Configurate config = null;
    private final Path dataDirectory;
    private JavaPlayerChecker playerChecker;
    private static LegacyComponentSerializer serializer = LegacyComponentSerializer.builder().character('&').hexCharacter('#').hexColors().build();

    @Inject
    public GeyserBlockJavaPlayersVelocity(ProxyServer server, Logger logger, @DataDirectory final Path folder) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = folder;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        config = Configurate.create(this.dataDirectory);
        boolean hasFloodgate = server.getPluginManager().isLoaded("floodgate");
        boolean hasGeyser = server.getPluginManager().isLoaded("Geyser-Velocity");

        if (!hasFloodgate && !hasGeyser) {
            logger.warn("There is no Geyser or Floodgate plugin detected!");
            return;
        }

        if (hasFloodgate) {
            this.playerChecker = new FloodgateJavaPlayerChecker();
        } else {
            this.playerChecker = new GeyserJavaPlayerChecker();
        }
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onPlayerChangeServer(ServerConnectedEvent event) {
        if (event.getPlayer().hasPermission(Permission.bypassPermission)) {
            return;
        }

        boolean isBedrockPlayer = this.playerChecker.isBedrockPlayer(event.getPlayer().getUniqueId());
        String servername = event.getServer().getServerInfo().getName();

        if (!isBedrockPlayer
                // Check if the "deny-server-access:" list contains the server name.
                && config.getNoServerAccess().contains(servername)
                // Then check if the list contains "all" in case they want full network deny
                || config.getNoServerAccess().contains("all")) {
            // Disconnect Java player
            event.getPlayer().disconnect(color(config.getBlockJavaMessage()));
        }
    }

    public static TextComponent color(String s) {
        return serializer.deserialize(s);
    }
}
