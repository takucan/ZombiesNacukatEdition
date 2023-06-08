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
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.CheckInBlock;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.isCritical;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.reload;

import java.util.*;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.HasQF;
import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.bosses;

public class basic {
  public boolean pistol_Other(HashMap<ItemStack, Long> lastShotTimes, HashMap<Integer, Boolean> isReloading, HashMap<Integer, Boolean> isShootingRR, HashMap<Integer, Long> magazines, long clipSize, Player player, ItemStack item, Sound shootSound, float volume, float pich, long fireRate, double knockBack, double damage, double period) {
    Long lastShotTime = lastShotTimes.get(item);
    long currentTime = System.currentTimeMillis();
    Long magazine = magazines.get(Integer.valueOf(item.getItemMeta().getCustomModelData()));
    if ((HasQF.get(player.getName())).booleanValue())
      fireRate = (long)(fireRate * 0.75D); 
    if (magazine == null) {
      magazine = Long.valueOf(clipSize);
      magazines.put(Integer.valueOf(item.getItemMeta().getCustomModelData()), magazine);
      item.setAmount(Math.toIntExact(clipSize));
    } 
    if (magazine.longValue() <= 0L) {
      isReloading.replace(Integer.valueOf(item.getItemMeta().getCustomModelData()), Boolean.valueOf(true));
      boolean a = (new reload()).reloadGun(item, isReloading, magazines, clipSize, (long)period, player);
      player.sendMessage("reload");
    } 
    if (magazine.longValue() > 0L) {
      if (magazine.longValue() > clipSize) {
        magazine = Long.valueOf(clipSize);
        item.setAmount((int)clipSize);
      } 
      if (lastShotTime == null || currentTime - lastShotTime.longValue() >= fireRate) {
        for (Player player1 : Bukkit.getServer().getOnlinePlayers())
          player1.playSound(player, shootSound, volume, pich);
        if (lastShotTime != null);
        String hitmessage = "Hit";
        boolean intersect = false;
        RayTraceResult rayTraceResult = player.getWorld().rayTrace(player.getEyeLocation(), player.getLocation().getDirection(), 50.0D, FluidCollisionMode.NEVER, true, 0.2D, entity -> (entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND && ((LivingEntity)entity).getHealth() != 0.0D && entity != player));

        if(rayTraceResult.getHitBlock() != null){
//          RayTraceResult raytrace = new CheckInBlock().checkOppositeLocation(player,rayTraceResult,rayTraceResult.getHitPosition().toLocation(player.getWorld()));
//          rayTraceResult = raytrace;
        }
        Entity hitEntity = rayTraceResult.getHitEntity();
        List<LivingEntity> near = new ArrayList<>(player.getLocation().getNearbyLivingEntities(10.0D, entity -> (entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND && entity.getHealth() != 0.0D && entity != player)));
        near.sort(Comparator.comparingDouble(entity -> entity.getLocation().distance(player.getLocation())));
        if (near.size() > 0) {
          BoundingBox box1 = player.getBoundingBox();
          BoundingBox box2 = near.get(0).getBoundingBox();
          box1.expand(0.1D);
          box2.expand(0.2D);
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
            Vector velocity = player.getLocation().getDirection().multiply(knockBack);
            livingEntity.setVelocity(velocity);
            livingEntity.setVelocity(velocity);
          } else {
            livingEntity.damage(damage);
            livingEntity.setKiller(player);
          } 
        } 
        if (item.getAmount() > 1)
          item.setAmount(Math.toIntExact(magazines.get(Integer.valueOf(item.getItemMeta().getCustomModelData())).longValue()) - 1);
        magazine = Long.valueOf(magazine.longValue() - 1L);
        magazines.put(Integer.valueOf(item.getItemMeta().getCustomModelData()), magazine);
        lastShotTimes.put(item, Long.valueOf(currentTime));
        if (magazine.longValue() <= 0L) {
          isReloading.replace(Integer.valueOf(item.getItemMeta().getCustomModelData()), Boolean.valueOf(true));
          boolean a = (new reload()).reloadGun(item, isReloading, magazines, clipSize, (long)period, player);
          player.sendMessage("reload");
        } 
      } 
    } 
    return true;
  }
}
