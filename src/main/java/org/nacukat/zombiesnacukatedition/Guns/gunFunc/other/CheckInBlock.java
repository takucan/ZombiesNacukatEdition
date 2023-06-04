package org.nacukat.zombiesnacukatedition.Guns.gunFunc.other;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Arrays;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.canWallShot;

public class CheckInBlock {
    public RayTraceResult checkOppositeLocation(Player player, RayTraceResult rayTraceResult, Location hitLocation) {

        Vector direction = hitLocation.getDirection();
        double distance = 0.1;
        double maxDistance = 10000.0; // 最大探索距離（例として100ブロックまでとします）
        Location loc = hitLocation.clone();
        RayTraceResult result = rayTraceResult;
        if(!Arrays.asList(canWallShot).contains(rayTraceResult.getHitBlock().getType()))return result;

        for (double d = distance; d < maxDistance; d += distance) {

            loc = loc.add(direction.multiply(distance));
            result = player.getWorld().rayTrace(loc, player.getLocation().getDirection(), 70.0D, FluidCollisionMode.NEVER, true, 0.2D, entity -> (entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND && ((LivingEntity) entity).getHealth() != 0.0D && entity != player));
            player.getWorld().spawnParticle(Particle.REDSTONE,loc,0, new Particle.DustOptions(Color.RED,1));
            if (result != null) {
                if(result.getHitEntity() != null){
                    break;
                }
                if(result.getHitBlock()!= null){
                    loc = result.getHitPosition().toLocation(player.getWorld());
                }
            } else {
                result = rayTraceResult;
                break;
            }
        }
        // ブロックが存在しない場合の処理
        return result;
    }
}
