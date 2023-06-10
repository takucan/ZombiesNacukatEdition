package org.nacukat.zombiesnacukatedition.Guns.gunFunc.other;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.*;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class rayTrace {
        public LivingEntity shoot(Player player, ItemStack item, int gold, int critgold, double damage, String hitmessage, String critmessage, double knockBack, Vector direction){
            RayTraceResult rayTraceResult = player.getWorld().rayTrace(player.getEyeLocation(), direction, 70.0D, FluidCollisionMode.NEVER, true, 0.2D, entity -> (entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND && ((LivingEntity)entity).getHealth() != 0.0D && entity != player));
            lastShotTimes.putIfAbsent(item.getItemMeta().getCustomModelData(),System.currentTimeMillis());
            if (totalBullets.get(item.getItemMeta().getCustomModelData()) > 0){
                if(item.getType() != Material.FLINT_AND_STEEL&&item.getType() != Material.IRON_HOE){
                    totalBullets.put(item.getItemMeta().getCustomModelData(),totalBullets.get(item.getItemMeta().getCustomModelData())-1);
                    player.setExp(1);
                    player.setLevel(totalBullets.get(item.getItemMeta().getCustomModelData()));
                    if(isCounting.get(player.getUniqueId())){

                        if(player.getInventory().getHeldItemSlot() < 5&&player.getInventory().getHeldItemSlot()>0) {
                            slotShoots.putIfAbsent(player.getUniqueId(), new ArrayList<>(Arrays.asList(0L, 0L, 0L, 0L)));
                            List<Long> slotShootList = new ArrayList<>(slotShoots.get(player.getUniqueId()));
                            slotShootList.set(player.getInventory().getHeldItemSlot()-1, slotShootList.get(player.getInventory().getHeldItemSlot()-1) + 1);
                            slotShoots.put(player.getUniqueId(), slotShootList);
                        }

                        gunShoots.putIfAbsent(player.getUniqueId(),new ArrayList<>(Arrays.asList(0L,0L,0L,0L,0L,0L)));
                        List<Long> gunShootList = new ArrayList<>(gunShoots.get(player.getUniqueId()));
                        gunShootList.set(guns.indexOf(item.getType()),gunShootList.get(guns.indexOf(item.getType()))+1);
                        gunShoots.put(player.getUniqueId(),gunShootList);
                    }
                }

                boolean intersect = false;
                if(rayTraceResult == null)return null;
                Entity hitEntity = rayTraceResult.getHitEntity();
                Entity newRayTrace = new get01Location().getLocation(player.getEyeLocation(),player.getEyeLocation().getDirection(),0.1,700);
                if(newRayTrace != null){
                    hitEntity = newRayTrace;
                }
                //        if(rayTraceResult.getHitBlock() != null){
                //            rayTraceResult = new CheckInBlock().checkOppositeLocation(player,rayTraceResult,rayTraceResult.getHitPosition().toLocation(player.getWorld()));
                //        }


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
                            hitmessage = critmessage;
                            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 1.5F);
                            Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())+critgold);
                        } else {
                            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 2.0F);
                            Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())+gold);
                        }
                    } else if (player.getEyeLocation().getDirection().getY() > 0.0D) {
                        Random random = new Random();
                        int rand = random.nextInt(100);
                        if (rand <= 90) {
                            damage *= 1.2D;
                            hitmessage = critmessage;
                            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 1.5F);
                            Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())+critgold);
                        }
                    } else {
                        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 2.0F);
                        Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())+gold);
                    }
                    if (livingEntity.getNoDamageTicks() != 0 || livingEntity.getMaximumNoDamageTicks() != 0) {
                        livingEntity.setNoDamageTicks(0);
                        livingEntity.setMaximumNoDamageTicks(0);
                    }
                    player.sendMessage(hitmessage);
                    if (!Arrays.asList(bosses).contains(livingEntity.getCustomName())) {
                        livingEntity.damage(damage, player);
                        Vector velocity = player.getLocation().getDirection().multiply(knockBack);
                        livingEntity.setVelocity(velocity);
                    } else {
                        livingEntity.damage(damage);
                        livingEntity.setKiller(player);
                    }
                }
            }

            return (LivingEntity) rayTraceResult.getHitEntity();
        }
}
