package org.nacukat.zombiesnacukatedition.Game.Windows;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import javax.swing.text.html.parser.Entity;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.plugin;

public class setSpawnPoints {
    public void setStand(Location location,boolean bool,String room){
        for(LivingEntity livingEntity:  location.getNearbyLivingEntities(1, livingEntity -> livingEntity instanceof ArmorStand)){
            livingEntity.setHealth(0);
        }

        ArmorStand armorStand = (ArmorStand) Bukkit.getWorld("world").spawnEntity(location, EntityType.ARMOR_STAND);
        if(bool){
            armorStand.setMetadata("Door",new FixedMetadataValue(plugin,room));
            armorStand.setMetadata("spawn",new FixedMetadataValue(plugin,true));
        }else {
            armorStand.setMetadata("spawn",new FixedMetadataValue(plugin,false));
        }
        armorStand.setVisible(false);
    }
}
