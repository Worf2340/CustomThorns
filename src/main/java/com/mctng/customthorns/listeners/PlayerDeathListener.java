package com.mctng.customthorns.listeners;

import com.mctng.customthorns.CustomThorns;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    CustomThorns plugin;

    public PlayerDeathListener(CustomThorns plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        this.plugin.damageBuffMap.remove(event.getEntity().getUniqueId());
    }
}
