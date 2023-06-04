package org.nacukat.zombiesnacukatedition.Guns.gunFunc.other;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.canWallShot;

public class get01Location {
    public Entity getLocation(Location location, Vector direction, double distance, int maxIterations){
        boolean findingBlock = true;
        boolean findingBlock1 = false;
        Entity hitEntity = null;
        World world = Bukkit.getWorld("world"); // プレイヤーのワールドを取得

        List<Location> positions = new ArrayList<>(); // 位置を格納するリスト

        for (int i = 0; i < maxIterations; i++) {
            Location newPosition = location.clone().add(direction.clone().multiply(distance * i));
            positions.add(newPosition);
            if(findingBlock){
                if(!newPosition.getBlock().getType().equals(Material.AIR)){
                    if(findingBlock1){

                    }
                    if(Arrays.stream(canWallShot).toList().contains(newPosition.getBlock().getType())){
//                        world.spawnParticle(Particle.REDSTONE,newPosition,10, new Particle.DustOptions(Color.LIME,1));

                        findingBlock =false;
                    }else {
//                        world.spawnParticle(Particle.REDSTONE,newPosition,10, new Particle.DustOptions(Color.YELLOW,1));
                        break;
                    }
                }

            }else {
                if(newPosition.getBlock().getType().equals(Material.AIR)){
                    RayTraceResult rayTraceResult = newPosition.getWorld().rayTrace(newPosition,direction,70,FluidCollisionMode.NEVER,true,0.2,entity -> (entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND && ((LivingEntity)entity).getHealth() != 0.0D ));
                    if(rayTraceResult != null){
                        List<LivingEntity> near = new ArrayList<>(newPosition.getNearbyLivingEntities(10.0D, entity -> (entity != null && entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND && entity.getHealth() != 0.0D)));
                        near.sort(Comparator.comparingDouble(entity -> entity.getLocation().distance(newPosition)));
                        if (near.size() > 0) {
                            BoundingBox box1 = new BoundingBox(newPosition.getX(), newPosition.getY(), newPosition.getZ(), newPosition.x(), newPosition.y(), newPosition.z());
                            BoundingBox box2 = ((LivingEntity)near.get(0)).getBoundingBox();
                            box2.expand(0.2D,0D,0.2D);
                            boolean intersects = (box1.getMinX() <= box2.getMaxX() && box1.getMaxX() >= box2.getMinX() && box1.getMinY() <= box2.getMaxY() && box1.getMaxY() >= box2.getMinY() && box1.getMinZ() <= box2.getMaxZ() && box1.getMaxZ() >= box2.getMinZ());
                            if (intersects) {
                                hitEntity = near.get(0);
                                break;
                            }
                        }
                        if(rayTraceResult.getHitEntity() != null){
//                            world.spawnParticle(Particle.REDSTONE,newPosition,10, new Particle.DustOptions(Color.RED,1));
                            hitEntity = rayTraceResult.getHitEntity();
                            break;
                        }else {
//                            world.spawnParticle(Particle.REDSTONE,newPosition,10, new Particle.DustOptions(Color.AQUA,1));
                        }
                    }else {
                        break;
                    }
                }
            }

        }
        return hitEntity;
    }
}
