package org.nacukat.zombiesnacukatedition.Listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EntityTarget implements Listener {
    @EventHandler
    public void onTargetChanged(EntityTargetEvent e){
        if(e.getTarget() == null||e.getTarget().getType() != EntityType.PLAYER&&e.getTarget().getType() != EntityType.ARMOR_STAND){
            List<LivingEntity> nearest = new ArrayList<>();
            nearest.addAll(e.getEntity().getLocation().getNearbyLivingEntities(70,livingEntity -> livingEntity.getType() == EntityType.PLAYER));
            nearest.sort(Comparator.comparingDouble(player -> player.getLocation().distance(e.getEntity().getLocation())));
            e.setTarget(nearest.get(0));
        }
    }
}
