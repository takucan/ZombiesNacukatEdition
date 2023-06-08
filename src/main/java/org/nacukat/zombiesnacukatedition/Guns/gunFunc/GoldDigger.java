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
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.*;

import java.util.*;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class GoldDigger {
  public boolean goldDigger(HashMap<ItemStack, Long> lastShotTimes, HashMap<Integer, Boolean> isReloading, HashMap<Integer, Long> magazines, Player player, ItemStack item) {
    long clipSize = 7L;
    Sound shootSound = Sound.BLOCK_STONE_BREAK;
    float volume = 0.7F;
    float pich = 2.0F;
    long fireRate = 500L;
    double knockBack = 0.5D;
    double damage = 6.0D;
    double period = 30.0D;
    Long lastShotTime = lastShotTimes.get(item);
    long currentTime = System.currentTimeMillis();
    Long magazine = magazines.get(Integer.valueOf(item.getItemMeta().getCustomModelData()));
    switch (Ultimates.get(Integer.valueOf(item.getItemMeta().getCustomModelData())).intValue()) {
      case 1:
        damage = 8.0D;
        clipSize = 10L;
        period = 28.0D;
        break;
      case 2:
        damage = 10.0D;
        clipSize = 13L;
        fireRate = 400L;
        period = 26.0D;
        break;
      case 3:
        damage = 12.0D;
        clipSize = 16L;
        fireRate = 300L;
        period = 24.0D;
        break;
      case 4:
        damage = 15.0D;
        clipSize = 20L;
        fireRate = 300L;
        period = 22.0D;
        break;
      case 5:
        damage = 20.0D;
        clipSize = 25L;
        fireRate = 200L;
        period = 20.0D;
        break;
    } 
    if (HasQF.get(player.getName()))
      fireRate = (long)(fireRate * 0.75D); 
    if (magazine == null) {
      magazine = clipSize;
      magazines.put(item.getItemMeta().getCustomModelData(), magazine);
      item.setAmount(Math.toIntExact(clipSize));
    } 
    if (magazine <= 0L) {
      isReloading.replace(item.getItemMeta().getCustomModelData(), Boolean.valueOf(true));
      boolean a = (new reload()).reloadGun(item, isReloading, magazines, clipSize, (long)period, player);
      player.sendMessage("reload");
    } 
    if (magazine > 0L) {
      if (magazine > clipSize) {
        magazine = clipSize;
        item.setAmount((int)clipSize);
      } 
      if (lastShotTime == null || currentTime - lastShotTime >= fireRate) {

        for (Player player1 : Bukkit.getServer().getOnlinePlayers())
          player1.playSound((Entity)player, shootSound, volume, pich); 
        if (lastShotTime != null);
        String hitmessage = "§6+10 Gold";
        String critmessage = "§6+15 Gold (Critical Hit)";

        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.YELLOW, 1.0f);
        Particle particle = Particle.REDSTONE;

        new spawnParticle().spawn(particle,dustOptions,player);
        new rayTrace().shoot(player,item,10,15,damage,hitmessage,critmessage,knockBack,player.getEyeLocation().getDirection());

        if (item.getAmount() > 1)
          item.setAmount(Math.toIntExact(magazines.get(item.getItemMeta().getCustomModelData())) - 1);
        magazine = magazine - 1L;
        magazines.put(item.getItemMeta().getCustomModelData(), magazine);
        lastShotTimes.put(item, currentTime);
        if (magazine <= 0L) {
          isReloading.replace(item.getItemMeta().getCustomModelData(), Boolean.TRUE);
          boolean a = (new reload()).reloadGun(item, isReloading, magazines, clipSize, (long)period, player);
          player.sendMessage("reload");
        } 
      } 
    } 
    return true;
  }
}
