package org.nacukat.zombiesnacukatedition.Guns;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.reload;
import org.nacukat.zombiesnacukatedition.Skill.LeftLightningRod;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class playerAnimation implements Listener {

    @EventHandler
    public void DBS(BlockIgniteEvent e){
        if(e.getCause().equals(BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL))e.setCancelled(true);
    }
    @EventHandler
    public void playerAnimation(PlayerAnimationEvent e){
        Player player = e.getPlayer();
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();

        if(item.getType().equals(Material.BLAZE_ROD)){
            new LeftLightningRod().lightningRod(player);
            return;
        }
        if (item.getType().equals(Material.IRON_HOE) ||item.getType().equals(Material.DIAMOND_PICKAXE) || item.getType().equals(Material.GOLDEN_PICKAXE) || item.getType().equals(Material.GOLDEN_SHOVEL) || item.getType().equals(Material.FLINT_AND_STEEL)) {

            if (!meta.hasCustomModelData()) {
                Random random = new Random();
                int randomMeta = random.nextInt(100000);
                meta.setCustomModelData(randomMeta);
                switch (item.getType()) {
                    case DIAMOND_PICKAXE:
                        meta.setDisplayName("§6Zombie Zapper");
                        break;
                    case GOLDEN_PICKAXE:
                        meta.setDisplayName("§6Gold Digger");
                        break;
                    case GOLDEN_SHOVEL:
                        meta.setDisplayName("§6Rainbow Rifle");
                        break;
                    case FLINT_AND_STEEL:
                        meta.setDisplayName("§6Double Barrel Shotgun");
                        break;
                    case IRON_HOE:
                        meta.setDisplayName("§6Shotgun");
                        break;
                }
                item.setItemMeta(meta);
            }
            Ultimates.putIfAbsent(item.getItemMeta().getCustomModelData(), 0);
            isReloading.putIfAbsent(item.getItemMeta().getCustomModelData(), false);
            if (item.getItemMeta().hasCustomModelData()) {
                magazines.putIfAbsent(item.getItemMeta().getCustomModelData(), 0L);
                isReloading.putIfAbsent(item.getItemMeta().getCustomModelData(), Boolean.TRUE);
                if (item.getType() == Material.FLINT_AND_STEEL && !(Boolean) isReloading.get(item.getItemMeta().getCustomModelData())) {
                    int period = 60;
                    int clipAmmo = 2;
                    switch (Ultimates.get(item.getItemMeta().getCustomModelData())) {
                        case 1, 2:
                            period = 50;
                            break;
                        case 3:
                            period = 46;
                            break;
                    }
                    new reload().reloadGun(item, isReloading, magazines, clipAmmo, period, player);
                }
                if (item.getType() == Material.GOLDEN_PICKAXE && !(Boolean) isReloading.get(item.getItemMeta().getCustomModelData())) {
                    int period = 30;
                    int clipAmmo = 7;
                    switch (Ultimates.get(item.getItemMeta().getCustomModelData())) {
                        case 1:
                            period = 28;
                            clipAmmo = 10;
                            break;
                        case 2:
                            period = 26;
                            clipAmmo = 13;
                            break;
                        case 3:
                            period = 24;
                            clipAmmo = 16;
                            break;
                        case 4:
                            period = 22;
                            clipAmmo = 20;
                            break;
                        case 5:
                            period = 20;
                            clipAmmo = 25;
                            break;
                    }
                    (new reload()).reloadGun(item, isReloading, magazines, clipAmmo, period, player);
                }
                if (item.getType() == Material.GOLDEN_SHOVEL && !isReloading.get(item.getItemMeta().getCustomModelData())) {
                    int period = 30;
                    int clipAmmo = 30;
                    switch (Ultimates.get(item.getItemMeta().getCustomModelData())) {
                        case 1 -> {
                            period = 20;
                            clipAmmo = 36;
                        }
                        case 2 -> {
                            period = 20;
                            clipAmmo = 39;
                        }
                        case 3 -> {
                            period = 20;
                            clipAmmo = 42;
                        }
                    }
                    (new reload()).reloadGun(item, isReloading, magazines, clipAmmo, period, player);
                }
                if (item.getType() == Material.DIAMOND_PICKAXE && !(Boolean) isReloading.get(item.getItemMeta().getCustomModelData()))
                    if (Ultimates.get(item.getItemMeta().getCustomModelData()) == 0) {
                        (new reload()).reloadGun(item, isReloading, magazines, 10L, 40L, player);
                    } else {
                        (new reload()).reloadGun(item, isReloading, magazines, 10L, 30L, player);
                    }
                if (item.getType() == Material.IRON_HOE && !(Boolean) isReloading.get(item.getItemMeta().getCustomModelData()))
                    if (Ultimates.get(item.getItemMeta().getCustomModelData()) == 0) {
                        (new reload()).reloadGun(item, isReloading, magazines, 5L, 30, player);
                    } else {
                        (new reload()).reloadGun(item, isReloading, magazines, 5L, 20, player);
                    }
            }
        }

    }
}
