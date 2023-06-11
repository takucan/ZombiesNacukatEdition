package org.nacukat.zombiesnacukatedition.Listeners;

import com.fasterxml.jackson.databind.JsonNode;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import org.nacukat.zombiesnacukatedition.Game.Windows.checkIsWindowBreak;

import java.util.*;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class PlayerEvent implements Listener {
    public HashMap<Player, Boolean> playerRevivingStatus = new HashMap<>();

    public HashMap<Player, Player> playerRevivingWhom = new HashMap<>();

    public HashMap<Player, Double> Count = new HashMap<>();

    @EventHandler
    public void counting(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Player player))return;
        isCounting.putIfAbsent(player.getUniqueId(),false);
        if(isCounting.get(player.getUniqueId())){
            damage.putIfAbsent(player.getUniqueId(),0L);
            damage.put(player.getUniqueId(), (long) (damage.get(player.getUniqueId())+e.getDamage()));
        }
    }

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        isDown.put(player, Boolean.FALSE);
        showParticle.putIfAbsent(player,true);
        player.sendMessage(String.valueOf(Kills.keySet()) + Kills.keySet());
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getMainScoreboard();
        Objective objective = scoreboard.getObjective("ZombiesKills");
        Objects.requireNonNull(objective).setDisplaySlot(DisplaySlot.PLAYER_LIST);
        for (Player player1 : Bukkit.getServer().getOnlinePlayers()) {
            Kills.putIfAbsent(player1.getName(), 0L);
            Score score = objective.getScore(player1);
            score.setScore(Math.toIntExact(Kills.get(player1.getName())));
            player1.setScoreboard(scoreboard);
        }
    }

    @EventHandler
    public void onPlayerDied(PlayerDeathEvent e) {
        Player player = e.getPlayer();
        isDown.put(player, Boolean.TRUE);
        List<LivingEntity> nearest = new ArrayList<>();
        nearest.addAll(player.getLocation().getNearbyLivingEntities(20,livingEntity -> !livingEntity.getMetadata("Door").isEmpty()));
        nearest.sort(Comparator.comparingDouble(player1 -> player1.getLocation().distance(player.getLocation())));

        Bukkit.broadcastMessage(ChatColor.AQUA+player.getName()+"§ewas knocked down in §a"+nearest.get(0).getMetadata("Door").get(0).asString());
        for (Player player1 :Bukkit.getOnlinePlayers()){
            player1.sendTitle("",ChatColor.AQUA+player.getName()+"§ewas knocked down in §a"+nearest.get(0).getMetadata("Door").get(0).asString());
            player1.playSound(player1.getLocation(),Sound.ENTITY_ENDER_DRAGON_AMBIENT,1,0.9F);
        }
        Location diedLocation = player.getLocation().subtract(0.0D, 1.0D, 0.0D);
        Entity sittingArrow = player.getWorld().spawnEntity(diedLocation, EntityType.ARROW);
        player.setHealth(player.getMaxHealth());
        player.setGameMode(GameMode.CREATIVE);
        player.teleport(new Location(player.getWorld(),player.getLocation().getX(),player.getLocation().getY()-1,player.getLocation().getZ()));
        sittingArrow.setSilent(true);
        sittingArrow.setInvulnerable(true);
        sittingArrow.setCustomName("sittingArrow");
        sittingArrow.addPassenger(player);
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
        isDown.putIfAbsent((Player)e.getView().getPlayer(), Boolean.FALSE);
        if (isDown.get((Player) e.getWhoClicked()))
            e.setCancelled(true);
    }
    @EventHandler
    public void onHunger(FoodLevelChangeEvent e){
        if(e.getEntity().getType().equals(EntityType.PLAYER)){

            e.setFoodLevel(20);

        }

    }
    @EventHandler
    public void shop(EntityPickupItemEvent e){
        e.setCancelled(true);
    }
    @EventHandler
    public void onArm(PlayerArmorStandManipulateEvent e){
        e.setCancelled(true);
    }
    @EventHandler
    public void onChangeSlot(PlayerItemHeldEvent e){
        Player player = e.getPlayer();
//        isCounting.putIfAbsent(player.getUniqueId(),false);
//        if(isCounting.get(player.getUniqueId())){
//            lastSlotChange.putIfAbsent(player.getUniqueId(),System.currentTimeMillis());
//            slotHolding.putIfAbsent(player.getUniqueId(), new ArrayList<>(Arrays.asList(0L,0L,0L,0L,0L,0L,0L,0L,0L)));
//            List<Long> holdings = slotHolding.get(player.getUniqueId());
//            holdings.set(e.getNewSlot(), holdings.get(e.getNewSlot()) +System.currentTimeMillis()-lastSlotChange.get(player.getUniqueId()));
//            slotHolding.put(player.getUniqueId(),holdings);
//            lastSlotChange.put(player.getUniqueId(),System.currentTimeMillis());
//        }
        if (player.getInventory().getItem(e.getNewSlot()) == null)return;
        ItemStack item = player.getInventory().getItem(e.getNewSlot());
        if(!Objects.requireNonNull(item).hasItemMeta()||!item.getItemMeta().hasCustomModelData()||!(item.getType().equals(Material.WOODEN_HOE) ||item.getType().equals(Material.IRON_HOE) ||item.getType().equals(Material.DIAMOND_PICKAXE) || item.getType().equals(Material.GOLDEN_PICKAXE) || item.getType().equals(Material.GOLDEN_SHOVEL) || item.getType().equals(Material.FLINT_AND_STEEL)))return;

        Ultimates.putIfAbsent(item.getItemMeta().getCustomModelData(),0);

        totalBullets.putIfAbsent(item.getItemMeta().getCustomModelData(),totalbulletsMaterial.get(item.getType())[Ultimates.get(item.getItemMeta().getCustomModelData())]);

        if(totalBullets.get(item.getItemMeta().getCustomModelData())<0)
            totalBullets.put(item.getItemMeta().getCustomModelData(),0);
        player.setExp(1);
        player.setLevel(totalBullets.get(item.getItemMeta().getCustomModelData()));
    }
    HashMap<Player,Integer> repairing = new HashMap<>();

    @EventHandler
    public void onPlayerSneakDown(PlayerToggleSneakEvent e) {
        final Player player = e.getPlayer();
        if(!player.isSneaking()&&currentMap != null){
            repairing.putIfAbsent(player,-1);
            if(repairing.get(player) == -1){
                int windowNum = 0;
                for (JsonNode window:node.get("Maps").get(currentMap).get("Windows")){
                    if(!repairing.containsValue(windowNum)){
                        JsonNode winloc = window.get("window").get(3);
                        Location location=new Location(player.getWorld(),winloc.get(0).asDouble(),winloc.get(1).asDouble(),winloc.get(2).asDouble());
                        if(player.getLocation().distance(location) <= 1.5){
                            if(!new checkIsWindowBreak().check(window,player).isEmpty()){
                                player.playSound(player.getLocation(),Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR,1,1);
                                player.sendMessage("§aRepairing Windows. Keep holding SNEAK to continue repairing.");
                                repairing.put(player,windowNum);

                                new BukkitRunnable(){
                                    int last = 0;
                                    int count = 0;
                                    @Override
                                    public void run() {
                                        Location point3 = new Location(Bukkit.getWorld("world"),window.get("window").get(2).get(0).asDouble(),window.get("window").get(2).get(1).asDouble(),window.get("window").get(2).get(2).asDouble());
                                        // 範囲内にいるかチェックする
                                        if(!point3.getNearbyLivingEntities(1,livingEntity -> livingEntity.getType() != EntityType.PLAYER).isEmpty()&&repairing.get(player) != -1){
                                            player.sendMessage("§cStopped repairing. There are enemies nearby!");
                                            cancel();
                                            repairing.put(player,-1);
                                        }
                                        if(!player.isSneaking()&&repairing.get(player) != -1){
                                            player.sendMessage("§cStopped repairing. Hold SNEAK to continue repairing!");
                                            repairing.put(player,-1);
                                            cancel();
                                        }
                                        if(player.getLocation().distance(location) > 1.5&&repairing.get(player) != -1){
                                            player.sendMessage("§cStopped repairing. Stay within range of the window to repair it!");
                                            repairing.put(player,-1);
                                            cancel();
                                        }
                                        if(count - last >= 10){
                                            Location point1 = new Location(Bukkit.getWorld("world"),window.get("window").get(0).get(0).asInt(),window.get("window").get(0).get(1).asInt(),window.get("window").get(0).get(2).asInt());
                                            Location point2 = new Location(Bukkit.getWorld("world"),window.get("window").get(1).get(0).asInt(),window.get("window").get(1).get(1).asInt(),window.get("window").get(1).get(2).asInt());
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
                                                        Block block = Objects.requireNonNull(world).getBlockAt(x, y, z);
                                                        if (block.getType() == Material.AIR) {

                                                            blocks.add(block);
                                                            player.playSound(block.getLocation(),Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR,1,1);

                                                        }
                                                    }
                                                }
                                            }


                                            if (!blocks.isEmpty()) {
                                                Block block = blocks.get(random.nextInt(blocks.size()));
                                                Location blockLocation = new Location(world,block.getX()+0.5,block.getY()+0.5,block.getZ()+0.5);
                                                Material material = Material.OAK_SLAB;
                                                material.createBlockData("[type=bottom]");
                                                player.sendMessage("§6+10 Gold");
                                                Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())+10);
                                                block.setType(material);

                                            }
                                            if(blocks.size() == 1){
                                                player.sendMessage("§aYou have fully repaired this window!");
                                                player.playSound(player.getEyeLocation(),Sound.BLOCK_ANVIL_PLACE,1,2);
                                                repairing.put(player,-1);
                                                cancel();
                                            }

                                            last = count;
                                        }
                                        count++;
                                    }
                                }.runTaskTimer(plugin,0,2);
                            }


                        }
                    }

                    windowNum++;
                }
            }
        }


        double count = 15.0;
        HasFR.putIfAbsent(player.getName(), Boolean.FALSE);
        if (HasFR.get(player.getName())) count = 3.0D;

        isDown.putIfAbsent(player, Boolean.FALSE);
        this.playerRevivingStatus.putIfAbsent(player, Boolean.FALSE);
        this.Count.putIfAbsent(player, count);

        if (isDown.get(player)) {
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
                        if (!playerRevivingStatus.get(player)) {
                            players.sort(Comparator.comparingDouble(player1 -> player1.getLocation().distance(player.getLocation())));
                            for (int i = 1; i < players.size(); i++) {
                                isDown.putIfAbsent(players.get(i), Boolean.FALSE);
                                if (isDown.get(players.get(i)) && !playerRevivingWhom.containsValue(players.get(i))) {
                                    playerRevivingWhom.put(player, players.get(i));
                                    playerRevivingStatus.put(player, Boolean.TRUE);
                                }
                            }
                        } else if (players.contains(playerRevivingWhom.get(player))) {
                            if (isDown.get(playerRevivingWhom.get(player)))
                                if (player.getLocation().distance(playerRevivingWhom.get(player).getLocation()) <= 3.0D && player.isSneaking()) {
                                    if (Count.get(player) > 0.0D) {
                                        playerRevivingWhom.get(player).sendActionBar("§b" + player.getName() + " §ais Reviving you §7- §c" + Count.get(player).doubleValue() / 10.0D + "s");
                                        player.sendActionBar("§aReviving §b" + playerRevivingWhom.get(player).getName() + " §7- §c" + Count.get(player).doubleValue() / 10.0D + "s");
                                        Count.put(player, Count.get(player) - 1.0D);
                                    } else {
                                        Bukkit.broadcastMessage("§b" + player.getName() + " §eRevived §b" + playerRevivingWhom.get(player).getName());
                                        isDown.replace(playerRevivingWhom.get(player), Boolean.FALSE);
                                        playerRevivingWhom.get(player).setHealth(playerRevivingWhom.get(player).getMaxHealth() / 2.0D);
                                        playerRevivingWhom.get(player).leaveVehicle();
                                        playerRevivingWhom.get(player).teleport(playerRevivingWhom.get(player).getEyeLocation());
                                        playerRevivingWhom.get(player).setInvisible(false);
                                        playerRevivingWhom.get(player).setGameMode(GameMode.ADVENTURE);
                                        playerRevivingWhom.remove(player);
                                        playerRevivingStatus.put(player, Boolean.FALSE);
                                        player.sendActionBar("§aRevived");
                                        Count.put(player, finalCount);
                                    }
                                } else {
                                    playerRevivingWhom.get(player).sendActionBar(" ");
                                    player.sendActionBar(" ");
                                    playerRevivingWhom.remove(player);
                                    playerRevivingStatus.put(player, Boolean.FALSE);
                                    Count.put(player, finalCount);
                                }
                        } else {
                            playerRevivingWhom.get(player).sendActionBar(" ");
                            player.sendActionBar(" ");
                            Count.put(player, finalCount);
                            playerRevivingWhom.remove(player);
                            playerRevivingStatus.put(player, Boolean.FALSE);
                        }
                    } else {
                        Count.put(player, finalCount);
                        playerRevivingWhom.remove(player);
                        playerRevivingStatus.put(player, Boolean.FALSE);
                    }
                }
            }).runTaskTimer(plugin, 0L, 2L);
        }
    }
}
