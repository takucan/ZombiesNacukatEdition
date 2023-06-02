package org.nacukat.zombiesnacukatedition.Shops;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class getShopCommend implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Player player = (Player)sender;
    player.sendMessage( node.get("Maps").get("DE").get("Doors").get(0).toString());
    HasQF.putIfAbsent(player.getName(), Boolean.valueOf(false));
    HasFB.putIfAbsent(player.getName(),false);
    HasFR.putIfAbsent(player.getName(),false);
    EHs.putIfAbsent(player.getName(),0);

    Inventory ShopGUI = Bukkit.createInventory((InventoryHolder)player, 45, "Shop");
    ItemStack QF = new ItemStack(Material.REDSTONE);
    ItemStack FB = new ItemStack(Material.GHAST_TEAR);
    ItemStack EH = new ItemStack(Material.GOLD_NUGGET);
    ItemStack FR = new ItemStack(Material.COOKIE);
    if ((HasQF.get(player.getName())).booleanValue()) {
      QF.addItemFlags(ItemFlag.HIDE_ENCHANTS);
      QF.addUnsafeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking")), 1);    }
    if(HasFB.get(player.getName())){
      FB.addItemFlags(ItemFlag.HIDE_ENCHANTS);
      FB.addUnsafeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking")), 1);
    }
    if(EHs.get(player.getName()) != 0){
      EH.addItemFlags(ItemFlag.HIDE_ENCHANTS);
      EH.addUnsafeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking")), 1);
    }
    if(HasFR.get(player.getName())){
      FR.addItemFlags(ItemFlag.HIDE_ENCHANTS);
      FR.addUnsafeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking")), 1);
    }

    for (int i = 0; i <= 44; i++)
      ShopGUI.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE)); 
    ItemStack ZombieZapper = new ItemStack(Material.DIAMOND_PICKAXE);
    ShopGUI.setItem(10, ZombieZapper);
    ShopGUI.setItem(28, QF);
    ShopGUI.setItem(30, FB);
    ShopGUI.setItem(32, EH);
    ShopGUI.setItem(34, FR);
    player.openInventory(ShopGUI);
    return false;
  }
}
