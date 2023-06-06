package org.nacukat.zombiesnacukatedition.Skill;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Collection;

public class RightLightningRod {
    public void lightningRod(Player player){
        if(player.hasCooldown(Material.BLAZE_ROD))return;
        RayTraceResult rayTraceResult = player.getWorld().rayTrace(player.getEyeLocation(), player.getLocation().getDirection(), 7, FluidCollisionMode.NEVER, true, 0.2D, entity -> (entity instanceof LivingEntity && !(entity instanceof Player) && !(entity instanceof ArmorStand) && ((LivingEntity)entity).getHealth() != 0.0D));

        Location loc1 = player.getLocation();
        Location loc2;
        Collection<LivingEntity> nearPlayer = loc1.getNearbyLivingEntities(5, entity -> (entity instanceof LivingEntity && !(entity instanceof Player) && !(entity instanceof ArmorStand) && ((LivingEntity)entity).getHealth() != 0.0D));
        Collection<LivingEntity> nearHitLoc;
        if(rayTraceResult != null){
            loc2= rayTraceResult.getHitPosition().toLocation(player.getWorld());
            nearHitLoc = loc2.getNearbyLivingEntities(4.5,entity -> (entity instanceof LivingEntity && !(entity instanceof Player) && !(entity instanceof ArmorStand) && ((LivingEntity)entity).getHealth() != 0.0D));
        }else {
            Location eyeLocation = player.getEyeLocation();
            Vector direction = eyeLocation.getDirection();

            double distance = 7;
            loc2 = eyeLocation.add(direction.multiply(distance));
            nearHitLoc = loc2.getNearbyLivingEntities(5,entity -> (entity instanceof LivingEntity && !(entity instanceof Player) && !(entity instanceof ArmorStand) && ((LivingEntity)entity).getHealth() != 0.0D));
        }
        if(nearHitLoc.size() == 0&&nearPlayer.size() == 0)return;
        if (nearPlayer.size()>=nearHitLoc.size()){
            LightningStrike lightningStrike = player.getWorld().strikeLightningEffect(nearHitLoc.stream().toList().get(0).getLocation());
            int i = 0;
            for(LivingEntity entity :nearHitLoc){
                entity.setKiller(player);
                entity.damage(80);
                i++;
            }
            player.sendMessage("Hit "+i+" enemies");
            player.setCooldown(Material.BLAZE_ROD,540);
        }else {
            LightningStrike lightningStrike = player.getWorld().strikeLightningEffect(nearHitLoc.stream().toList().get(0).getLocation());
            int i = 0;
            for(LivingEntity entity :nearHitLoc){
                entity.setKiller(player);
                entity.damage(80);
                i++;
            }
            player.sendMessage("Hit "+i+" enemies");
            player.setCooldown(Material.BLAZE_ROD,540);
        }

    }
}
