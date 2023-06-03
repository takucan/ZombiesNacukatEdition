package org.nacukat.zombiesnacukatedition.Game.Doors;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class setDoors {
    public void setDoor(Location start, Location end, Location target){
        World world = start.getWorld();

        int minX = Math.min(start.getBlockX(), end.getBlockX());
        int minY = Math.min(start.getBlockY(), end.getBlockY());
        int minZ = Math.min(start.getBlockZ(), end.getBlockZ());
        int maxX = Math.max(start.getBlockX(), end.getBlockX());
        int maxY = Math.max(start.getBlockY(), end.getBlockY());
        int maxZ = Math.max(start.getBlockZ(), end.getBlockZ());

        Vector offset = new Vector(target.getBlockX() - minX, target.getBlockY() - minY, target.getBlockZ() - minZ);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Location sourceLocation = new Location(world, x, y, z);
                    Location targetLocation = sourceLocation.clone().add(offset);

                    Block sourceBlock = sourceLocation.getBlock();
                    Block targetBlock = targetLocation.getBlock();

                    targetBlock.setType(sourceBlock.getType());
                    targetBlock.setBlockData(sourceBlock.getBlockData());
                }
            }
        }
    }


}
