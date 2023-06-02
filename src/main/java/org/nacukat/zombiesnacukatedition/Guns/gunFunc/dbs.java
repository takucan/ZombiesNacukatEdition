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

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class dbs {
  public boolean doubleBarrel(HashMap<ItemStack, Long> lastShotTimes, HashMap<Integer, Boolean> isReloading, HashMap<Integer, Long> magazines, Player player, ItemStack item) {
    Long lastShotTime = lastShotTimes.get(item);
    Long magazine = magazines.get(Integer.valueOf(item.getItemMeta().getCustomModelData()));
    long clipSize = 2L;
    long currentTime = System.currentTimeMillis();
    long damage = 7L;
    int period = 60;
    long fireRate = 300L;
    double knockBack = 0.5D;
    switch (Ultimates.get(Integer.valueOf(item.getItemMeta().getCustomModelData())).intValue()) {
      case 1:
        damage = 7L;
        fireRate = 300L;
        period = 50;
        break;
      case 2:
        damage = 8L;
        fireRate = 300L;
        period = 50;
        break;
      case 3:
        damage = 8L;
        fireRate = 300L;
        period = 46;
        break;
    } 
    if (HasQF.get(player.getName()).booleanValue())
      fireRate = (long)(fireRate * 0.75D); 
    if (magazine == null) {
      magazine = Long.valueOf(clipSize);
      magazines.put(Integer.valueOf(item.getItemMeta().getCustomModelData()), magazine);
      item.setAmount(Math.toIntExact(clipSize));
    } 
    if (magazine.longValue() <= 0L) {
      isReloading.replace(Integer.valueOf(item.getItemMeta().getCustomModelData()), Boolean.valueOf(true));
      boolean a = (new reload()).reloadGun(item, isReloading, magazines, clipSize, period, player);
      player.sendMessage("reload");
    } 
    if (magazine.longValue() > 0L) {
      if (magazine.longValue() > clipSize) {
        magazine = Long.valueOf(clipSize);
        item.setAmount((int)clipSize);
      } 
      if (lastShotTime == null || currentTime - lastShotTime.longValue() >= fireRate) {
        for (Player player1 : Bukkit.getServer().getOnlinePlayers())
          player1.playSound((Entity)player, Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 0.6F, 1.2F); 
        if (lastShotTime != null);
        String hitmessage = "§6+8 Gold";
        for (int i = 0; i <= 10; i++) {
          Vector direction = player.getEyeLocation().getDirection();
          double randomYawOffset = Math.toRadians(Math.random() * 20.0D - 10.0D);
          double randomPitchOffset = Math.toRadians(Math.random() * 20.0D - 10.0D);
          direction.rotateAroundY(randomYawOffset);
          direction.rotateAroundX(randomPitchOffset);
          boolean intersect = false;
          RayTraceResult rayTraceResult = player.getWorld().rayTrace(player.getEyeLocation(), player.getLocation().getDirection(), 50.0D, FluidCollisionMode.NEVER, true, 0.2D, entity -> (entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND && ((LivingEntity)entity).getHealth() != 0.0D && entity != player));

          if(rayTraceResult.getHitBlock() != null){
            RayTraceResult raytrace = new CheckInBlock().checkOppositeLocation(player,rayTraceResult,rayTraceResult.getHitPosition().toLocation(player.getWorld()));
            rayTraceResult = raytrace;
          }
          Entity hitEntity = rayTraceResult.getHitEntity();
          List<LivingEntity> near = new ArrayList<>(player.getLocation().getNearbyLivingEntities(10.0D, entity -> (entity != null && entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ARMOR_STAND && entity.getHealth() != 0.0D && entity != player)));
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
                damage = (long)(damage * 1.2D);
                hitmessage = "§6+12 Gold (Critical Hit)";
                player.playSound((Entity)player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 1.5F);
              } else {
                player.playSound((Entity)player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 2.0F);
              } 
            } else if (player.getEyeLocation().getDirection().getY() > 0.0D) {
              Random random = new Random();
              int rand = random.nextInt(100);
              if (rand <= 90) {
                damage = (long)(damage * 1.2D);
                hitmessage = "§6+12 Gold (Critical Hit)";
                player.playSound((Entity)player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 1.5F);
              } 
            } else {
              player.playSound((Entity)player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.6F, 2.0F);
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
              Vector velocity = player.getLocation().getDirection().multiply(0);
              livingEntity.damage(damage);
              livingEntity.setKiller(player);
            } 
          } 
        } 
        if (item.getAmount() > 1)
          item.setAmount(Math.toIntExact(((Long)magazines.get(Integer.valueOf(item.getItemMeta().getCustomModelData()))).longValue()) - 1); 
        magazine = Long.valueOf(magazine.longValue() - 1L);
        magazines.put(Integer.valueOf(item.getItemMeta().getCustomModelData()), magazine);
        lastShotTimes.put(item, Long.valueOf(currentTime));
        if (magazine.longValue() <= 0L) {
          isReloading.replace(Integer.valueOf(item.getItemMeta().getCustomModelData()), Boolean.valueOf(true));
          boolean a = (new reload()).reloadGun(item, isReloading, magazines, clipSize, period, player);
          player.sendMessage("reload");
        } 
      } 
    } 
    return false;
  }
}
