package org.nacukat.zombiesnacukatedition.Game;

import com.fasterxml.jackson.databind.JsonNode;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.MetadataValueAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class Zombies {
    public void spawnZombie(int num){
        List<LivingEntity> nearbyPoints = new ArrayList<>();
        for (Player player : Bukkit.getWorld("world").getPlayers()){
            nearbyPoints.addAll(player.getLocation().getNearbyLivingEntities(24,livingEntity -> livingEntity instanceof ArmorStand));
        }

        JsonNode info = node.get("Zombies").get(num);
        Random random = new Random();


        Zombie zombie = (Zombie) Bukkit.getWorld("world").spawnEntity(nearbyPoints.get(random.nextInt(nearbyPoints.size())).getLocation(),EntityType.valueOf(info.get("type").asText()),false);

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
    }
}
