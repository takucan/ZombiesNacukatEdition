package org.nacukat.zombiesnacukatedition.Guns.gunFunc;

import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.*;

import java.util.*;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class RainbowRifle {
    public boolean rainbowRifle(final HashMap<Integer, Boolean> isShootingRR, HashMap<ItemStack, Long> lastShotTimes, final HashMap<Integer, Boolean> isReloading, final HashMap<Integer, Long> magazines, final Player player, final ItemStack item) {
        long clipSize = 30L;
        final Sound shootSound = Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR;
        final float volume = 0.4F;
        final float pich = 2.0F;
        long fireRate = 400L;
        double knockBack = 0.5D;
        double damage = 6.0D;
        double period = 30.0D;
        int burst = 2;
        Long lastShotTime = lastShotTimes.get(item);
        long currentTime = System.currentTimeMillis();
        Long magazine = magazines.get(item.getItemMeta().getCustomModelData());
        switch (Ultimates.get(item.getItemMeta().getCustomModelData())) {
            case 1:
                clipSize = 36L;
                fireRate = 300L;
                period = 20.0D;
                burst = 3;
                break;
            case 2:
                damage = 6.5D;
                clipSize = 39L;
                fireRate = 300L;
                period = 20.0D;
                burst = 3;
                break;
            case 3:
                damage = 7.0D;
                clipSize = 42L;
                fireRate = 300L;
                period = 20.0D;
                burst = 3;
                break;
        }
        if (HasQF.get(player.getName()))
            fireRate = (long)(fireRate * 0.75D);
        Boolean isShooting = isShootingRR.get(item.getItemMeta().getCustomModelData());
        isShootingRR.putIfAbsent(item.getItemMeta().getCustomModelData(), Boolean.TRUE);
        if (magazine == null) {
            magazine = clipSize;
            magazines.put(item.getItemMeta().getCustomModelData(), magazine);
            item.setAmount(Math.toIntExact(clipSize));
        }
        if (magazine > 0L) {
            if (magazine > clipSize) {
                magazine = clipSize;
                item.setAmount((int)clipSize);
            }
            if ((isShooting == null || !isShooting) && (
                    lastShotTime == null || currentTime - lastShotTime >= fireRate)) {
                isShootingRR.put(item.getItemMeta().getCustomModelData(), Boolean.TRUE);
                for (Player player1 : Bukkit.getServer().getOnlinePlayers())
                    player1.playSound(player, shootSound, volume, pich);
                String hitmessage = "§6+5 Gold";
                boolean intersect = false;

                String critmessage = "§6+7 Gold (Critical Hit)";
                new spawnParticle().rainbow(player);

                new rayTrace().shoot(player,item,5,7,damage,hitmessage,critmessage,knockBack,player.getEyeLocation().getDirection());
                if (item.getAmount() > 1)
                    item.setAmount(Math.toIntExact(magazines.get(item.getItemMeta().getCustomModelData())) - 1);
                magazine = magazine - 1L;
                magazines.put(item.getItemMeta().getCustomModelData(), magazine);
                lastShotTimes.put(item, currentTime);
                if (magazine <= 0L) {
                    isReloading.replace(item.getItemMeta().getCustomModelData(), Boolean.TRUE);
                    new reload().reloadGun(item, isReloading,  magazines, clipSize, (long)period, player);
                    isShootingRR.replace(item.getItemMeta().getCustomModelData(), Boolean.FALSE);
                    player.sendMessage("reload");
                }
                final int finalBurst = burst;
                final long finalClipSize = clipSize;
                final double finalPeriod = period;
                (new BukkitRunnable() {
                    long magazine = magazines.get(item.getItemMeta().getCustomModelData());

                    int count = 0;

                    public void run() {
                        if (count < finalBurst) {
                            double damage = 5.0D;
                            String hitmessage = "§6+5 Gold";
                            for (Player player1 : Bukkit.getServer().getOnlinePlayers())
                                player1.playSound(player, shootSound, volume, pich);
                            boolean intersect = false;

                            String critmessage = "§6+7 Gold (Critical Hit)";
                            new spawnParticle().rainbow(player);

                            new rayTrace().shoot(player,item,5,7,damage,hitmessage,critmessage,knockBack,player.getEyeLocation().getDirection());
                            if (item.getAmount() > 1)
                                item.setAmount(Math.toIntExact(magazines.get(item.getItemMeta().getCustomModelData())) - 1);
                            magazine--;
                            magazines.put(item.getItemMeta().getCustomModelData(), magazine);
                            if (magazine <= 0L) {
                                isReloading.replace(item.getItemMeta().getCustomModelData(), Boolean.TRUE);
                                new reload().reloadGun(item, isReloading, magazines, finalClipSize, (long)finalPeriod, player);
                                player.sendMessage("reload");
                                isShootingRR.replace(item.getItemMeta().getCustomModelData(), Boolean.FALSE);
                                cancel();
                            }
                            count++;
                        } else {
                            cancel();
                            isShootingRR.replace(item.getItemMeta().getCustomModelData(), Boolean.FALSE);
                        }
                    }
                }).runTaskTimer(plugin, fireRate / 50L / (burst + 1) + 2L, fireRate / 50L / (burst + 1) + 2L);
                isShootingRR.putIfAbsent(item.getItemMeta().getCustomModelData(), Boolean.FALSE);
            }
        }
        return false;
    }

}
