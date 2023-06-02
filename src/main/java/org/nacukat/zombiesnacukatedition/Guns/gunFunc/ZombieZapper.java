package org.nacukat.zombiesnacukatedition.Guns.gunFunc;

import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.CheckInBlock;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.isCritical;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.reload;

import java.util.*;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class ZombieZapper {

    public boolean zombieZapper(HashMap<ItemStack, Long> lastShotTimes, HashMap<Integer, Boolean> isReloading, HashMap<Integer, Long> magazines, Player player, ItemStack item) {
        long clipSize = 10L;
        double damage = 12.0D;
        long period = 40L;
        Long lastShotTime = lastShotTimes.get(item);
        long currentTime = System.currentTimeMillis();
        Long magazine = magazines.get(item.getItemMeta().getCustomModelData());
        int fireRate = 500;
        if (Ultimates.get(item.getItemMeta().getCustomModelData()) == 1) {
            damage = 18.0D;
            period = 30L;
        }
        if (HasQF.get(player.getName()))
            fireRate = (int)(fireRate * 0.75D);
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
                for (Player player1 : Bukkit.getServer().getOnlinePlayers())
                    player1.playSound(player, Sound.ITEM_FLINTANDSTEEL_USE, 0.4F, 0.5F);
                String hitmessage = "§6+15 Gold";
                boolean intersect = false;
                RayTraceResult rayTraceResult = player.getWorld().rayTrace(player.getEyeLocation(), player.getLocation().getDirection(), 50.0D, FluidCollisionMode.NEVER, true, 0.2D, entity -> (entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND && ((LivingEntity)entity).getHealth() != 0.0D && entity != player));

                if(rayTraceResult.getHitBlock() != null){
                    rayTraceResult = new CheckInBlock().checkOppositeLocation(player,rayTraceResult,rayTraceResult.getHitPosition().toLocation(player.getWorld()));
                }
                Entity hitEntity = rayTraceResult.getHitEntity();
                List<LivingEntity> near = new ArrayList<>(player.getLocation().getNearbyLivingEntities(10.0D, entity -> (entity != null && entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND && entity.getHealth() != 0.0D && entity != player)));
                near.sort(Comparator.comparingDouble(entity -> entity.getLocation().distance(player.getLocation())));

                if (near.size() > 0) {
                    BoundingBox box1 = player.getBoundingBox();
                    BoundingBox box2 = near.get(0).getBoundingBox();
                    box2.expand(0.2D,0D,0.2D);
                    boolean intersects = (box1.getMinX() <= box2.getMaxX() && box1.getMaxX() >= box2.getMinX() && box1.getMinY() <= box2.getMaxY() && box1.getMaxY() >= box2.getMinY() && box1.getMinZ() <= box2.getMaxZ() && box1.getMaxZ() >= box2.getMinZ());
                    if (intersects) {
                        intersect = true;
                        hitEntity = near.get(0);
                    }
                }
                if (hitEntity != null) {
                    LivingEntity livingEntity = (LivingEntity)hitEntity;
                    if (!intersect) {
                        Location hitLocation = rayTraceResult.getHitPosition().toLocation(player.getWorld());
                        Location headLocation = livingEntity.getEyeLocation();
                        boolean isCritical = new isCritical().critical(player, livingEntity, hitLocation, headLocation);
                        if (isCritical) {
                            damage *= 1.2D;
                            hitmessage = "§6+20 Gold (Critical Hit)";
                            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 1.5F);
                        } else {
                            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 2.0F);
                        }
                    } else if (player.getEyeLocation().getDirection().getY() > 0.0D) {
                        Random random = new Random();
                        int rand = random.nextInt(100);
                        if (rand <= 90) {
                            damage *= 1.2D;
                            hitmessage = "§6+20 Gold (Critical Hit)";
                            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 1.5F);
                        }
                    } else {
                        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 2.0F);
                    }
                    if (livingEntity.getNoDamageTicks() != 0 || livingEntity.getMaximumNoDamageTicks() != 0) {
                        livingEntity.setNoDamageTicks(0);
                        livingEntity.setMaximumNoDamageTicks(0);
                    }
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
                                org.bukkit.util.Vector velocity = hitEntity.getLocation().subtract(entities.get(i).getLocation()).toVector().multiply(-0.2D);
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
                    player.sendMessage(hitmessage);
                    if (!Arrays.asList(bosses).contains(livingEntity.getName())) {
                        livingEntity.damage(damage, player);
                        Vector velocity = player.getLocation().getDirection().multiply(0.5D);
                        livingEntity.setVelocity(velocity);
                    } else {
                        livingEntity.damage(damage);
                        livingEntity.setKiller(player);
                    }
                }
                if (item.getAmount() > 1)
                    item.setAmount(Math.toIntExact(magazines.get(item.getItemMeta().getCustomModelData())) - 1);
                magazine = magazine - 1L;
                magazines.put(item.getItemMeta().getCustomModelData(), magazine);
                lastShotTimes.put(item, currentTime);
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
