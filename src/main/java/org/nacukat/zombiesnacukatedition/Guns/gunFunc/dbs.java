package org.nacukat.zombiesnacukatedition.Guns.gunFunc;

import org.bukkit.*;
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
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.rayTrace;
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


          String critmessage = "§6+12 Gold (Critical Hit)";
          new rayTrace().shoot(player,8,12,damage,hitmessage,critmessage,knockBack,direction);//shoot
          }
          for (int i1 = 0;i1 <30;i1++){
            Vector direction = player.getEyeLocation().getDirection();

            double randomYawOffset = Math.toRadians(Math.random() * 45.0D - 22.0D);
            double randomPitchOffset = Math.toRadians(Math.random() * 45.0D - 22.0D);
            direction.rotateAroundY(randomYawOffset);
            direction.rotateAroundX(randomPitchOffset);
            Location loc = player.getEyeLocation();
            Random random = new Random();

            loc = loc.add(direction.multiply(1.5+random.nextDouble(2)));
            Particle particle = Particle.LAVA;
            for (Player player1 : Bukkit.getWorld("world").getPlayers()){
              if(showParticle.get(player1)){
                player1.spawnParticle(particle,loc,0);
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
