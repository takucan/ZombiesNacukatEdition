package org.nacukat.zombiesnacukatedition.Guns.gunFunc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.rayTrace;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.reload;

import java.util.*;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class dbs {
  public boolean doubleBarrel(HashMap<Integer, Long> lastShotTimes, HashMap<Integer, Boolean> isReloading, HashMap<Integer, Long> magazines, Player player, ItemStack item) {

    lastShotTimes.putIfAbsent(item.getItemMeta().getCustomModelData(),System.currentTimeMillis());
    long lastShotTime = lastShotTimes.get(item.getItemMeta().getCustomModelData());
    long clipSize = 2L;
    long magazine = magazines.getOrDefault(Integer.valueOf(item.getItemMeta().getCustomModelData()),clipSize);
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
    if (magazine <= 0L) {
      isReloading.replace(Integer.valueOf(item.getItemMeta().getCustomModelData()), Boolean.valueOf(true));
      boolean a = (new reload()).reloadGun(item, isReloading, magazines, clipSize, period, player);
      player.sendMessage("reload");
    } 
    if (magazine > 0L) {
      if (magazine > clipSize) {
        magazine = Long.valueOf(clipSize);
        item.setAmount((int)clipSize);
      } 
      if ( currentTime - lastShotTime >= fireRate) {
        for (Player player1 : Bukkit.getServer().getOnlinePlayers())
          player1.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 0.6F, 1.2F);
        String hitmessage = "§6+8 Gold";
        for (int i = 0; i <= 10; i++) {
          Vector direction = player.getEyeLocation().getDirection();
          double randomYawOffset = Math.toRadians(Math.random() * 20.0D - 10.0D);
          double randomPitchOffset = Math.toRadians(Math.random() * 20.0D - 10.0D);
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

          gunShoots.putIfAbsent(player.getUniqueId(),new ArrayList<>(Arrays.asList(0L,0L,0L,0L,0L,0L)));
          List<Long> gunShootList = new ArrayList<>(gunShoots.get(player.getUniqueId()));
          gunShootList.set(guns.indexOf(item.getType()),gunShootList.get(guns.indexOf(item.getType()))+1);
          gunShoots.put(player.getUniqueId(),gunShootList);
        }

        totalBullets.put(item.getItemMeta().getCustomModelData(),totalBullets.get(item.getItemMeta().getCustomModelData())-1);
        player.setExp(1);
        player.setLevel(totalBullets.get(item.getItemMeta().getCustomModelData()));


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
//        if (item.getAmount() > 1)
//          item.setAmount(Math.toIntExact(magazines.get(Integer.valueOf(item.getItemMeta().getCustomModelData())).longValue()) - 1);
        magazine = magazine - 1L;
        if(magazine > 0)item.setAmount((int) magazine);
        magazines.put(item.getItemMeta().getCustomModelData(), magazine);
        lastShotTimes.put(item.getItemMeta().getCustomModelData(), currentTime);
        if (magazine <= 0L) {
          isReloading.replace(item.getItemMeta().getCustomModelData(), Boolean.TRUE);
          boolean a = (new reload()).reloadGun(item, isReloading, magazines, clipSize, period, player);
          player.sendMessage("reload");
        } 
      } 
    } 
    return false;
  }
}
