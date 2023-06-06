package org.nacukat.zombiesnacukatedition.Game;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.nacukat.zombiesnacukatedition.Game.Windows.windowBreak;

import java.util.ArrayList;
import java.util.List;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class StartGame {
    public static boolean notSpawnedAll = true;
    public void start(){
            inGame = true;
        new BukkitRunnable(){


            double count = 0;
            int currentRound = 0;
            @Override
            public void run() {
                List<Player> livingPlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
                boolean allDead = true;
                for (Player player : livingPlayers){
                    if(!isDown.get(player)) allDead = false;
                }
                if(allDead){
                    currentMap = null;
                    for (Player player : Bukkit.getWorld("world").getPlayers()){
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1.0F, 0.8F);
                        player.sendTitle("§cGame Over!", "§7You made it to Round "+currentRound,10,100,20);
                        player.sendMessage((count/10)+"s");
                        currentMap = null;
                        inGame = false;
                        cancel();
                    }
                    cancel();
                }
                count++;
                List<LivingEntity> arrayList = Bukkit.getWorld("world").getLivingEntities().stream().filter(livingEntity -> livingEntity.getType() != EntityType.PLAYER&&livingEntity.getType() != EntityType.ARMOR_STAND).toList();

                if (!notSpawnedAll &&arrayList.size() == 0||count ==1){
                    if(currentRound< node.get("Maps").get(currentMap).get("TotalRound").asInt()){

                        new StartWave().start(currentRound);
                        for (Player player : Bukkit.getWorld("world").getPlayers()){
                            player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.3D);
                            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0F, 0.8F);
                            player.sendTitle("§cRound "+(currentRound+1), "");
                            if(currentRound != 0){
                                player.sendMessage((count/10)+"s");
                            }
                        }
                        currentRound++;
                    }else {
                        for (Player player : Bukkit.getWorld("world").getPlayers()){
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1.0F, 0.8F);
                            player.sendTitle("§aYou Win!", "§7You made it to Round "+node.get("Maps").get(currentMap).get("TotalRound").asText()+"!",10,100,20);
                            player.sendMessage((count/10)+"s");
                            currentMap = null;
                            inGame = false;
                            cancel();
                        }
                    }

                }



            }
        }.runTaskTimer(plugin,0,2);
    }
}
