package org.nacukat.zombiesnacukatedition.Shops;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class shopListener implements Listener {
  @EventHandler
  public void onClickShop(InventoryClickEvent e) {
    Player player = (Player)e.getWhoClicked();
    if (e.getView().getTitle().equals("Shop")) {
      e.setCancelled(true);
      if (e.getClick().isLeftClick())
        switch (e.getSlot()) {
          case 10:
            player.getInventory().addItem(new ItemStack(Material.DIAMOND_PICKAXE));
            break;
          case 28:
            HasQF.putIfAbsent(player.getName(), Boolean.valueOf(false));
            if (!HasQF.get(player.getName()).booleanValue()) {
              player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1.0F, 1.0F);
              player.sendMessage(ChatColor.YELLOW + "You activated " + ChatColor.BLUE + "Quick Fire Perk" + ChatColor.YELLOW + "!");
              e.getInventory().getItem(e.getSlot()).addItemFlags(ItemFlag.HIDE_ENCHANTS);
              e.getInventory().getItem(e.getSlot()).addUnsafeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking")), 1);
              HasQF.put(player.getName(), Boolean.valueOf(true));
              break;
            } 
            player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 0.1F);
            player.sendMessage(ChatColor.YELLOW + "You already activated " + ChatColor.BLUE + "Quick Fire Perk" + ChatColor.YELLOW + "!");
            break;
          case 30:
            HasFB.putIfAbsent(player.getName(), Boolean.valueOf(false));
            if (!HasFB.get(player.getName()).booleanValue()) {
              player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1.0F, 1.0F);
              player.sendMessage(ChatColor.YELLOW + "You activated " + ChatColor.BLUE + "Frozen Bullet Perk" + ChatColor.YELLOW + "!");
              e.getInventory().getItem(e.getSlot()).addItemFlags(ItemFlag.HIDE_ENCHANTS);
              e.getInventory().getItem(e.getSlot()).addUnsafeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking")), 1);
              HasFB.put(player.getName(), Boolean.valueOf(true));
              break;
            } 
            player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 0.1F);
            player.sendMessage(ChatColor.YELLOW + "You already activated " + ChatColor.BLUE + "Frozen Bullet Perk" + ChatColor.YELLOW + "!");
            break;
          case 32:
            EHs.putIfAbsent(player.getName(), 0);
            if (EHs.get(player.getName()) < 10) {
              EHs.put(player.getName(), EHs.get(player.getName())+1);
              player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1.0F, 1.0F);
              String ehValue = "I";
              switch (EHs.get(player.getName())){
                case 1:
                  ehValue = "I";
                  break;
                case 2:
                  ehValue = "II";
                  break;
                case 3:
                  ehValue = "III";
                  break;
                case 4:
                  ehValue = "IV";
                  break;
                case 5:
                  ehValue = "V";
                  break;
                case 6:
                  ehValue = "VI";
                  break;
                case 7:
                  ehValue = "VII";
                  break;
                case 8:
                  ehValue = "VIII";
                  break;
                case 9:
                  ehValue = "IX";
                  break;
                case 10:
                  ehValue = "X";
                  break;
              }
              player.sendMessage(ChatColor.YELLOW + "You activated " + ChatColor.BLUE + "Extra Health Perk "+ehValue + ChatColor.YELLOW + "!");
              player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
              PotionEffect potionEffect = new PotionEffect(PotionEffectType.HEALTH_BOOST,-1,EHs.get(player.getName())-1,false,false,false);
              player.addPotionEffect(potionEffect);
              e.getInventory().getItem(e.getSlot()).addItemFlags(ItemFlag.HIDE_ENCHANTS);
              e.getInventory().getItem(e.getSlot()).addUnsafeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking")), 1);

              break;
            }
            player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 0.1F);
            player.sendMessage(ChatColor.YELLOW + "You already activated " + ChatColor.BLUE + "Extra Health Perk" + ChatColor.YELLOW + "!");
            break;
          case 34:
            HasFR.putIfAbsent(player.getName(), Boolean.valueOf(false));
            if (!HasFR.get(player.getName()).booleanValue()) {
              player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1.0F, 1.0F);
              player.sendMessage(ChatColor.YELLOW + "You activated " + ChatColor.BLUE + "Fast Revive Perk" + ChatColor.YELLOW + "!");
              e.getInventory().getItem(e.getSlot()).addItemFlags(ItemFlag.HIDE_ENCHANTS);
              e.getInventory().getItem(e.getSlot()).addUnsafeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking")), 1);
              HasFR.put(player.getName(), Boolean.valueOf(true));
              break;
            }
            player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 0.1F);
            player.sendMessage(ChatColor.YELLOW + "You already activated " + ChatColor.BLUE + "Fast Revive Perk" + ChatColor.YELLOW + "!");
            break;
        }  
      if (e.getClick().isRightClick())
        switch (e.getSlot()) {
          case 28:
            HasQF.putIfAbsent(player.getName(), Boolean.valueOf(false));
            if (HasQF.get(player.getName()).booleanValue()) {
              player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1.0F, 1.0F);
              player.sendMessage(ChatColor.YELLOW + "You deactivated " + ChatColor.BLUE + "Quick Fire Perk" + ChatColor.YELLOW + "!");
              e.getInventory().getItem(e.getSlot()).removeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking")));
              HasQF.replace(player.getName(), Boolean.valueOf(false));
              break;
            } 
            player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 0.1F);
            player.sendMessage(ChatColor.YELLOW + "You are not activated " + ChatColor.BLUE + "Quick Fire Perk" + ChatColor.YELLOW + "!");
            break;
          case 30:
            HasFB.putIfAbsent(player.getName(), Boolean.valueOf(false));
            if (HasFB.get(player.getName()).booleanValue()) {
              player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1.0F, 1.0F);
              player.sendMessage(ChatColor.YELLOW + "You deactivated " + ChatColor.BLUE + "Frozen Bullet Perk" + ChatColor.YELLOW + "!");
              e.getInventory().getItem(e.getSlot()).removeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking")));
              HasFB.replace(player.getName(), Boolean.valueOf(false));
              break;
            } 
            player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 0.1F);
            player.sendMessage(ChatColor.YELLOW + "You are not activated " + ChatColor.BLUE + "Frozen Bullet Perk" + ChatColor.YELLOW + "!");
            break;
          case 32:
            EHs.putIfAbsent(player.getName(),0);
            if(EHs.get(player.getName()) != 0){
              player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1.0F, 1.0F);
              player.sendMessage(ChatColor.YELLOW + "You deactivated " + ChatColor.BLUE + "Extra Health Perk" + ChatColor.YELLOW + "!");
              e.getInventory().getItem(e.getSlot()).removeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking")));
              EHs.replace(player.getName(), 0);
              player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
              break;
            }
            player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 0.1F);
            player.sendMessage(ChatColor.YELLOW + "You are not activated " + ChatColor.BLUE + "Extra Health Perk" + ChatColor.YELLOW + "!");
            break;
          case 34:
            HasFR.putIfAbsent(player.getName(), Boolean.valueOf(false));
            if (HasFR.get(player.getName()).booleanValue()) {
              player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1.0F, 1.0F);
              player.sendMessage(ChatColor.YELLOW + "You deactivated " + ChatColor.BLUE + "Fast Revive Perk" + ChatColor.YELLOW + "!");
              e.getInventory().getItem(e.getSlot()).removeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking")));
              HasFR.replace(player.getName(), Boolean.valueOf(false));
              break;
            }
            player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 0.1F);
            player.sendMessage(ChatColor.YELLOW + "You are not activated " + ChatColor.BLUE + "Fast Revive Perk" + ChatColor.YELLOW + "!");
            break;
        }  
    } 
  }
}
