package org.nacukat.zombiesnacukatedition.Game;

import com.fasterxml.jackson.databind.JsonNode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class Zombies {
    public void spawnZombie(int num){
        List<LivingEntity> nearbyPoints = new ArrayList<>();
        for (Player player : Bukkit.getWorld("world").getPlayers()){
            nearbyPoints.addAll(player.getLocation().getNearbyLivingEntities(50,3,livingEntity -> livingEntity instanceof ArmorStand&&livingEntity.getMetadata("spawn").get(0).asBoolean()));
        }

        JsonNode info = node.get("Zombies").get(num);
        Random random = new Random();

        Location location = nearbyPoints.get(random.nextInt(nearbyPoints.size())).getLocation();
        Zombie zombie = (Zombie) Bukkit.getWorld("world").spawnEntity(location,EntityType.valueOf(info.get("type").asText()),false);

        zombie.setMaxHealth(info.get("Health").asDouble());
        zombie.setHealth(info.get("Health").asDouble());
        zombie.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(info.get("Attack").asDouble());
        zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(info.get("Speed").asDouble());

        if(!info.get("Head").isBoolean()){
            zombie.getEquipment().setHelmet(new ItemStack(Material.valueOf(info.get("Head").asText())));
        }
        if(!info.get("Chest").isBoolean()){
            zombie.getEquipment().setChestplate(new ItemStack(Material.valueOf(info.get("Chest").asText())));
        }
        if(!info.get("Leg").isBoolean()){
            zombie.getEquipment().setLeggings(new ItemStack(Material.valueOf(info.get("Leg").asText())));
        }
        if(!info.get("Boots").isBoolean()){
            zombie.getEquipment().setBoots(new ItemStack(Material.valueOf(info.get("Boots").asText())));
        }
        if(!info.get("Hand").isBoolean()){
            zombie.getEquipment().setItemInMainHand(new ItemStack(Material.valueOf(info.get("Hand").asText())));
        }
        if(info.get("Boss").asBoolean()){
            zombie.setMetadata("isBoss", new FixedMetadataValue(plugin,true));
        }else {
            zombie.setMetadata("isBoss", new FixedMetadataValue(plugin,true));
        }
        zombie.setShouldBurnInDay(false);


        for (JsonNode window : node.get("Maps").get(currentMap).get("Windows")){
            if(Objects.equals(window.get("spawnPoint").toString(), "["+location.getX()+","+(int)location.getY()+","+location.getZ()+"]")){

                Location targetLoc = new Location(location.getWorld(), window.get("window").get(3).get(0).asDouble(),window.get("window").get(3).get(1).asDouble(),window.get("window").get(3).get(2).asDouble());
                zombie.setTarget(targetLoc.getNearbyLivingEntities(0.1,livingEntity -> livingEntity instanceof ArmorStand).stream().toList().get(0));

                new BukkitRunnable(){

                    @Override
                    public void run() {
                        if(zombie.getLocation().distance(targetLoc) <1){
                            Player nearestPlayer = null;
                            double nearestDistance = Double.MAX_VALUE;
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                isDown.putIfAbsent(player,false);
                                if(!isDown.get(player)){
                                    double distance = player.getLocation().distance(zombie.getLocation());
                                    if (distance < nearestDistance) {
                                        nearestPlayer = player;
                                        nearestDistance = distance;
                                    }
                                }
                            }
                            zombie.setTarget(nearestPlayer);
                            cancel();
                        }
                        if(zombie.isDead()){
                            cancel();
                        }
                    }
                }.runTaskTimer(plugin,40,2);
                break;
            }
        }
//        zombie.setTarget(zombie.getLocation().getNearbyLivingEntities(3,livingEntity -> livingEntity instanceof ArmorStand).stream().toList().get(0));
    }
}
