package org.nacukat.zombiesnacukatedition.Guns.gunFunc.other;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.isDown;
import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.showParticle;

public class spawnParticle {
    public void spawn(Particle particle,Particle.DustOptions dustOptions, Player player){
        Vector direction = player.getEyeLocation().getDirection();
        double distance = 1;
        double maxDistance = 70.0; // 最大探索距離（例として100ブロックまでとします）
        Location loc = player.getEyeLocation();
        for (double d = distance; d < maxDistance; d += distance) {
            loc = loc.add(direction.multiply(distance));
            if(Bukkit.getWorld("world").getBlockAt(loc).getType().equals(Material.AIR)){
                for (Player player1 : Bukkit.getWorld("world").getPlayers()){
                    if(showParticle.get(player1)){
                        if(dustOptions == null){
                            player1.spawnParticle(particle,loc,0);
                        }else {
                            player1.spawnParticle(particle,loc,0,dustOptions);
                        }
                    }
                }

            }else {

                break;
            }
        }

    }

    public void rainbow(Player player){

        Vector direction = player.getEyeLocation().getDirection();
        double distance = 1;
        double maxDistance = 70.0; // 最大探索距離（例として100ブロックまでとします）
        Location loc = player.getEyeLocation();
        for (double d = distance; d < maxDistance; d += distance) {
            loc = loc.add(direction.multiply(distance));
            if(Bukkit.getWorld("world").getBlockAt(loc).getType().equals(Material.AIR)){
                for (Player player1 : Bukkit.getWorld("world").getPlayers()){
                    if(showParticle.get(player1)){
                        Random random = new Random();
                        int randomInt = random.nextInt(6);
                        Color[] colors = {Color.PURPLE,Color.BLUE,Color.AQUA,Color.LIME,Color.YELLOW,Color.ORANGE,Color.RED};
                        Particle.DustOptions dustOptions = new Particle.DustOptions(colors[randomInt],1);
                        Particle particle = Particle.REDSTONE;
                        player1.spawnParticle(particle,loc,0,dustOptions);
                    }
                }

            }else {
                break;
            }
        }

    }
}
