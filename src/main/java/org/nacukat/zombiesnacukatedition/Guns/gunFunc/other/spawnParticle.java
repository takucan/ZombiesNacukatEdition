package org.nacukat.zombiesnacukatedition.Guns.gunFunc.other;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.isDown;
import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.showParticle;

public class spawnParticle {
    public void spawn(Particle particle,Particle.DustOptions dustOptions, Player player){
        Vector direction = player.getEyeLocation().getDirection();
        double distance = 1;
        double maxDistance = 10000.0; // 最大探索距離（例として100ブロックまでとします）
        Location loc = player.getEyeLocation();
        for (double d = distance; d < maxDistance; d += distance) {
            loc = loc.add(direction.multiply(distance));
            if(Bukkit.getWorld("world").getBlockAt(loc).getType().equals(Material.AIR)){
                for (Player player1 : Bukkit.getWorld("world").getPlayers()){
                    if(showParticle.get(player1)){
                        player1.spawnParticle(particle,loc,0,dustOptions);
                    }
                }

            }else {
                break;
            }
        }

    }
}
