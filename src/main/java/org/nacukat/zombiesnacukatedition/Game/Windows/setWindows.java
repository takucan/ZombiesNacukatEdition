package org.nacukat.zombiesnacukatedition.Game.Windows;

import com.fasterxml.jackson.databind.JsonNode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.currentMap;
import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.node;

public class setWindows {
    public void set(){
        World world = Bukkit.getWorld("world");
        for (JsonNode window : node.get("Maps").get(currentMap).get("Windows")){
            Location point1 = new Location(world,window.get("window").get(0).get(0).asInt(),window.get("window").get(0).get(1).asInt(),window.get("window").get(0).get(2).asInt());
            Location point2 = new Location(world,window.get("window").get(1).get(0).asInt(),window.get("window").get(1).get(1).asInt(),window.get("window").get(1).get(2).asInt());
            int minX = Math.min(point1.getBlockX(), point2.getBlockX());
            int minY = Math.min(point1.getBlockY(), point2.getBlockY());
            int minZ = Math.min(point1.getBlockZ(), point2.getBlockZ());
            int maxX = Math.max(point1.getBlockX(), point2.getBlockX());
            int maxY = Math.max(point1.getBlockY(), point2.getBlockY());
            int maxZ = Math.max(point1.getBlockZ(), point2.getBlockZ());

            // 指定範囲内のブロックを壊す
            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        Location location = new Location(world, x, y, z);
                        location.getBlock().setType(Material.OAK_SLAB);
                    }
                }
            }
        }

    }
}
