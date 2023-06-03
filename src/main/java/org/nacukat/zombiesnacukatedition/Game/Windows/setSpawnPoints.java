package org.nacukat.zombiesnacukatedition.Game.Windows;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import javax.swing.text.html.parser.Entity;

public class setSpawnPoints {
    public void setStand(Location location){
        for(LivingEntity livingEntity:  location.getNearbyLivingEntities(1, livingEntity -> livingEntity instanceof ArmorStand)){
            livingEntity.setHealth(0);
        }

        ArmorStand armorStand = (ArmorStand) Bukkit.getWorld("world").spawnEntity(location, EntityType.ARMOR_STAND);
    }
}
