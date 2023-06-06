package org.nacukat.zombiesnacukatedition.comands;

import com.fasterxml.jackson.databind.JsonNode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.nacukat.zombiesnacukatedition.Game.Doors.setDoors;
import org.nacukat.zombiesnacukatedition.Game.StartGame;
import org.nacukat.zombiesnacukatedition.Game.Windows.setSpawnPoints;
import org.nacukat.zombiesnacukatedition.Game.Windows.setWindows;
import org.nacukat.zombiesnacukatedition.Game.Windows.windowBreak;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class setMap implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length==0)return false;
        if (node.get("Maps").get(strings[0])!= null){
            currentMap = strings[0];
            new setWindows().set();
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (currentMap == null)cancel();
                    new windowBreak().checkZombieInRange();
                }
            }.runTaskTimer(plugin, 0L, 30L); // 1秒 = 20 tick
            for (JsonNode door : node.get("Maps").get(currentMap).get("Doors")){
                Location start = new Location(Bukkit.getWorld("world"),door.get("startPoint").get(0).asInt(),door.get("startPoint").get(1).asInt(),door.get("startPoint").get(2).asInt());
                Location end = new Location(Bukkit.getWorld("world"),door.get("endPoint").get(0).asInt(),door.get("endPoint").get(1).asInt(),door.get("endPoint").get(2).asInt());
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
                new setSpawnPoints().setStand(location,true);
                Location armlocation = new Location(Bukkit.getWorld("world"),window.get("window").get(3).get(0).asDouble(),window.get("window").get(3).get(1).asDouble(),window.get("window").get(3).get(2).asDouble());
                new setSpawnPoints().setStand(armlocation,false);
            }
            new StartGame().start();

        }
        return false;
    }
}
