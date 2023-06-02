package org.nacukat.zombiesnacukatedition;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.nacukat.zombiesnacukatedition.Guns.invokeGun;
import org.nacukat.zombiesnacukatedition.Guns.playerAnimation;
import org.nacukat.zombiesnacukatedition.Listeners.PlayerEvent;
import org.nacukat.zombiesnacukatedition.Listeners.RemoveDropItem;
import org.nacukat.zombiesnacukatedition.Shops.getCommend;
import org.nacukat.zombiesnacukatedition.Shops.shopListener;
import org.nacukat.zombiesnacukatedition.comands.revive;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class ZombiesNacukatEdition extends JavaPlugin {
    public static Plugin plugin;

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

    public static JsonNode node= null;
    @Override
    public void onEnable() {
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
        getCommand("rew").setExecutor(new revive());
        getCommand("open-shop").setExecutor(new getCommend());
        plugin = this;
        getLogger().info("§bプラグインが起動しました");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
