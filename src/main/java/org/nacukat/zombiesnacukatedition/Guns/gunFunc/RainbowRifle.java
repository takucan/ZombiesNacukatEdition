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
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.CheckInBlock;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.isCritical;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.reload;

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
        Long lastShotTime = lastShotTimes.get(item.getItemMeta().getCustomModelData());
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
                RayTraceResult rayTraceResult = player.getWorld().rayTrace(player.getEyeLocation(), player.getLocation().getDirection(), 50.0D, FluidCollisionMode.NEVER, true, 0.2D, entity -> (entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND && ((LivingEntity)entity).getHealth() != 0.0D && entity != player));

                if(rayTraceResult != null && rayTraceResult.getHitBlock()!=null){
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
                        boolean isCritical = (new isCritical()).critical(player, livingEntity, hitLocation, headLocation);
                        if (isCritical) {
                            damage *= 1.2D;
                            hitmessage = "§6+7 Gold (Critical Hit)";
                            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 1.5F);
                        } else {
                            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 2.0F);
                        }
                    } else if (player.getEyeLocation().getDirection().getY() > 0.0D) {
                        Random random = new Random();
                        int rand = random.nextInt(100);
                        if (rand <= 90) {
                            damage *= 1.2D;
                            hitmessage = "§6+7 Gold (Critical Hit)";
                            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 1.5F);
                        }
                    } else {
                        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 2.0F);
                    }
                    if (livingEntity.getNoDamageTicks() != 0 || livingEntity.getMaximumNoDamageTicks() != 0) {
                        livingEntity.setNoDamageTicks(0);
                        livingEntity.setMaximumNoDamageTicks(0);
                    }
                    player.sendMessage(hitmessage);
                    if (!Arrays.asList(bosses).contains(livingEntity.getName())) {
                        livingEntity.damage(damage, player);
                        org.bukkit.util.Vector velocity = player.getLocation().getDirection().multiply(knockBack);
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
                            RayTraceResult rayTraceResult = player.getWorld().rayTrace(player.getEyeLocation(), player.getLocation().getDirection(), 50.0D, FluidCollisionMode.NEVER, true, 0.2D, entity -> (entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND && ((LivingEntity)entity).getHealth() != 0.0D && entity != player));

                            if(rayTraceResult != null && rayTraceResult.getHitBlock()!=null){
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
                                    boolean isCritical = (new isCritical()).critical(player, livingEntity, hitLocation, headLocation);
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
                                player.sendMessage(hitmessage);
                                if (!Arrays.asList(bosses).contains(livingEntity.getCustomName())) {
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
