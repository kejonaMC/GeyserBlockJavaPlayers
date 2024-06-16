package com.github.camotoy.geyserblockjavaplayers;

import com.github.camotoy.geyserblockjavaplayers.common.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import org.jetbrains.annotations.NotNull;

public final class GeyserBlockJavaPlayersBungee extends Plugin implements Listener {
    public Configurate config;
    public FloodgatePlayerChecker playerChecker;

    @Override
    public void onEnable() {
        config = Configurate.create(this.getDataFolder().toPath());
        boolean hasFloodgate = getProxy().getPluginManager().getPlugin("floodgate") != null;

        if (!hasFloodgate) {
            getLogger().warning("There is no Floodgate plugin detected! Disabling...");
            onDisable();
            return;
        }

        playerChecker = new FloodgatePlayerChecker();

        getProxy().getPluginManager().registerListener(this, this);
    }

    @EventHandler
    public void onPlayerJoin(@NotNull ServerConnectedEvent event) {
        if (event.getPlayer().hasPermission(Permission.bypassPermission)) {
            return;
        }

        boolean isBedrockPlayer = playerChecker.isBedrockPlayer(event.getPlayer().getUniqueId());
        String servername = event.getServer().getInfo().getName();

        if (!isBedrockPlayer
                // Check if the "deny-server-access:" list contains the server name.
                && config.getNoServerAccess().contains(servername)
                // Then check if the list contains "all" in case they want full network deny
                || config.getNoServerAccess().contains("all")) {
            // Disconnect Java player
            event.getPlayer().disconnect(new TextComponent(ChatColor.translateAlternateColorCodes( '&', config.getBlockJavaMessage())));
        }
    }
}
