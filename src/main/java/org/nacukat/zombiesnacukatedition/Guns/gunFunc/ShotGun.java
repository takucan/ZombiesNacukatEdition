package org.nacukat.zombiesnacukatedition.Guns.gunFunc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.rayTrace;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.reload;

import java.util.*;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class ShotGun {
  public boolean shotgun(HashMap<ItemStack, Long> lastShotTimes, HashMap<Integer, Boolean> isReloading, HashMap<Integer, Long> magazines, Player player, ItemStack item) {
    Long lastShotTime = lastShotTimes.get(item);
    Long magazine = magazines.get(Integer.valueOf(item.getItemMeta().getCustomModelData()));
    long clipSize = 5L;
    long currentTime = System.currentTimeMillis();
    double damage = 4.7;
    int period = 30;
    long fireRate = 1400L;
    double knockBack = 0.5D;
    switch (Ultimates.get(Integer.valueOf(item.getItemMeta().getCustomModelData())).intValue()) {
      case 1:
        fireRate = 1000L;
        period = 20;
        break;
      case 2:
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
          player1.playSound((Entity)player, Sound.ENTITY_GENERIC_EXPLODE, 0.6F, 1.8F);
        if (lastShotTime != null);
        String hitmessage = "§6+8 Gold";
        for (int i = 0; i <= 10; i++) {
          Vector direction = player.getEyeLocation().getDirection();
          double randomYawOffset = Math.toRadians(Math.random() * 15.0D - 7.0D);
          double randomPitchOffset = Math.toRadians(Math.random() * 15.0D - 7.0D);
          direction.rotateAroundY(randomYawOffset);
          direction.rotateAroundX(randomPitchOffset);
          boolean intersect = false;


          String critmessage = "§6+12 Gold (Critical Hit)";
          new rayTrace().shoot(player,item,8,12,damage,hitmessage,critmessage,knockBack,direction);//shoot
          }

        isCounting.putIfAbsent(player.getUniqueId(),false);
        if(isCounting.get(player.getUniqueId())){
          if(player.getInventory().getHeldItemSlot() < 5&&player.getInventory().getHeldItemSlot()>0) {
            slotShoots.putIfAbsent(player.getUniqueId(), new ArrayList<>(Arrays.asList(0L, 0L, 0L, 0L)));
            List<Long> slotShootList = new ArrayList<>(slotShoots.get(player.getUniqueId()));
            slotShootList.set(player.getInventory().getHeldItemSlot()-1, slotShootList.get(player.getInventory().getHeldItemSlot()-1) + 1);
            slotShoots.put(player.getUniqueId(), slotShootList);
          }

          gunShoots.putIfAbsent(player.getUniqueId(),new ArrayList<>(Arrays.asList(0L,0L,0L,0L,0L)));
          List<Long> gunShootList = new ArrayList<>(gunShoots.get(player.getUniqueId()));
          gunShootList.set(guns.indexOf(player.getInventory().getItemInMainHand().getType()),gunShootList.get(player.getInventory().getHeldItemSlot())+1);
          gunShoots.put(player.getUniqueId(),gunShootList);
        }

        totalBullets.put(item.getItemMeta().getCustomModelData(),totalBullets.get(item.getItemMeta().getCustomModelData())-1);
        player.setExp(1);
        player.setLevel(totalBullets.get(item.getItemMeta().getCustomModelData()));


          for (int i1 = 0;i1 <50;i1++){
            Vector direction = player.getEyeLocation().getDirection();

            double randomYawOffset = Math.toRadians(Math.random() * 15.0D - 7.0D);
            double randomPitchOffset = Math.toRadians(Math.random() * 15.0D - 7.0D);
            direction.rotateAroundY(randomYawOffset);
            direction.rotateAroundX(randomPitchOffset);
            Location loc = player.getEyeLocation();
            Random random = new Random();

            loc = loc.add(direction.multiply(0.6+random.nextDouble(2)));
            Particle particle = Particle.SMOKE_NORMAL;
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
