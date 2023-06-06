package org.nacukat.zombiesnacukatedition.Listeners;

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
    }
    @EventHandler
    public void shopItem(ItemDespawnEvent e){
        e.setCancelled(true);
    }
}