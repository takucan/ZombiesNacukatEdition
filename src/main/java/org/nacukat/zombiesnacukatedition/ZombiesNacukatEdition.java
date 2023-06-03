package org.nacukat.zombiesnacukatedition;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import org.nacukat.zombiesnacukatedition.Guns.invokeGun;
import org.nacukat.zombiesnacukatedition.Guns.playerAnimation;
import org.nacukat.zombiesnacukatedition.Listeners.PlayerEvent;
import org.nacukat.zombiesnacukatedition.Listeners.RemoveDropItem;
import org.nacukat.zombiesnacukatedition.Shops.getShopCommend;
import org.nacukat.zombiesnacukatedition.Shops.shopListener;
import org.nacukat.zombiesnacukatedition.comands.reloadConfig;
import org.nacukat.zombiesnacukatedition.comands.revive;
import org.nacukat.zombiesnacukatedition.comands.setMap;
import org.nacukat.zombiesnacukatedition.Game.Doors.openingDoor;
import org.nacukat.zombiesnacukatedition.comands.toggleParticle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class ZombiesNacukatEdition extends JavaPlugin {
    public static Plugin plugin;

    public static String currentMap;

    public static HashMap<String, Boolean> HasQF = new HashMap<>();

    public static HashMap<String, Boolean> HasFB = new HashMap<>();

    public static HashMap<String, Boolean> HasFR = new HashMap<>();

    public static HashMap<String, Integer> EHs = new HashMap<>();

    public static HashMap<Player, Boolean> isDown = new HashMap<>();

    public static String[] bosses = new String[] { "The old one", "Giant" };

    public static Material[] canWallShot = new Material[]{Material.STONE_BRICK_SLAB,Material.STONE_BRICK_STAIRS,Material.OAK_SLAB,Material.BARRIER,Material.GLASS_PANE};
    public static HashMap<EntityType, Double[]> HeadSizes = new HashMap<>();

    static {
        HeadSizes.put(EntityType.GIANT, new Double[] {2.0D, 4.0D, 2.0D, 2.0D});
        HeadSizes.put(EntityType.ZOMBIE, new Double[] {0.51D, 0.6D, 0.51D, 0.3D});
        HeadSizes.put(EntityType.IRON_GOLEM, new Double[] {0.9D, 0.63D, 0.9D, 0.315D});
    }
    public static HashMap<ItemStack, Long> lastShotTimes = new HashMap<>();

    public static HashMap<Integer, Boolean> isShootingRR = new HashMap<>();

    public static HashMap<Integer, Boolean> isReloading = new HashMap<>();

    public static HashMap<Integer, Long> magazines = new HashMap<>();

    public static HashMap<Integer, Integer> Ultimates = new HashMap<>();

    public static HashMap<Player, Boolean> isBlock = new HashMap<>();

    public static HashMap<Player, Long> lastBlock = new HashMap<>();

    public static HashMap<ItemStack, Long> totalbullets = new HashMap<>();
    public static HashMap<String, Long> Kills = new HashMap<>();

    public static HashMap<Player,Boolean> showParticle = new HashMap<>();
    public static JsonNode node = null;

    public static HashMap<UUID,Integer> Gold = new HashMap<>();
    @Override
    public void onEnable() {
        for (Player player : Bukkit.getWorld("world").getPlayers()){
            showParticle.putIfAbsent(player,true);
        }

        ObjectMapper mapper = new ObjectMapper();
        File json = new File(getDataFolder()+"/Config.json");
        try {
            node = mapper.readTree(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new invokeGun(),this);
        getServer().getPluginManager().registerEvents(new playerAnimation(),this);
        getServer().getPluginManager().registerEvents(new shopListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerEvent(),this);
        getServer().getPluginManager().registerEvents(new RemoveDropItem(),this);
        getServer().getPluginManager().registerEvents(new openingDoor(),this);
        getCommand("rew").setExecutor(new revive());
        getCommand("open-shop").setExecutor(new getShopCommend());
        getCommand("reload-config").setExecutor(new reloadConfig());
        getCommand("setmap").setExecutor(new setMap());
        getCommand("toggle-particles").setExecutor(new toggleParticle());
        plugin = this;
        getLogger().info("§bプラグインが起動しました");

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getMainScoreboard();
        if(scoreboard.getObjective("Gold") != null){
            scoreboard.getObjective("Gold").unregister();
        }
        if(scoreboard.getObjective("ZombiesKills") != null){
            scoreboard.getObjective("ZombiesKills").unregister();
        }
        scoreboard.registerNewObjective("ZombiesKills", Criteria.DUMMY,"ZombiesKills");
        scoreboard.registerNewObjective("Gold", Criteria.DUMMY,"Golds");
        Objective objective =scoreboard.getObjective("Gold");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Objective kills = scoreboard.getObjective("ZombiesKills");
        kills.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        new BukkitRunnable(){
            List<String> score = new ArrayList<>();
            @Override
            public void run() {
                int i = 0;
                for (String entry : scoreboard.getEntries()){
                    scoreboard.resetScores(entry);
                }
                int downs = 0;
                for (Player player : Bukkit.getWorld("world").getPlayers()){

                    isDown.putIfAbsent(player,false);
                    Gold.putIfAbsent(player.getUniqueId(),0);
                    Kills.putIfAbsent(player.getName(), Long.valueOf(0L));

                    kills.getScore(player).setScore(Math.toIntExact(Kills.get(player.getName())));
                    objective.getScore(ChatColor.AQUA+player.getName()+"§f: "+ChatColor.GOLD+Gold.get(player.getUniqueId())).setScore(i);
                    score.add(ChatColor.AQUA+player.getName()+"§f: "+ChatColor.GOLD+Gold.get(player.getUniqueId()));

                    i++;

                }

            }
        }.runTaskTimer(plugin,0,20);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
