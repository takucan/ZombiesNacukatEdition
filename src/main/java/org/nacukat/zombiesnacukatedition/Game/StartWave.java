package org.nacukat.zombiesnacukatedition.Game;

import com.fasterxml.jackson.databind.JsonNode;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Bukkit.getServer;
import static org.nacukat.zombiesnacukatedition.Game.StartGame.notSpawnedAll;
import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class StartWave {
    public void start(int round){
        notSpawnedAll =true;
        new BukkitRunnable(){
            @Override
            public void run() {
                if(currentMap == null){
                    cancel();
                    return;
                }
                if(currentWave < node.get("Maps").get(currentMap).get("Rounds").get(round).get("wavesInRound").asInt()){
                    notSpawnedAll = true;
                    JsonNode wave = node.get("Maps").get(currentMap).get("Rounds").get(round).get("waves").get(currentWave);
                    getServer().getLogger().info(wave.toString()+currentWave);
                    for (JsonNode zombie : wave.get("Zombie")){
                        for (int i = 0;i<zombie.get(1).asInt();i++){
                            new Zombies().spawnZombie(zombie.get(0).asInt());
                        }
                    }

                    currentWave++;
                }
                if(currentWave == node.get("Maps").get(currentMap).get("Rounds").get(round).get("wavesInRound").asInt()){
                    cancel();
                    notSpawnedAll=false;
                }
            }
        }.runTaskTimer(plugin,250,300);
    }
}
