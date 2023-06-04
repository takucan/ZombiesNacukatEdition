package org.nacukat.zombiesnacukatedition.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.scoreboard.*;

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
            Kills.putIfAbsent(e.getEntity().getKiller().getName(), Long.valueOf(0L));
            Kills.put(e.getEntity().getKiller().getName(), Long.valueOf(((Long) Kills.get(e.getEntity().getKiller().getName())).longValue() + 1L));
        }
    }
}
