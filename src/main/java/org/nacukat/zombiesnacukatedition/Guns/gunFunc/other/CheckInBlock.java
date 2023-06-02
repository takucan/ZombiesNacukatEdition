package org.nacukat.zombiesnacukatedition.Guns.gunFunc.other;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
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
//            loc.add(direction.multiply(distance));

//            result = world.rayTrace(loc, player.getLocation().getDirection(), 50.0D, FluidCollisionMode.NEVER, true, 0.2D, entity -> (entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND && ((LivingEntity) entity).getHealth() != 0.0D && entity != player));
//            if (result.getHitBlock() != null) {
//                // ブロックにぶつかった場合の処理
//                loc = loc.add(direction.multiply(distance));
//                loc = rayTraceResult.getHitPosition().toLocation(player.getWorld());
//                loc = result.getHitBlock().getLocation().add(result.getHitBlockFace().getDirection());
//                player.sendMessage("1");
//            } else {
//                // ブロックが存在しない場合の処理
//                player.sendMessage("2");
//                break;
//            }
//            player.sendMessage("3");
//
//        }            loc.add(direction.multiply(distance));

            // ブロックが存在する場合の処理
            loc = loc.add(direction.multiply(distance));
            result = player.getWorld().rayTrace(loc, player.getLocation().getDirection(), 70.0D, FluidCollisionMode.NEVER, true, 0.2D, entity -> (entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND && ((LivingEntity) entity).getHealth() != 0.0D && entity != player));
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
