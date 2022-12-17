package com.mctng.customthorns;

import com.mctng.customthorns.listeners.EntityDamageByEntityListener;
import com.mctng.customthorns.listeners.PlayerDeathListener;
import com.mctng.customthorns.listeners.PlayerQuitListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class CustomThorns extends JavaPlugin {

    public HashMap<UUID, Double> damageBuffMap;

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);

        this.damageBuffMap = new HashMap<>();

    }
}
