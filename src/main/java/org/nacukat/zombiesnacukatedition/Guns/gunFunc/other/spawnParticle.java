package org.nacukat.zombiesnacukatedition.Guns.gunFunc.other;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class spawnParticle {
    public void spawn(Particle particle,Particle.DustOptions dustOptions, Player player){
        Vector direction = player.getEyeLocation().getDirection();
        double distance = 1;
        double maxDistance = 10000.0; // 最大探索距離（例として100ブロックまでとします）
        Location loc = player.getEyeLocation();
        for (double d = distance; d < maxDistance; d += distance) {
            loc = loc.add(direction.multiply(distance));
            if(Bukkit.getWorld("world").getBlockAt(loc).getType().equals(Material.AIR)){
                player.getWorld().spawnParticle(particle,loc,0,dustOptions);
            }else {
                break;
            }
        }

    }
}
