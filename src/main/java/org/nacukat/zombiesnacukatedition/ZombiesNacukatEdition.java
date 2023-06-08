package org.nacukat.zombiesnacukatedition;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
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
import org.nacukat.zombiesnacukatedition.comands.*;
import org.nacukat.zombiesnacukatedition.Game.Doors.openingDoor;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.nacukat.zombiesnacukatedition.Game.StartGame.currentRound;

public final class ZombiesNacukatEdition extends JavaPlugin {
    public static Plugin plugin;

    public static String currentMap;

    public static HashMap<String, Boolean> HasQF = new HashMap<>();

    public static HashMap<String, Boolean> HasFB = new HashMap<>();

    public static HashMap<String, Boolean> HasFR = new HashMap<>();

    public static HashMap<String, Integer> EHs = new HashMap<>();

    public static HashMap<Player, Boolean> isDown = new HashMap<>();

    public static String[] bosses = new String[] { "The old one", "Giant" };

    public static Material[] canWallShot = new Material[]{
            Material.STONE_BRICK_SLAB,
            Material.STONE_BRICK_STAIRS,
            Material.OAK_SLAB,
            Material.BARRIER,
            Material.GLASS_PANE,
            Material.SPRUCE_SLAB,
            Material.SPRUCE_STAIRS};
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

    public static HashMap<Material, Integer[]> totalbulletsMaterial = new HashMap<>();
    static {
        totalbulletsMaterial.put(Material.DIAMOND_PICKAXE,new Integer[]{100,120});
        totalbulletsMaterial.put(Material.GOLDEN_PICKAXE,new Integer[]{70,100,130,160,200,250});
        totalbulletsMaterial.put(Material.GOLDEN_SHOVEL,new Integer[]{240,288,312,336});
        totalbulletsMaterial.put(Material.FLINT_AND_STEEL,new Integer[]{20,30,36,42});
        totalbulletsMaterial.put(Material.IRON_HOE,new Integer[]{65,80});
    }
    public static boolean inGame = false;
    public static HashMap<Integer,Integer> totalBullets = new HashMap<>();
    public static HashMap<String, Long> Kills = new HashMap<>();

    public static HashMap<Player,Boolean> showParticle = new HashMap<>();
    public static JsonNode node = null;

    public static HashMap<UUID,Integer> Gold = new HashMap<>();

    //ZZ,RR,GD,DBS
    public static HashMap<UUID,List<Long>> gunShoots = new HashMap<>();
    public static HashMap<UUID,List<Long>> gunClicks = new HashMap<>();
    public static HashMap<UUID,List<Long>> slotShoots = new HashMap<>();
    public static HashMap<UUID,List<Long>> slotClicks = new HashMap<>();
    public static HashMap<UUID,List<Long>> slotHolding = new HashMap<>();
    public static HashMap<UUID,Long> damage = new HashMap<>();
    public static HashMap<UUID,Long> lastSlotChange = new HashMap<>();
    public static HashMap<UUID,Boolean> isCounting = new HashMap<>();
    public static List<Material> guns = new ArrayList<>();
    static {
        guns.add(Material.DIAMOND_PICKAXE);
        guns.add(Material.GOLDEN_SHOVEL);
        guns.add(Material.GOLDEN_PICKAXE);
        guns.add(Material.FLINT_AND_STEEL);
        guns.add(Material.IRON_HOE);
    }
    public static int currentWave = 0;
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
        getCommand("setmap").setTabCompleter(new mapCompleter());
        getCommand("toggle-particles").setExecutor(new toggleParticle());
        getCommand("gold").setExecutor(new giveGold());
        getCommand("start").setExecutor(new start());
        getCommand("startCounting").setExecutor(new startCounting());
        getCommand("startCounting").setTabCompleter(new startCountingCompleter());
        plugin = this;
        getLogger().info("§bプラグインが起動しました");

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getMainScoreboard();
        if(scoreboard.getObjective("time") != null){
            scoreboard.getObjective("time").unregister();
        }
        if(scoreboard.getObjective("Gold") != null){
            scoreboard.getObjective("Gold").unregister();
        }
        if(scoreboard.getObjective("ZombiesKills") != null){
            scoreboard.getObjective("ZombiesKills").unregister();
        }
        scoreboard.registerNewObjective("ZombiesKills", Criteria.DUMMY,"ZombiesKills");
        scoreboard.registerNewObjective("Gold", Criteria.DUMMY,"§e§lZombies");
        Objective objective =scoreboard.getObjective("Gold");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Objective kills = scoreboard.getObjective("ZombiesKills");
        kills.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        new BukkitRunnable(){
            List<String> score = new ArrayList<>();
            int time = 0;
            boolean sakkimade = false;
            @Override
            public void run() {
                int i = 1;
                int scoreNum = 10;
                for (String entry : scoreboard.getEntries()){
                    scoreboard.resetScores(entry);
                }
                int downs = 0;
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
                objective.getScore("§7"+sdf.format(date)).setScore(scoreNum);
                scoreNum--;

                objective.getScore("  ").setScore(scoreNum);
                scoreNum--;
                objective.getScore("§cRound: "+(currentRound+1)).setScore(scoreNum);
                scoreNum--;
                int zombiesLeft = 0;
                if (currentMap != null){
                    int wave = 0;
                    for (JsonNode waves:node.get("Maps").get(currentMap).get("Rounds").get(currentRound).get("waves")) {
                        if (currentWave == 0||wave>=currentWave){
                            for (JsonNode zombie : waves.get("Zombie")) {
                                zombiesLeft += zombie.get(1).asInt();
                            }
                        }
                        wave++;
                    }
                }
                List<LivingEntity> onground = Bukkit.getWorld("world").getLivingEntities();
                onground.removeIf(liv -> liv.getType().equals(EntityType.PLAYER) || liv.getType().equals(EntityType.ARMOR_STAND));
//                Bukkit.getPlayer("Nacukat").sendMessage(Component.text("これから何体湧くか:"+zombiesLeft+" 現在いる数:"+onground.size()+" 現在のウェーブ:"+currentWave+" 現在のラウンド:"+currentRound));
                objective.getScore("Zombies Left: §a"+(zombiesLeft+onground.size())).setScore(scoreNum);
                scoreNum--;;

                objective.getScore("").setScore(scoreNum);
                scoreNum--;

                for (Player player : Bukkit.getWorld("world").getPlayers()){

                    isDown.putIfAbsent(player,false);
                    Gold.putIfAbsent(player.getUniqueId(),0);
                    Kills.putIfAbsent(player.getName(), Long.valueOf(0L));

                    kills.getScore(player).setScore(Math.toIntExact(Kills.get(player.getName())));
                    objective.getScore(ChatColor.AQUA+player.getName()+"§f: "+ChatColor.GOLD+Gold.get(player.getUniqueId())).setScore(scoreNum);
                    scoreNum--;
                    score.add(ChatColor.AQUA+player.getName()+"§f: "+ChatColor.GOLD+Gold.get(player.getUniqueId()));


                    i++;

                }

                if(!inGame)
                    sakkimade = false;
                objective.getScore(" ").setScore(scoreNum);
                scoreNum--;
                objective.getScore("Time: §a"+String.format("%02d", ((int)time/60))+":"+String.format("%02d", ((int)time%60))).setScore(scoreNum);
                scoreNum--;
                if (inGame) {
                    if (!sakkimade)time = 0;
                    sakkimade = true;
                    time++;
                }

                objective.getScore("Map: §a"+currentMap).setScore(scoreNum);
                scoreNum--;
                objective.getScore("   ").setScore(scoreNum);
                scoreNum--;
                objective.getScore("§ewww.kusaｗwＷW.com").setScore(scoreNum);
                scoreNum--;

            }
        }.runTaskTimer(plugin,0,20);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
