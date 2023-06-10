package org.nacukat.zombiesnacukatedition.comands;

import com.fasterxml.jackson.databind.JsonNode;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.nacukat.zombiesnacukatedition.Game.Doors.setDoors;
import org.nacukat.zombiesnacukatedition.Game.StartGame;
import org.nacukat.zombiesnacukatedition.Game.Windows.setSpawnPoints;
import org.nacukat.zombiesnacukatedition.Game.Windows.setWindows;
import org.nacukat.zombiesnacukatedition.Game.Windows.windowBreak;

import java.awt.*;
import java.util.List;

import static org.nacukat.zombiesnacukatedition.Game.Doors.openingDoor.isOpened;
import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class setMap implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length==0)return false;
        if (node.get("Maps").get(strings[0])!= null){
            currentMap = strings[0];
            openedDoors.clear();
            isOpened.clear();
            new setWindows().set();
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (currentMap == null)cancel();
                    new windowBreak().checkZombieInRange();
                }
            }.runTaskTimer(plugin, 0L, 30L); // 1秒 = 20 tick
            for (JsonNode door : node.get("Maps").get(currentMap).get("Doors")){
                Location start = new Location(Bukkit.getWorld("world"),node.get("Maps").get(currentMap).get("DoorFrom").get(door.get("type").asInt()).get(0).get(0).asInt(),node.get("Maps").get(currentMap).get("DoorFrom").get(door.get("type").asInt()).get(0).get(1).asInt(),node.get("Maps").get(currentMap).get("DoorFrom").get(door.get("type").asInt()).get(0).get(2).asInt());
                Location end = new Location(Bukkit.getWorld("world"),node.get("Maps").get(currentMap).get("DoorFrom").get(door.get("type").asInt()).get(1).get(0).asInt(),node.get("Maps").get(currentMap).get("DoorFrom").get(door.get("type").asInt()).get(1).get(1).asInt(),node.get("Maps").get(currentMap).get("DoorFrom").get(door.get("type").asInt()).get(1).get(2).asInt());
                Location target = new Location(Bukkit.getWorld("world"),door.get("position").get(0).asInt(),door.get("position").get(1).asInt(),door.get("position").get(2).asInt());

                new setDoors().setDoor(start,end,target);
//                Location startPoint = new Location(Bukkit.getWorld("world"),door.get("starrtPoint").get(0).asInt(),door.get("startPoint").get(1).asInt(),door.get("startPoint").get(2).asInt());
//                Location endPoint; // 複製元の終点の座標を指定
//                if(door.get("facing").asInt() == 1){
//                    endPoint = new Location(Bukkit.getWorld("world"),door.get("starrtPoint").get(0).asInt()+door.get("oppmath").get(0).get(0).asInt(),door.get("startPoint").get(1).asInt()+door.get("oppmath").get(0).get(1).asInt(),door.get("startPoint").get(2).asInt()+door.get("oppmath").get(0).get(2).asInt());
//                }else {
//
//                    endPoint = new Location(Bukkit.getWorld("world"),door.get("starrtPoint").get(0).asInt()+door.get("oppmath").get(1).get(0).asInt(),door.get("startPoint").get(1).asInt()+door.get("oppmath").get(1).get(1).asInt(),door.get("startPoint").get(2).asInt()+door.get("oppmath").get(1).get(2).asInt());
//                }
//
//                World world = startPoint.getWorld();
//                int minX = Math.min(startPoint.getBlockX(), endPoint.getBlockX());
//                int minY = Math.min(startPoint.getBlockY(), endPoint.getBlockY());
//                int minZ = Math.min(startPoint.getBlockZ(), endPoint.getBlockZ());
//                int maxX = Math.max(startPoint.getBlockX(), endPoint.getBlockX());
//                int maxY = Math.max(startPoint.getBlockY(), endPoint.getBlockY());
//                int maxZ = Math.max(startPoint.getBlockZ(), endPoint.getBlockZ());
//
//                int dx = maxX - minX + 1;
//                int dy = maxY - minY + 1;
//                int dz = maxZ - minZ + 1;
//
//                for (int x = 0; x < dx; x++) {
//                    for (int y = 0; y < dy; y++) {
//                        for (int z = 0; z < dz; z++) {
//                            Block sourceBlock = world.getBlockAt(minX + x, minY + y, minZ + z);
//                            Block targetBlock = world.getBlockAt(minX + x + dx, minY + y, minZ + z);
//                            targetBlock.setType(sourceBlock.getType());
//                            targetBlock.setBlockData(sourceBlock.getBlockData());
//                        }
//                    }
//                }
            }
            for (JsonNode window : node.get("Maps").get(currentMap).get("Windows")){
                Location location = new Location(Bukkit.getWorld("world"),window.get("spawnPoint").get(0).asDouble(),window.get("spawnPoint").get(1).asDouble(),window.get("spawnPoint").get(2).asDouble());
                new setSpawnPoints().setStand(location,true,window.get("Door").asText());
                Location armlocation = new Location(Bukkit.getWorld("world"),window.get("window").get(3).get(0).asDouble(),window.get("window").get(3).get(1).asDouble(),window.get("window").get(3).get(2).asDouble());
                new setSpawnPoints().setStand(armlocation,false,"");
            }
            for (JsonNode shop : node.get("Maps").get(currentMap).get("Shops")){
                Location location = new Location(Bukkit.getWorld("world"),shop.get("position").get(0).asDouble(),shop.get("position").get(1).asDouble(),shop.get("position").get(2).asDouble());
                Location location1 = new Location(Bukkit.getWorld("world"),shop.get("position").get(0).asDouble(),shop.get("position").get(1).asDouble()-0.5,shop.get("position").get(2).asDouble());
                List<Entity> oldShop = Bukkit.getWorld("world").getNearbyEntities(location,0.1,0.1,0.1, entity -> entity.getType().equals(EntityType.DROPPED_ITEM)).stream().toList();
                List<LivingEntity> oldShopStand = Bukkit.getWorld("world").getNearbyLivingEntities(location,0.1,0.1,0.1, entity -> entity.getType().equals(EntityType.ARMOR_STAND)).stream().toList();
                if(oldShop.size() >0){
                    for (Entity en:oldShop){
                        en.remove();
                    }
                }
                if(oldShopStand.size() >0){
                    for (LivingEntity en:oldShopStand){
                        en.setHealth(0);
                    }
                }
                openedDoors.put(node.get("Maps").get(currentMap).get("SpawnRoom").asText(),true);

                ArmorStand shopStand = (ArmorStand) Bukkit.getWorld("world").spawnEntity(location1,EntityType.ARMOR_STAND);
                shopStand.setGravity(false);

                shopStand.setVisible(false);
                shopStand.setInvulnerable(true);
                shopStand.setCustomNameVisible(true);
                shopStand.customName(Component.text("§6"+shop.get("price").asText()+" Gold"));
                shopStand.setMetadata("spawn",new FixedMetadataValue(plugin,false));
                shopStand.setMetadata("Shop",new FixedMetadataValue(plugin,true));
                shopStand.setMetadata("price",new FixedMetadataValue(plugin,shop.get("price").asInt()));
                shopStand.setMetadata("type",new FixedMetadataValue(plugin,shop.get("type").asText()));
                switch (shop.get("type").asText()){
                    case "gun":
                        shopStand.setMetadata("item",new FixedMetadataValue(plugin,shop.get("item").asText()));
                        shopStand.setSmall(true);
                        Entity entity = Bukkit.getWorld("world").dropItem(location,new ItemStack(Material.valueOf(shop.get("item").asText())));
                        entity.setVelocity(new Vector(0,0,0));
                        entity.setGravity(false);
                        break;
                    case "perks":
                        shopStand.setMetadata("perk",new FixedMetadataValue(plugin,shop.get("perk").asText()));
                        shopStand.setSmall(true);
                        Material material = Material.GLASS;
                        switch (shop.get("perk").asText()){
                            case "EH"->material = Material.GOLD_NUGGET;
                            case "FB"->material = Material.GHAST_TEAR;
                            case "QF"->material = Material.REDSTONE;
                            case "FR"->material = Material.COOKIE;
                        }
                        Entity entity1 = Bukkit.getWorld("world").dropItem(location,new ItemStack(material));
                        entity1.setVelocity(new Vector(0,0,0));
                        entity1.setGravity(false);
                        break;
                    case "tops":
                        shopStand.setMetadata("met",new FixedMetadataValue(plugin,shop.get("item").get(0).asText()));
                        shopStand.setMetadata("chest",new FixedMetadataValue(plugin,shop.get("item").get(1).asText()));
                        shopStand.getEquipment().setHelmet(new ItemStack(Material.valueOf(shop.get("item").get(0).asText())));
                        shopStand.getEquipment().setChestplate(new ItemStack(Material.valueOf(shop.get("item").get(1).asText())));
                        break;
                    case "bottoms":
                        shopStand.setMetadata("leg",new FixedMetadataValue(plugin,shop.get("item").get(0).asText()));
                        shopStand.setMetadata("boots",new FixedMetadataValue(plugin,shop.get("item").get(1).asText()));
                        shopStand.getEquipment().setLeggings(new ItemStack(Material.valueOf(shop.get("item").get(0).asText())));
                        shopStand.getEquipment().setBoots(new ItemStack(Material.valueOf(shop.get("item").get(1).asText())));
                        break;
                }


            }

        }
        return false;
    }
}
