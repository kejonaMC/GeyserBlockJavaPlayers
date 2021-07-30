package com.github.camotoy.geyserblockjavaplayers.spigot;

import com.github.camotoy.geyserblockjavaplayers.common.GeyserJavaPlayerChecker;
import com.github.camotoy.geyserblockjavaplayers.common.JavaPlayerChecker;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class GeyserBlockJavaPlayers extends JavaPlugin implements Listener {
    private JavaPlayerChecker playerChecker;

    @Override
    public void onEnable() {
        boolean hasFloodgate = Bukkit.getPluginManager().getPlugin("floodgate-spigot") != null;
        boolean hasGeyser = Bukkit.getPluginManager().getPlugin("Geyser-Spigot") != null;
        if (!hasFloodgate && !hasGeyser) {
            getLogger().warning("There is no Geyser or Floodgate plugin detected! Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if (hasFloodgate) {
            this.playerChecker = new FloodgateSpigotJavaPlayerChecker();
        } else {
            this.playerChecker = new GeyserJavaPlayerChecker();
        }

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("geyserblockjavaplayers.bypass")) {
            return;
        }
        boolean isBedrockPlayer = this.playerChecker.isBedrockPlayer(event.getPlayer().getUniqueId());
        if (!isBedrockPlayer) {
            event.getPlayer().kickPlayer("This server can only be joined by Bedrock players!");
        }
    }
}
