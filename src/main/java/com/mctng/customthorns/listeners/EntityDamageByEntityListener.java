package com.mctng.customthorns.listeners;

import com.mctng.customthorns.CustomThorns;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class EntityDamageByEntityListener implements Listener {

    CustomThorns plugin;

    public EntityDamageByEntityListener(CustomThorns plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {


        // Cancel Thorns damage
        if (event.getCause() == EntityDamageEvent.DamageCause.THORNS) {
            event.setCancelled(true);
            return;
        }

        // Adds potential damage buff to damaged entity for future use
        if (event.getEntityType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();

            double damage = calculateThornsDamage(player.getInventory().getArmorContents());

            if (damage > 0) {
                if (!(this.plugin.damageBuffMap.containsKey(player.getUniqueId()))) {
                    this.plugin.damageBuffMap.put(player.getUniqueId(), damage);
                    player.sendMessage(ChatColor.BLUE + "You got a " + damage + " buff!");
                }
            }

        }

        // Adds extra damage to damaged entity if the attacker had damage buff
        if (event.getDamager().getType() == EntityType.PLAYER) {
            if (this.plugin.damageBuffMap.containsKey(event.getDamager().getUniqueId())) {
                double originalDamage = event.getDamage();
                double damageBuff = this.plugin.damageBuffMap.get(event.getDamager().getUniqueId());

                Player player = (Player) event.getDamager();

                player.sendMessage(ChatColor.LIGHT_PURPLE + "Original Damage: " + originalDamage);
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Damage Buff: " + damageBuff);

                event.setDamage(this.plugin.damageBuffMap.get(event.getDamager().getUniqueId()) + originalDamage);

                this.plugin.damageBuffMap.remove(event.getDamager().getUniqueId());
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Final Damage: " + event.getFinalDamage());

            }
        }

    }

    double calculateThornsDamage (ItemStack[] armorList) {
        double totalDamage = 0;

        for (ItemStack armorPiece : armorList) {
            for (Enchantment enchantment : armorPiece.getEnchantments().keySet()) {

                if (enchantment.getName().equals("THORNS")) {
                    Random rand = new Random();
                    int enchantmentLevel = armorPiece.getEnchantments().get(enchantment);
                    double damageProbability = enchantmentLevel * .15;
                    double randDouble = rand.nextDouble();
                    if (randDouble <= damageProbability) {
                        int damageAmount = rand.nextInt(4 - 1) + 1;
                        totalDamage += damageAmount;
                    }
                }
            }
        }

        if (totalDamage >= 4) {
            totalDamage = 4;
        }

        return totalDamage/4;
    }
}

