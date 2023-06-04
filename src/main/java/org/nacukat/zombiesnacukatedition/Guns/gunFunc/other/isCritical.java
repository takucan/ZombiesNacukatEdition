package org.nacukat.zombiesnacukatedition.Guns.gunFunc.other;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.HasFB;
import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.HeadSizes;

public class isCritical {
    public boolean critical(Player player, LivingEntity hitEntity, Location hitLocation, Location headLocation) {
 /*       double boundingBoxSizeX = ((Double[]) HeadSizes.get(hitEntity.getType()))[0];
        double boundingBoxSizeY = ((Double[]) HeadSizes.get(hitEntity.getType()))[1];
        double boundingBoxSizeZ = ((Double[]) HeadSizes.get(hitEntity.getType()))[2];
        double fixY = ((Double[]) HeadSizes.get(hitEntity.getType()))[3];
        boundingBoxSizeX *= 0.7D;
        boundingBoxSizeZ *= 0.7D;
        boundingBoxSizeY += 10.0D;
        double minX = headLocation.getX() - boundingBoxSizeX;
        double minY = headLocation.getY() - fixY;
        double minZ = headLocation.getZ() - boundingBoxSizeZ;
        double maxX = headLocation.getX() + boundingBoxSizeX;
        double maxY = headLocation.getY() + boundingBoxSizeY;
        double maxZ = headLocation.getZ() + boundingBoxSizeZ;

        if (hitLocation.getX() >= minX && hitLocation.getX() <= maxX && hitLocation
                .getY() >= minY && hitLocation.getY() <= maxY && hitLocation
                .getZ() >= minZ && hitLocation.getZ() <= maxZ) {
            bool = true;
        } else */boolean bool = false;if (player.getEyeLocation().getDirection().getY() > 0.0D) {
            Random random = new Random();
            int rand = random.nextInt(100);
            if (rand <= 90)
                bool = true;
        }
        HasFB.putIfAbsent(player.getName(), Boolean.FALSE);
        if (HasFB.get(player.getName())) {
            PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 60, 1, false, false);
            hitEntity.addPotionEffect(effect);
        }
        return bool;
    }
}
