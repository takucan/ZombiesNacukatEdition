package org.nacukat.zombiesnacukatedition.Guns;

import com.fasterxml.jackson.databind.JsonNode;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.GoldDigger;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.RainbowRifle;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.ZombieZapper;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.dbs;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.other.get01Location;
import org.nacukat.zombiesnacukatedition.Skill.RightLightningRod;

import java.util.List;
import java.util.Random;
import java.util.stream.StreamSupport;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class invokeGun implements Listener {

    private Material[] guns = new Material[]{Material.DIAMOND_PICKAXE,Material.GOLDEN_PICKAXE,Material.GOLDEN_SHOVEL,Material.FLINT_AND_STEEL};
    @EventHandler
    public void onClickedArmStand(PlayerInteractEntityEvent e){
        Action action = Action.RIGHT_CLICK_AIR;
        Player player = e.getPlayer();
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        clickEvent(player,item,action);
    }
    @EventHandler
    public void playerShoot(PlayerInteractEvent e){
        Action action = e.getAction();
        Player player = e.getPlayer();
        ItemStack item = e.getItem();
        if(isDown.get(player))e.setCancelled(true);

        if (action.equals(Action.RIGHT_CLICK_BLOCK)&&currentMap != null) {
            for(JsonNode ultimates: node.get("Maps").get(currentMap).get("Ultimates")){
                Location loc = new Location(Bukkit.getWorld("world"), ultimates.get(0).asDouble(), ultimates.get(1).asDouble(), ultimates.get(2).asDouble());
                if (e.getClickedBlock().getLocation().equals(loc)&&Gold.get(player.getUniqueId())>=1500) {
                    ItemMeta itemMeta = item.getItemMeta();
                    if (item.getType().equals(Material.DIAMOND_PICKAXE)) {
                        Ultimates.putIfAbsent(Integer.valueOf(item.getItemMeta().getCustomModelData()), Integer.valueOf(0));
                        if (((Integer)Ultimates.get(Integer.valueOf(item.getItemMeta().getCustomModelData()))).equals(Integer.valueOf(0))) {
                            itemMeta.setDisplayName("§6§lZombie Zapper Ultimate");
                            Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())-1500);
                            item.setItemMeta(itemMeta);
                            Ultimates.put(Integer.valueOf(item.getItemMeta().getCustomModelData()), Integer.valueOf(1));
                            item.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
                            item.addEnchantment(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking")), 1);
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                        }
                    }
                    if (item.getType().equals(Material.FLINT_AND_STEEL)) {
                        Ultimates.putIfAbsent(Integer.valueOf(item.getItemMeta().getCustomModelData()), Integer.valueOf(0));
                        switch (((Integer)Ultimates.get(Integer.valueOf(item.getItemMeta().getCustomModelData()))).intValue()) {
                            case 0:
                                itemMeta.setDisplayName("§6§lDouble Barrel Shotgun Ultimate");
                                Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())-1500);
                                item.setItemMeta(itemMeta);
                                item.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
                                item.addEnchantment(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking")), 1);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(Integer.valueOf(item.getItemMeta().getCustomModelData()), Integer.valueOf(1));
                                break;
                            case 1:
                                itemMeta.setDisplayName("§6§lDouble Barrel Shotgun Ultimate II");
                                Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())-1500);
                                item.setItemMeta(itemMeta);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(Integer.valueOf(item.getItemMeta().getCustomModelData()), Integer.valueOf(2));
                                break;
                            case 2:
                                itemMeta.setDisplayName("§6§lDouble Barrel Shotgun III");
                                Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())-1500);
                                item.setItemMeta(itemMeta);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(Integer.valueOf(item.getItemMeta().getCustomModelData()), Integer.valueOf(3));
                                break;
                        }
                    }
                    if (item.getType().equals(Material.GOLDEN_SHOVEL)) {
                        Ultimates.putIfAbsent(Integer.valueOf(item.getItemMeta().getCustomModelData()), Integer.valueOf(0));
                        switch (((Integer)Ultimates.get(Integer.valueOf(item.getItemMeta().getCustomModelData()))).intValue()) {
                            case 0:
                                itemMeta.setDisplayName("§6§lRainbow Rifle Ultimate");
                                Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())-1500);
                                item.setItemMeta(itemMeta);
                                item.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
                                item.addEnchantment(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking")), 1);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(Integer.valueOf(item.getItemMeta().getCustomModelData()), Integer.valueOf(1));
                                break;
                            case 1:
                                itemMeta.setDisplayName("§6§lRainbow Rifle Ultimate II");
                                Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())-1500);
                                item.setItemMeta(itemMeta);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(Integer.valueOf(item.getItemMeta().getCustomModelData()), Integer.valueOf(2));
                                break;
                            case 2:
                                itemMeta.setDisplayName("§6§lRainbow Rifle Ultimate III");
                                Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())-1500);
                                item.setItemMeta(itemMeta);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(Integer.valueOf(item.getItemMeta().getCustomModelData()), Integer.valueOf(3));
                                break;
                        }
                    }
                    if (item.getType().equals(Material.GOLDEN_PICKAXE)) {
                        Ultimates.putIfAbsent(Integer.valueOf(item.getItemMeta().getCustomModelData()), Integer.valueOf(0));
                        switch (((Integer)Ultimates.get(Integer.valueOf(item.getItemMeta().getCustomModelData()))).intValue()) {
                            case 0:
                                itemMeta.setDisplayName("§6§lGold Digger Ultimate");
                                Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())-1500);
                                item.setItemMeta(itemMeta);
                                item.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
                                item.addEnchantment(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking")), 1);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(Integer.valueOf(item.getItemMeta().getCustomModelData()), Integer.valueOf(1));
                                break;
                            case 1:
                                itemMeta.setDisplayName("§6§lGold Digger Ultimate II");
                                Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())-1500);
                                item.setItemMeta(itemMeta);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(Integer.valueOf(item.getItemMeta().getCustomModelData()), Integer.valueOf(2));
                                break;
                            case 2:
                                itemMeta.setDisplayName("§6§lGold Digger Ultimate III");
                                Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())-1500);
                                item.setItemMeta(itemMeta);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(Integer.valueOf(item.getItemMeta().getCustomModelData()), Integer.valueOf(3));
                                break;
                            case 3:
                                itemMeta.setDisplayName("§6§lGold Digger Ultimate IV");
                                Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())-1500);
                                item.setItemMeta(itemMeta);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(Integer.valueOf(item.getItemMeta().getCustomModelData()), Integer.valueOf(4));
                                break;
                            case 4:
                                itemMeta.setDisplayName("§6§lGold Digger Ultimate V");
                                Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())-1500);
                                item.setItemMeta(itemMeta);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(Integer.valueOf(item.getItemMeta().getCustomModelData()), Integer.valueOf(5));
                                break;
                        }
                    }
                    totalBullets.put(item.getItemMeta().getCustomModelData(),totalbulletsMaterial.get(item.getType())[Ultimates.get(item.getItemMeta().getCustomModelData())]);

                    player.setExp(1);
                    player.setLevel(totalBullets.get(item.getItemMeta().getCustomModelData()));
                    break;
                }else {
                    return;
                }
            }
        }
        clickEvent(player,item,action);
    }

    public void clickEvent(Player player, ItemStack item, Action action){
        isDown.putIfAbsent(player,false);
        Gold.putIfAbsent(player.getUniqueId(),0);

        if (item == null)return;


        if(item.getType().equals(Material.BLAZE_ROD)&&action.isRightClick()){
            new RightLightningRod().lightningRod(player);
        }

        if (item.getType().equals(Material.IRON_SWORD)&&action.isRightClick()) {
            isBlock.putIfAbsent(player, Boolean.valueOf(false));
            lastBlock.putIfAbsent(player, Long.valueOf(0L));
            if (System.currentTimeMillis() - ((Long)lastBlock.get(player)).longValue() >= 100L) {
                isBlock.replace(player, Boolean.valueOf(true));
                ItemStack shield = new ItemStack(Material.SHIELD);
                ItemMeta itemMeta = shield.getItemMeta();
                itemMeta.setUnbreakable(true);
                shield.setItemMeta(itemMeta);
                player.setShieldBlockingDelay(0);
                player.getInventory().setItemInOffHand(shield);
                (new BukkitRunnable() {
                    public void run() {
                        if (!player.isBlocking() && !player.isHandRaised() || !player.getInventory().getItemInMainHand().getType().equals(Material.IRON_SWORD)) {
                            isBlock.replace(player, Boolean.valueOf(false));
                            lastBlock.put(player, Long.valueOf(System.currentTimeMillis()));
                            player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
                            cancel();
                        }
                    }
                }).runTaskTimer(plugin, 6L, 1L);
            }
        }

        if(!action.isRightClick()||!(item.getType().equals(Material.DIAMOND_PICKAXE) || item.getType().equals(Material.GOLDEN_PICKAXE) || item.getType().equals(Material.GOLDEN_SHOVEL) || item.getType().equals(Material.FLINT_AND_STEEL)))return;

        ItemMeta meta = item.getItemMeta();
        HasQF.putIfAbsent(player.getName(),false);
        HasFB.putIfAbsent(player.getName(),false);


        if (!meta.hasCustomModelData()) {
            Random random = new Random();
            int randomMeta = random.nextInt(100000);
            meta.setCustomModelData(randomMeta);
            switch (item.getType()) {
                case DIAMOND_PICKAXE:
                    meta.setDisplayName("§6Zombie Zapper");
                    break;
                case GOLDEN_PICKAXE:
                    meta.setDisplayName("§6Gold Digger");
                    break;
                case GOLDEN_SHOVEL:
                    meta.setDisplayName("§6Rainbow Rifle");
                    break;
                case FLINT_AND_STEEL:
                    meta.setDisplayName("§6Double Barrel Shotgun");
                    break;
            }
            item.setItemMeta(meta);
        }
        isReloading.putIfAbsent(item.getItemMeta().getCustomModelData(),false);
        Ultimates.putIfAbsent(item.getItemMeta().getCustomModelData(),0);
        totalBullets.putIfAbsent(item.getItemMeta().getCustomModelData(),totalbulletsMaterial.get(item.getType())[Ultimates.get(item.getItemMeta().getCustomModelData())]);

        if(isReloading.get(item.getItemMeta().getCustomModelData())||isDown.get(player))return;
        if(totalBullets.get(item.getItemMeta().getCustomModelData())<=0)return;
        player.setExp(1);
        player.setLevel(totalBullets.get(item.getItemMeta().getCustomModelData()));

        switch (item.getType()){
            case DIAMOND_PICKAXE:
                new ZombieZapper().zombieZapper(lastShotTimes,isReloading,magazines,player,item);
                break;
            case GOLDEN_PICKAXE:
                new GoldDigger().goldDigger(lastShotTimes,isReloading,magazines,player,item);
                break;
            case GOLDEN_SHOVEL:
                new RainbowRifle().rainbowRifle(isShootingRR,lastShotTimes,isReloading,magazines,player,item);
                break;
            case FLINT_AND_STEEL:
                new dbs().doubleBarrel(lastShotTimes,isReloading,magazines,player,item);
                break;
            default:
        }

    }

}
