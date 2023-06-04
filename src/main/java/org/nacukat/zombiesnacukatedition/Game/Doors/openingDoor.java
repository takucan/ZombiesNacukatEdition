package org.nacukat.zombiesnacukatedition.Game.Doors;

import com.fasterxml.jackson.databind.JsonNode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class openingDoor implements Listener {
    @EventHandler
    public void pressDoorButton(PlayerInteractEvent e){
        Action action = e.getAction();
        Player player = e.getPlayer();

        if(action.equals(Action.RIGHT_CLICK_BLOCK)&&currentMap != null){
            for (JsonNode door : node.get("Maps").get(currentMap).get("Doors")){
                for (JsonNode material : door.get("materials")){
                    if(Gold.get(player.getUniqueId())>=door.get("price").asInt()){
                        if(e.getClickedBlock().getType().equals(Material.valueOf(material.asText()))){
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
                            if (x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ) {
                                player.playSound(player, Sound.BLOCK_IRON_DOOR_OPEN,1F,1.5F);
                                for (int x1 = minX; x1 <= maxX; x1++) {
                                    for (int y1 = minY; y1 <= maxY; y1++) {
                                        for (int z1 = minZ; z1 <= maxZ; z1++) {
                                            Block block = world.getBlockAt(x1, y1, z1);
                                            Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())-door.get("price").asInt());
                                            block.setType(Material.AIR);
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}
