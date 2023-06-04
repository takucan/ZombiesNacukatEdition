package org.nacukat.zombiesnacukatedition.Game.Windows;

import com.fasterxml.jackson.databind.JsonNode;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class checkIsWindowBreak {
    public List<Block> check(JsonNode window ,Player player){
        Location point1 = new Location(Bukkit.getWorld("world"),window.get("window").get(0).get(0).asInt(),window.get("window").get(0).get(1).asInt(),window.get("window").get(0).get(2).asInt());
        Location point2 = new Location(Bukkit.getWorld("world"),window.get("window").get(1).get(0).asInt(),window.get("window").get(1).get(1).asInt(),window.get("window").get(1).get(2).asInt());
        World world = Bukkit.getWorld("world"); // ワールド名を適宜変更してください
        Random random = new Random();

        double minX = Math.min(point1.getX(), point2.getX());
        double minY = Math.min(point1.getY(), point2.getY());
        double minZ = Math.min(point1.getZ(), point2.getZ());
        double maxX = Math.max(point1.getX(), point2.getX());
        double maxY = Math.max(point1.getY(), point2.getY());
        double maxZ = Math.max(point1.getZ(), point2.getZ());

        // 範囲内に存在する非空気ブロックのリストを作成する
        List<Block> blocks = new ArrayList<>();
        for (int x = (int) minX; x <= maxX; x++) {
            for (int y = (int) minY; y <= maxY; y++) {
                for (int z = (int) minZ; z <= maxZ; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType() == Material.AIR) {
                        blocks.add(block);
                        player.playSound(block.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR,1,1);
                    }
                }
            }
        }
        return blocks;
    }
}
