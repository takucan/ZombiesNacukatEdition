package org.nacukat.zombiesnacukatedition.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class PlayerEvent implements Listener {
    public HashMap<Player, Boolean> playerRevivingStatus = new HashMap<>();

    public HashMap<Player, Player> playerRevivingWhom = new HashMap<>();

    public HashMap<Player, Double> Count = new HashMap<>();


    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        isDown.put(player, Boolean.valueOf(false));
        player.sendMessage("" + Kills.keySet() + Kills.keySet());
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getMainScoreboard();
        Objective objective = scoreboard.getObjective("ZombiesKills");
        objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        for (Player player1 : Bukkit.getServer().getOnlinePlayers()) {
            Kills.putIfAbsent(player1.getName(), Long.valueOf(0L));
            Score score = objective.getScore((OfflinePlayer)player1);
            score.setScore(Math.toIntExact((Kills.get(player1.getName())).longValue()));
            player1.setScoreboard(scoreboard);
        }
    }

    @EventHandler
    public void onPlayerDied(PlayerDeathEvent e) {
        Player player = e.getPlayer();
        isDown.put(player, Boolean.valueOf(true));
        Location diedLocation = player.getLocation().subtract(0.0D, 1.0D, 0.0D);
        Entity sittingArrow = player.getWorld().spawnEntity(diedLocation, EntityType.ARROW);
        player.setHealth(player.getMaxHealth());
        player.setGameMode(GameMode.CREATIVE);
        player.teleport(new Location(player.getWorld(),player.getLocation().getX(),player.getLocation().getY()-1,player.getLocation().getZ()));
        sittingArrow.setSilent(true);
        sittingArrow.setCustomName("sittingArrow");
        sittingArrow.addPassenger((Entity)player);
        new BukkitRunnable(){
            public void run(){
                if(isDown.get(player)){
                    sittingArrow.addPassenger(player);
                    sittingArrow.setTicksLived(1);
                }else {
                    cancel();
                }
            }
        }.runTaskTimer(plugin,5,20);
    }

    @EventHandler
    public void onInventoryMove(InventoryClickEvent e) {
        isDown.putIfAbsent((Player)e.getView().getPlayer(), Boolean.valueOf(false));
        if (((Boolean) isDown.get(e.getView().getPlayer())).booleanValue())
            e.setCancelled(true);
    }
    @EventHandler
    public void onHunger(FoodLevelChangeEvent e){
        if(e.getEntity().getType().equals(EntityType.PLAYER)){

                e.setFoodLevel(20);

        }

    }

    @EventHandler
    public void onPlayerSneakDown(PlayerToggleSneakEvent e) {
        final Player player = e.getPlayer();
        double count = 15.0;
        HasFR.putIfAbsent(player.getName(), Boolean.valueOf(false));
        if (HasFR.get(player.getName())) count = 3.0D;

        isDown.putIfAbsent(player, Boolean.valueOf(false));
        this.playerRevivingStatus.putIfAbsent(player, Boolean.valueOf(false));
        this.Count.putIfAbsent(player, Double.valueOf(count));

        if (((Boolean) isDown.get(player)).booleanValue()) {
            e.setCancelled(true);
        } else  {
            final double finalCount = count;
            (new BukkitRunnable() {
                public void run() {
                    if(!player.isSneaking()){
                        cancel();
                    }
                    if (player.isSneaking() && !isDown.get(player)) {
                        List<Player> players = new ArrayList<>(player.getLocation().getNearbyPlayers(3.0D));
                        players.sort(Comparator.comparingDouble(player1 -> player1.getLocation().distance(player.getLocation())));
                        if (!((Boolean)playerRevivingStatus.get(player)).booleanValue()) {
                            players.sort(Comparator.comparingDouble(player1 -> player1.getLocation().distance(player.getLocation())));
                            for (int i = 1; i < players.size(); i++) {
                                isDown.putIfAbsent(players.get(i), Boolean.valueOf(false));
                                if (((Boolean) isDown.get(players.get(i))).booleanValue() && !playerRevivingWhom.containsValue(players.get(i))) {
                                    playerRevivingWhom.put(player, players.get(i));
                                    playerRevivingStatus.put(player, Boolean.valueOf(true));
                                }
                            }
                        } else if (players.contains(playerRevivingWhom.get(player))) {
                            if (((Boolean) isDown.get(playerRevivingWhom.get(player))).booleanValue())
                                if (player.getLocation().distance(((Player)playerRevivingWhom.get(player)).getLocation()) <= 3.0D && player.isSneaking()) {
                                    if (((Double)Count.get(player)).doubleValue() > 0.0D) {
                                        ((Player)playerRevivingWhom.get(player)).sendActionBar("§b" + player.getName() + " §ais Reviving you §7- §c" + ((Double)Count.get(player)).doubleValue() / 10.0D + "s");
                                        player.sendActionBar("§aReviving §b" + ((Player)playerRevivingWhom.get(player)).getName() + " §7- §c" + ((Double)Count.get(player)).doubleValue() / 10.0D + "s");
                                        Count.put(player, Double.valueOf(((Double)Count.get(player)).doubleValue() - 1.0D));
                                    } else {
                                        Bukkit.broadcastMessage("§b" + player.getName() + " §eRevived §b" + ((Player)playerRevivingWhom.get(player)).getName());
                                        isDown.replace(playerRevivingWhom.get(player), Boolean.valueOf(false));
                                        ((Player)playerRevivingWhom.get(player)).setHealth(((Player)playerRevivingWhom.get(player)).getMaxHealth() / 2.0D);
                                        ((Player)playerRevivingWhom.get(player)).leaveVehicle();
                                        ((Player)playerRevivingWhom.get(player)).teleport(((Player)playerRevivingWhom.get(player)).getEyeLocation());
                                        ((Player)playerRevivingWhom.get(player)).setInvisible(false);
                                        ((Player)playerRevivingWhom.get(player)).setGameMode(GameMode.ADVENTURE);
                                        playerRevivingWhom.remove(player);
                                        playerRevivingStatus.put(player, Boolean.valueOf(false));
                                        player.sendActionBar("§aRevived");
                                        Count.put(player, Double.valueOf(finalCount));
                                    }
                                } else {
                                    ((Player)playerRevivingWhom.get(player)).sendActionBar(" ");
                                    player.sendActionBar(" ");
                                    playerRevivingWhom.remove(player);
                                    playerRevivingStatus.put(player, Boolean.valueOf(false));
                                    Count.put(player, Double.valueOf(finalCount));
                                }
                        } else {
                            ((Player)playerRevivingWhom.get(player)).sendActionBar(" ");
                            player.sendActionBar(" ");
                            Count.put(player, Double.valueOf(finalCount));
                            playerRevivingWhom.remove(player);
                            playerRevivingStatus.put(player, Boolean.valueOf(false));
                        }
                    } else {
                        Count.put(player, Double.valueOf(finalCount));
                        playerRevivingWhom.remove(player);
                        playerRevivingStatus.put(player, Boolean.valueOf(false));
                    }
                }
            }).runTaskTimer(plugin, 0L, 2L);
        }
    }
}
