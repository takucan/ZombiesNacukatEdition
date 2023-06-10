package org.nacukat.zombiesnacukatedition.Game.Doors;

import com.fasterxml.jackson.databind.JsonNode;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Objects;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class openingDoor implements Listener {
    public static HashMap<String,Boolean> isOpened = new HashMap<>();
    @EventHandler
    public void pressDoorButton(PlayerInteractEvent e){
        Action action = e.getAction();
        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (action.equals(Action.RIGHT_CLICK_BLOCK)&&currentMap != null) {
            for (JsonNode door : node.get("Maps").get(currentMap).get("Doors")){
                JsonNode position = door.get("position");

                int x = e.getClickedBlock().getLocation().getBlockX();
                int y = e.getClickedBlock().getLocation().getBlockY();
                int z = e.getClickedBlock().getLocation().getBlockZ();
                World world = player.getWorld();
                Location point1 = new Location(world,position.get(0).asInt(),position.get(1).asInt(),position.get(2).asInt()); // 指定範囲の1つ目の座標
                Location point2 = new Location(world, door.get("opposite").get(0).asInt(),door.get("opposite").get(1).asInt(),door.get("opposite").get(2).asInt()); // 指定範囲の2つ目の座標


                int minX = Math.min(point1.getBlockX(), point2.getBlockX());
                int minY = Math.min(point1.getBlockY(), point2.getBlockY());
                int minZ = Math.min(point1.getBlockZ(), point2.getBlockZ());
                int maxX = Math.max(point1.getBlockX(), point2.getBlockX());
                int maxY = Math.max(point1.getBlockY(), point2.getBlockY());
                int maxZ = Math.max(point1.getBlockZ(), point2.getBlockZ());
                isOpened.putIfAbsent(door.get("name").asText(),false);
                if (x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ&&Gold.get(player.getUniqueId())>=door.get("price").asInt()&&!isOpened.get(door.get("name").asText())) {
                        e.setCancelled(true);
                        isOpened.put(door.get("name").asText(),true);
                        Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())-door.get("price").asInt());
                        player.playSound(player, Sound.BLOCK_IRON_DOOR_OPEN,1F,1.5F);
                        for (int x1 = minX; x1 <= maxX; x1++) {
                            for (int y1 = minY; y1 <= maxY; y1++) {
                                for (int z1 = minZ; z1 <= maxZ; z1++) {
                                    Block block = world.getBlockAt(x1, y1, z1);
                                    block.setType(Material.AIR);
                                }
                            }
                        }
                        String[] rooms = door.get("name").asText().split("-");
                        for (String room : rooms){
                            openedDoors.put(room,true);
                        }
                }

            }
            for(JsonNode ultimates: node.get("Maps").get(currentMap).get("Ultimates")){
                Location loc = new Location(Bukkit.getWorld("world"), ultimates.get(0).asDouble(), ultimates.get(1).asDouble(), ultimates.get(2).asDouble());
                if (Objects.requireNonNull(e.getClickedBlock()).getLocation().equals(loc)&&Gold.get(player.getUniqueId())>=1500) {
                    e.setCancelled(true);
                    ItemMeta itemMeta = Objects.requireNonNull(item).getItemMeta();
                    if (item.getType().equals(Material.DIAMOND_PICKAXE)) {
                        Ultimates.putIfAbsent(item.getItemMeta().getCustomModelData(), 0);
                        if (Ultimates.get(item.getItemMeta().getCustomModelData()).equals(0)) {
                            itemMeta.setDisplayName("§6§lZombie Zapper Ultimate");
                            Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())-1500);
                            item.setItemMeta(itemMeta);
                            Ultimates.put(item.getItemMeta().getCustomModelData(), 1);
                            item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                            item.addEnchantment(Objects.requireNonNull(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking"))), 1);
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                        }
                    }
                    if (item.getType().equals(Material.IRON_HOE)) {
                        Ultimates.putIfAbsent(item.getItemMeta().getCustomModelData(), 0);
                        if (Ultimates.get(item.getItemMeta().getCustomModelData()).equals(0)) {
                            itemMeta.setDisplayName("§6§lShotgun Ultimate");
                            Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())-1500);
                            item.setItemMeta(itemMeta);
                            Ultimates.put(item.getItemMeta().getCustomModelData(), 1);
                            item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                            item.addEnchantment(Objects.requireNonNull(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking"))), 1);
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                        }
                    }
                    if (item.getType().equals(Material.WOODEN_HOE)) {
                        Ultimates.putIfAbsent(item.getItemMeta().getCustomModelData(), 0);
                        if (Ultimates.get(item.getItemMeta().getCustomModelData()).equals(0)) {
                            itemMeta.setDisplayName("§6§lPistol Ultimate");
                            Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())-1500);
                            item.setItemMeta(itemMeta);
                            Ultimates.put(item.getItemMeta().getCustomModelData(), 1);
                            item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                            item.addEnchantment(Objects.requireNonNull(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking"))), 1);
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                        }
                    }
                    if (item.getType().equals(Material.FLINT_AND_STEEL)) {
                        Ultimates.putIfAbsent(item.getItemMeta().getCustomModelData(), 0);
                        switch (Ultimates.get(item.getItemMeta().getCustomModelData())) {
                            case 0 -> {
                                itemMeta.setDisplayName("§6§lDouble Barrel Shotgun Ultimate");
                                Gold.put(player.getUniqueId(), Gold.get(player.getUniqueId()) - 1500);
                                item.setItemMeta(itemMeta);
                                item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                item.addEnchantment(Objects.requireNonNull(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking"))), 1);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(item.getItemMeta().getCustomModelData(), 1);
                            }
                            case 1 -> {
                                itemMeta.setDisplayName("§6§lDouble Barrel Shotgun Ultimate II");
                                Gold.put(player.getUniqueId(), Gold.get(player.getUniqueId()) - 1500);
                                item.setItemMeta(itemMeta);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(item.getItemMeta().getCustomModelData(), 2);
                            }
                            case 2 -> {
                                itemMeta.setDisplayName("§6§lDouble Barrel Shotgun III");
                                Gold.put(player.getUniqueId(), Gold.get(player.getUniqueId()) - 1500);
                                item.setItemMeta(itemMeta);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(item.getItemMeta().getCustomModelData(), 3);
                            }
                        }
                    }
                    if (item.getType().equals(Material.GOLDEN_SHOVEL)) {
                        Ultimates.putIfAbsent(item.getItemMeta().getCustomModelData(), 0);
                        switch (Ultimates.get(item.getItemMeta().getCustomModelData())) {
                            case 0 -> {
                                itemMeta.setDisplayName("§6§lRainbow Rifle Ultimate");
                                Gold.put(player.getUniqueId(), Gold.get(player.getUniqueId()) - 1500);
                                item.setItemMeta(itemMeta);
                                item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                item.addEnchantment(Objects.requireNonNull(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking"))), 1);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(item.getItemMeta().getCustomModelData(), 1);
                            }
                            case 1 -> {
                                itemMeta.setDisplayName("§6§lRainbow Rifle Ultimate II");
                                Gold.put(player.getUniqueId(), Gold.get(player.getUniqueId()) - 1500);
                                item.setItemMeta(itemMeta);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(item.getItemMeta().getCustomModelData(), 2);
                            }
                            case 2 -> {
                                itemMeta.setDisplayName("§6§lRainbow Rifle Ultimate III");
                                Gold.put(player.getUniqueId(), Gold.get(player.getUniqueId()) - 1500);
                                item.setItemMeta(itemMeta);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(item.getItemMeta().getCustomModelData(), 3);
                            }
                        }
                    }
                    if (item.getType().equals(Material.GOLDEN_PICKAXE)) {
                        Ultimates.putIfAbsent(item.getItemMeta().getCustomModelData(), 0);
                        switch (Ultimates.get(item.getItemMeta().getCustomModelData())) {
                            case 0 -> {
                                itemMeta.setDisplayName("§6§lGold Digger Ultimate");
                                Gold.put(player.getUniqueId(), Gold.get(player.getUniqueId()) - 1500);
                                item.setItemMeta(itemMeta);
                                item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                item.addEnchantment(Objects.requireNonNull(Enchantment.getByKey(NamespacedKey.minecraft("unbreaking"))), 1);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(item.getItemMeta().getCustomModelData(), 1);
                            }
                            case 1 -> {
                                itemMeta.setDisplayName("§6§lGold Digger Ultimate II");
                                Gold.put(player.getUniqueId(), Gold.get(player.getUniqueId()) - 1500);
                                item.setItemMeta(itemMeta);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(item.getItemMeta().getCustomModelData(), 2);
                            }
                            case 2 -> {
                                itemMeta.setDisplayName("§6§lGold Digger Ultimate III");
                                Gold.put(player.getUniqueId(), Gold.get(player.getUniqueId()) - 1500);
                                item.setItemMeta(itemMeta);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(item.getItemMeta().getCustomModelData(), 3);
                            }
                            case 3 -> {
                                itemMeta.setDisplayName("§6§lGold Digger Ultimate IV");
                                Gold.put(player.getUniqueId(), Gold.get(player.getUniqueId()) - 1500);
                                item.setItemMeta(itemMeta);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(item.getItemMeta().getCustomModelData(), 4);
                            }
                            case 4 -> {
                                itemMeta.setDisplayName("§6§lGold Digger Ultimate V");
                                Gold.put(player.getUniqueId(), Gold.get(player.getUniqueId()) - 1500);
                                item.setItemMeta(itemMeta);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                Ultimates.put(item.getItemMeta().getCustomModelData(), 5);
                            }
                        }
                    }
                    isReloading.put(item.getItemMeta().getCustomModelData(),false);
                    totalBullets.put(item.getItemMeta().getCustomModelData(),totalbulletsMaterial.get(item.getType())[Ultimates.get(item.getItemMeta().getCustomModelData())]);

                    player.setExp(1);
                    player.setLevel(totalBullets.get(item.getItemMeta().getCustomModelData()));


                    break;
                }
            }
        }
    }
}
