package org.nacukat.zombiesnacukatedition.Doors;

import com.fasterxml.jackson.databind.JsonNode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class openingDoor implements Listener {
    @EventHandler
    public void pressDoorButton(PlayerInteractEvent e){
        Action action = e.getAction();
        Player player = e.getPlayer();

        if(action.equals(Action.RIGHT_CLICK_BLOCK)&&e.getClickedBlock().getType().equals(Material.STONE_BUTTON)&&currentMap != null){
            for (JsonNode door : node.get("Maps").get(currentMap).get("Doors")){
                JsonNode position = door.get("position");
                if(e.getClickedBlock().getLocation().equals(new Location(player.getWorld(),door.get("button").get(0).asDouble(),door.get("button").get(1).asDouble(),door.get("button").get(2).asDouble()))){
                    String command = "fill "+(int)position.get(0).asDouble()+" "+(int)position.get(1).asDouble()+" "+(int)position.get(2).asDouble()+" "+(int)position.get(0).asDouble()+" "+(int)(position.get(1).asDouble()+3)+" "+(int)(position.get(2).asDouble()-2)+" air";
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(),command);
                }
            }
        }
    }
}
