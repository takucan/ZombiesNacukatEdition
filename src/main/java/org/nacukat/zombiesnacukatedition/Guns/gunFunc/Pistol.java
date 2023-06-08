package org.nacukat.zombiesnacukatedition.Guns.gunFunc;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.rayTrace;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.reload;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.spawnParticle;

import java.util.HashMap;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class Pistol {
    public boolean Pistol( HashMap<ItemStack, Long> lastShotTimes, final HashMap<Integer, Boolean> isReloading, final HashMap<Integer, Long> magazines, final Player player, final ItemStack item) {
        long clipSize = 10L;
        final Sound shootSound = Sound.ENTITY_IRON_GOLEM_HURT;
        final float volume = 0.4F;
        final float pich = 2.0F;
        long fireRate = 500L;
        double knockBack = 0.5D;
        double damage = 6.0D;
        double period = 30.0D;
        int burst = 0;
        Long lastShotTime = lastShotTimes.get(item);
        long currentTime = System.currentTimeMillis();
        Long magazine = magazines.get(item.getItemMeta().getCustomModelData());


        if (Ultimates.get(item.getItemMeta().getCustomModelData()) == 1) {
            period = 20L;
            clipSize = 14;
            fireRate = 400L;
            burst = 1;
        }
        if (HasQF.get(player.getName()))
            fireRate = (long)(fireRate * 0.75D);
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
            if (lastShotTime == null || currentTime - lastShotTime >= fireRate) {
                lastShotTimes.put(item,currentTime);
                for (Player player1 : Bukkit.getServer().getOnlinePlayers())
                    player1.playSound(player, shootSound, volume, pich);
                String hitmessage = "§6+10 Gold";
                boolean intersect = false;

                String critmessage = "§6+15 Gold (Critical Hit)";
                new spawnParticle().spawn(Particle.CRIT,null,player);

                new rayTrace().shoot(player,item,10,15,damage,hitmessage,critmessage,knockBack,player.getEyeLocation().getDirection());
                if (item.getAmount() > 1)
                    item.setAmount(Math.toIntExact(magazines.get(item.getItemMeta().getCustomModelData())) - 1);
                magazine = magazine - 1L;
                magazines.put(item.getItemMeta().getCustomModelData(), magazine);
                if (magazine <= 0L) {
                    isReloading.replace(item.getItemMeta().getCustomModelData(), Boolean.TRUE);
                    new reload().reloadGun(item, isReloading,  magazines, clipSize, (long)period, player);
                    player.sendMessage("reload");
                }
                if(Ultimates.get(item.getItemMeta().getCustomModelData()) == 1&&magazine > 0){
                    long finalClipSize1 = clipSize;
                    double finalPeriod1 = period;
                    new BukkitRunnable() {
                        long magazine = magazines.get(item.getItemMeta().getCustomModelData());

                        int count = 0;

                        public void run() {
                            for (Player player1 : Bukkit.getServer().getOnlinePlayers())
                                player1.playSound(player, shootSound, volume, pich);
                            String hitmessage = "§6+10 Gold";
                            boolean intersect = false;

                            String critmessage = "§6+15 Gold (Critical Hit)";
                            new spawnParticle().spawn(Particle.CRIT,null,player);

                            new rayTrace().shoot(player,item,10,15,damage,hitmessage,critmessage,knockBack,player.getEyeLocation().getDirection());
                            if (item.getAmount() > 1)
                                item.setAmount(Math.toIntExact(magazines.get(item.getItemMeta().getCustomModelData())) - 1);
                            magazine = magazine - 1L;
                            magazines.put(item.getItemMeta().getCustomModelData(), magazine);
                            if (magazine <= 0L) {
                                isReloading.replace(item.getItemMeta().getCustomModelData(), Boolean.TRUE);
                                new reload().reloadGun(item, isReloading,  magazines, finalClipSize1, (long) finalPeriod1, player);
                                player.sendMessage("reload");
                            }
                        }
                    }.runTaskLater(plugin, 2 );
                }
                lastShotTimes.put(item, currentTime);

            }
        }
        return false;
    }

}
