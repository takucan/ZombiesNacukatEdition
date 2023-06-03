package org.nacukat.zombiesnacukatedition.Guns.gunFunc.other;

import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.*;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.Gold;
import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.bosses;

public class rayTrace {
    public LivingEntity shoot(Player player,int gold,int critgold,double damage,String hitmessage,String critmessage,double knockBack,Vector direction){

        boolean intersect = false;
        RayTraceResult rayTraceResult = player.getWorld().rayTrace(player.getEyeLocation(), direction, 70.0D, FluidCollisionMode.NEVER, true, 0.2D, entity -> (entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND && ((LivingEntity)entity).getHealth() != 0.0D && entity != player));
        if(rayTraceResult == null)return null;
        if(rayTraceResult.getHitBlock() != null){
            RayTraceResult raytrace = new CheckInBlock().checkOppositeLocation(player,rayTraceResult,rayTraceResult.getHitPosition().toLocation(player.getWorld()));
            rayTraceResult = raytrace;
        }


        Entity hitEntity = rayTraceResult.getHitEntity();
        List<LivingEntity> near = new ArrayList<>(player.getLocation().getNearbyLivingEntities(10.0D, entity -> (entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND && entity.getHealth() != 0.0D && entity != player)));
        near.sort(Comparator.comparingDouble(entity -> entity.getLocation().distance(player.getLocation())));
        if (near.size() > 0) {
            BoundingBox box1 = player.getBoundingBox();
            BoundingBox box2 = ((LivingEntity)near.get(0)).getBoundingBox();
            box2.expand(0.2D,0D,0.2D);
            boolean intersects = (box1.getMinX() <= box2.getMaxX() && box1.getMaxX() >= box2.getMinX() && box1.getMinY() <= box2.getMaxY() && box1.getMaxY() >= box2.getMinY() && box1.getMinZ() <= box2.getMaxZ() && box1.getMaxZ() >= box2.getMinZ());
            if (intersects) {
                intersect = true;
                hitEntity = (Entity)near.get(0);
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
                    player.playSound((Entity)player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 1.5F);
                    Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())+critgold);
                } else {
                    player.playSound((Entity)player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 2.0F);
                    Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())+gold);
                }
            } else if (player.getEyeLocation().getDirection().getY() > 0.0D) {
                Random random = new Random();
                int rand = random.nextInt(100);
                if (rand <= 90) {
                    damage *= 1.2D;
                    hitmessage = critmessage;
                    player.playSound((Entity)player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 1.5F);
                    Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())+critgold);
                }
            } else {
                player.playSound((Entity)player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 2.0F);
                Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())+gold);
            }
            if (livingEntity.getNoDamageTicks() != 0 || livingEntity.getMaximumNoDamageTicks() != 0) {
                livingEntity.setNoDamageTicks(0);
                livingEntity.setMaximumNoDamageTicks(0);
            }
            player.sendMessage(hitmessage);
            if (!Arrays.asList(bosses).contains(livingEntity.getCustomName())) {
                livingEntity.damage(damage, (Entity)player);
                Vector velocity = player.getLocation().getDirection().multiply(knockBack);
                livingEntity.setVelocity(velocity);
            } else {
                livingEntity.damage(damage);
                livingEntity.setKiller(player);
            }
        }
        return (LivingEntity) rayTraceResult.getHitEntity();
    }
}
