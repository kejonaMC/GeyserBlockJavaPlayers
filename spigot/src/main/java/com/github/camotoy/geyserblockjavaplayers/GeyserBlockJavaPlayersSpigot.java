package com.github.camotoy.geyserblockjavaplayers;

import com.github.camotoy.geyserblockjavaplayers.common.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class GeyserBlockJavaPlayersSpigot extends JavaPlugin implements Listener {
    public Configurate config;
    public FloodgatePlayerChecker playerChecker;

    @Override
    public void onEnable() {
        config = Configurate.create(this.getDataFolder().toPath());
        boolean hasFloodgate = Bukkit.getPluginManager().getPlugin("floodgate") != null;

        if (!hasFloodgate) {
            getLogger().warning("There is no Floodgate plugin detected! Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        playerChecker = new FloodgatePlayerChecker();

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission(Permission.bypassPermission)) {
            return;
        }

        boolean isBedrockPlayer = playerChecker.isBedrockPlayer(event.getPlayer().getUniqueId());

        if (!isBedrockPlayer) {
            event.getPlayer().kickPlayer(ChatColor.translateAlternateColorCodes('&',config.getBlockJavaMessage()));
        }
    }
}
