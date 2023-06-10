package org.nacukat.zombiesnacukatedition.Shops;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.totalBullets;

public class teamMachineGUI implements Listener {
    @EventHandler
    public void onClickTeamMachine(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        if(e.getView().getTitle().equals("Team Machine")){
            e.setCancelled(true);
            switch (e.getSlot()){
                case 10:
                    for (Map.Entry<Integer,Integer> m : totalBullets.entrySet()){
                        totalBullets.clear();
                    }
            }
            e.getView().close();
        }
    }
}
