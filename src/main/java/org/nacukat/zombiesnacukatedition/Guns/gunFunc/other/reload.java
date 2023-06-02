package org.nacukat.zombiesnacukatedition.Guns.gunFunc.other;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.isShootingRR;
import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.plugin;

public class reload {
    public boolean reloadGun(final ItemStack item, final HashMap<Integer, Boolean> isReloading, final HashMap<Integer, Long> magazines, final long clipAmmo, long period, Player player) {
        magazines.putIfAbsent(item.getItemMeta().getCustomModelData(), 0L);
        if (magazines.get(item.getItemMeta().getCustomModelData()) != clipAmmo) {
            player.playSound(player, Sound.ENTITY_HORSE_GALLOP, 0.6F, 0.2F);
            Bukkit.getServer().getLogger().info("start");
            isReloading.put(item.getItemMeta().getCustomModelData(), Boolean.TRUE);
            new BukkitRunnable() {
                short count = 10;

                public void run() {
                    isReloading.replace(item.getItemMeta().getCustomModelData(), Boolean.TRUE);
                    short damage = (short)(item.getType().getMaxDurability() / 10 * count);
                    if (count == 0) {
                        magazines.replace(item.getItemMeta().getCustomModelData(), clipAmmo);
                        isReloading.replace(item.getItemMeta().getCustomModelData(), Boolean.FALSE);
                        item.setAmount((int)clipAmmo);
                        Bukkit.getServer().getLogger().info("end");
                        if (isShootingRR.containsKey(item.getItemMeta().getCustomModelData()) && isShootingRR.get(item.getItemMeta().getCustomModelData()))
                            isShootingRR.replace(item.getItemMeta().getCustomModelData(), Boolean.FALSE);
                        cancel();
                    }
                    if (count <= 10) {
                        item.setDurability(damage);
                        count = (short)(count - 1);
                    }
                }
            }.runTaskTimer(plugin, 0L, period / 10L);
        }
        return true;
    }
}
