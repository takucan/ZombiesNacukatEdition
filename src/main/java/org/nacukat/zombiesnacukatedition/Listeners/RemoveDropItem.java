package org.nacukat.zombiesnacukatedition.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.Kills;

public class RemoveDropItem implements Listener {
    @EventHandler
    public void Suteruna(PlayerDropItemEvent e) {
        e.getItemDrop().remove();
    }
    @EventHandler
    public void onZombieDied(EntityDeathEvent e) {
        e.getDrops().clear();
        e.setDroppedExp(0);

        if (e.getEntity().getKiller() instanceof Player) {
            Kills.putIfAbsent(e.getEntity().getKiller().getName(), 0L);
            Kills.put(e.getEntity().getKiller().getName(), Kills.get(e.getEntity().getKiller().getName()) + 1L);
        }
        if(e.getEntity().getKiller() != null&&e.getEntity().hasMetadata("TYPE")&&e.getEntity().getMetadata("TYPE").get(0).asString().equals("ENDER")){
            Endermite enderMite = (Endermite) Bukkit.getWorld("world").spawnEntity(e.getEntity().getKiller().getLocation(), EntityType.ENDERMITE,false);
            enderMite.setMaxHealth(50);
            enderMite.setHealth(50);
            enderMite.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(7);
            enderMite.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(0.8);
            enderMite.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.4);
        }
    }
    @EventHandler
    public void shopItem(ItemDespawnEvent e){
        e.setCancelled(true);
    }
}