package com.mctng.customthorns.listeners;

import com.mctng.customthorns.CustomThorns;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    CustomThorns plugin;

    public PlayerQuitListener(CustomThorns plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.plugin.damageBuffMap.remove(event.getPlayer().getUniqueId());
    }
}
