package org.nacukat.zombiesnacukatedition.Guns.gunFunc;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.rayTrace;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.reload;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.spawnParticle;

import java.util.*;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class ZombieZapper {

    public boolean zombieZapper(HashMap<Integer, Long> lastShotTimes, HashMap<Integer, Boolean> isReloading, HashMap<Integer, Long> magazines, Player player, ItemStack item) {
        long clipSize = 10L;
        double damage = 12.0D;
        long period = 40L;
        lastShotTimes.putIfAbsent(item.getItemMeta().getCustomModelData(),System.currentTimeMillis());
        long lastShotTime = lastShotTimes.get(item.getItemMeta().getCustomModelData());
        long currentTime = System.currentTimeMillis();
        magazines.putIfAbsent(item.getItemMeta().getCustomModelData(),clipSize);
        long magazine = magazines.get(item.getItemMeta().getCustomModelData());
        int fireRate = 500;
        if (Ultimates.get(item.getItemMeta().getCustomModelData()) == 1) {
            damage = 18.0D;
            period = 30L;
        }
        if (HasQF.get(player.getName()))
            fireRate = (int)(fireRate * 0.75D);
        if (magazine > 0L) {
            if (magazine > clipSize) {
                magazine = clipSize;
                item.setAmount((int)clipSize);
            }
            if (currentTime - lastShotTime >= fireRate) {
                for (Player player1 : Bukkit.getServer().getOnlinePlayers())
                    player1.playSound(player, Sound.ITEM_FLINTANDSTEEL_USE, 0.4F, 0.5F);
                String hitmessage = "§6+15 Gold";
                boolean intersect = false;
                String critmessage = "§6+20 Gold (Critical Hit)";
                Particle.DustOptions dustOptions = null;
                Particle particle = Particle.CRIT_MAGIC;
                new spawnParticle().spawn(particle,dustOptions,player);

                LivingEntity livingEntity = new rayTrace().shoot(player,item,15,20,damage,hitmessage,critmessage,0.5,player.getEyeLocation().getDirection());
                if(livingEntity != null){
                    int count = 0;
                    List<LivingEntity> entities = new ArrayList<>(livingEntity.getLocation().getNearbyLivingEntities(3.0D, 3.0D, 3.0D));
                    entities.sort(Comparator.comparingDouble(entity -> entity.getLocation().distance(livingEntity.getLocation())));
                    for (int i = 0; i < 5 && i < entities.size() &&
                            count < 5; i++) {
                        if (entities.get(i).getType() != EntityType.PLAYER && entities.get(i).getType() != EntityType.ARMOR_STAND && entities.get(i) != livingEntity) {
                            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.2F, 2.0F);
                            if (entities.get(i).getNoDamageTicks() != 0 || entities.get(i).getMaximumNoDamageTicks() != 0) {
                                entities.get(i).setNoDamageTicks(0);
                                entities.get(i).setMaximumNoDamageTicks(0);
                            }
                            if (!Arrays.asList(bosses).contains(entities.get(i).getName())) {
                                org.bukkit.util.Vector velocity = livingEntity.getLocation().subtract(entities.get(i).getLocation()).toVector().multiply(-0.2D);
                                entities.get(i).setVelocity(velocity);
                            }
                            entities.get(i).damage(damage);
                            entities.get(i).setKiller(player);
                            HasFB.putIfAbsent(player.getName(), Boolean.FALSE);
                            if (HasFB.get(player.getName())) {
                                PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 60, 1, false, false);
                                entities.get(i).addPotionEffect(effect);
                            }
                            player.sendMessage("§6+15 Gold");
                            count++;
                        }
                        if (entities.get(i) instanceof Player || entities.get(i) instanceof ArmorStand) {
                            entities.remove(i);
                            i--;
                            count--;
                        }
                    }
                }

//                if (item.getAmount() > 1)
//                    item.setAmount(Math.toIntExact(magazines.get(item.getItemMeta().getCustomModelData())) - 1);
                magazine = magazine - 1L;
                if(magazine > 0)item.setAmount((int) magazine);
                magazines.put(item.getItemMeta().getCustomModelData(), magazine);
                lastShotTimes.put(item.getItemMeta().getCustomModelData(), currentTime);
                if (magazine <= 0L) {
                    isReloading.replace(item.getItemMeta().getCustomModelData(), true);
                    boolean a = new reload().reloadGun(item, isReloading, magazines, 10L, period, player);
                    player.sendMessage("reload");
                }
            }

        }
        return false;
    }
}
