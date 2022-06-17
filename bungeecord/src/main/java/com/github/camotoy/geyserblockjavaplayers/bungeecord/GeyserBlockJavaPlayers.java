package com.github.camotoy.geyserblockjavaplayers.bungeecord;

import com.github.camotoy.geyserblockjavaplayers.common.Configurate;
import com.github.camotoy.geyserblockjavaplayers.common.FloodgateJavaPlayerChecker;
import com.github.camotoy.geyserblockjavaplayers.common.GeyserJavaPlayerChecker;
import com.github.camotoy.geyserblockjavaplayers.common.JavaPlayerChecker;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public final class GeyserBlockJavaPlayers extends Plugin implements Listener {
    private JavaPlayerChecker playerChecker;
    public Configurate config;

    @Override
    public void onEnable() {
        config = Configurate.create(this.getDataFolder().toPath());
        boolean hasFloodgate = getProxy().getPluginManager().getPlugin("floodgate") != null;
        boolean hasGeyser = getProxy().getPluginManager().getPlugin("Geyser-BungeeCord") != null;
        if (!hasFloodgate && !hasGeyser) {
            getLogger().warning("There is no Geyser or Floodgate plugin detected! Disabling...");
            onDisable();
            return;
        }
        if (hasFloodgate) {
            this.playerChecker = new FloodgateJavaPlayerChecker();
        } else {
            this.playerChecker = new GeyserJavaPlayerChecker();
        }

        getProxy().getPluginManager().registerListener(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PostLoginEvent event) {
        if (event.getPlayer().hasPermission("geyserblockjavaplayers.bypass")) {
            return;
        }
        boolean isBedrockPlayer = this.playerChecker.isBedrockPlayer(event.getPlayer().getUniqueId());
        if (!isBedrockPlayer) {
            event.getPlayer().disconnect(new TextComponent(config.getBlockJavaMessage()));
        }
    }
}
