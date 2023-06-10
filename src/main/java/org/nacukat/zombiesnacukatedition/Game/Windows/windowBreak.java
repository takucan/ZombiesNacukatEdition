package org.nacukat.zombiesnacukatedition.Game.Windows;

import com.fasterxml.jackson.databind.JsonNode;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class windowBreak{

    public void checkZombieInRange() {
        if(currentMap == null)return;
        for (JsonNode window : node.get("Maps").get(currentMap).get("Windows")){
            Location point1 = new Location(Bukkit.getWorld("world"),window.get("window").get(0).get(0).asInt(),window.get("window").get(0).get(1).asInt(),window.get("window").get(0).get(2).asInt());
            Location point2 = new Location(Bukkit.getWorld("world"),window.get("window").get(1).get(0).asInt(),window.get("window").get(1).get(1).asInt(),window.get("window").get(1).get(2).asInt());
            Location point3 = new Location(Bukkit.getWorld("world"),window.get("window").get(2).get(0).asDouble(),window.get("window").get(2).get(1).asDouble(),window.get("window").get(2).get(2).asDouble());
            // 範囲内にいるかチェックする
            if(!point3.getNearbyLivingEntities(1.5,livingEntity -> livingEntity.getType() != EntityType.PLAYER).isEmpty()){

                    // 範囲内にいる場合はランダムなブロックを壊す
                    destroyRandomBlock(point1,point2);

            }
        }

    }


    private void destroyRandomBlock(Location point1,Location point2) {
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
                    if (block.getType() != Material.AIR) {
                        blocks.add(block);
                    }
                }
            }
        }


        if (!blocks.isEmpty()) {
            Block block = blocks.get(random.nextInt(blocks.size()));
            Location blockLocation = new Location(world,block.getX()+0.5,block.getY()+0.5,block.getZ()+0.5);
            world.spawnParticle(Particle.BLOCK_CRACK,blockLocation,40, block.getType().createBlockData());
            block.setType(Material.AIR);

            for (Player player1 : Bukkit.getOnlinePlayers()){
                player1.playSound(block.getLocation(),Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR,1,1);
            }

        }
    }

}
