package org.nacukat.zombiesnacukatedition.Guns.gunFunc.other;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class get01Location {
    public RayTraceResult getLocation(Location location, Vector direction, double distance, int maxIterations){
        boolean findingBlock = true;
        RayTraceResult rayTraceResult = null;
        World world = Bukkit.getWorld("world"); // プレイヤーのワールドを取得

        List<Location> positions = new ArrayList<>(); // 位置を格納するリスト

        for (int i = 0; i < maxIterations; i++) {
            Location newPosition = location.clone().add(direction.clone().multiply(distance * i));
            positions.add(newPosition);
            if(findingBlock){
                if(!newPosition.getBlock().getType().equals(Material.AIR)){
                    findingBlock =false;
                }
            }else {
                if(newPosition.getBlock().getType().equals(Material.AIR)){
                    rayTraceResult = newPosition.getWorld().rayTrace(newPosition,direction,70,FluidCollisionMode.NEVER,true,0.2,entity -> (entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND && ((LivingEntity)entity).getHealth() != 0.0D ));
                    if(rayTraceResult != null&&rayTraceResult.getHitEntity() != null){
                        break;
                    }else {
                        findingBlock =true;
                    }
                }
            }

        }
        return rayTraceResult;
    }
}
