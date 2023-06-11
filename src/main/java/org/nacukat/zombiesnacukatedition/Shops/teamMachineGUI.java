package org.nacukat.zombiesnacukatedition.Shops;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Collection;
import java.util.Map;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class teamMachineGUI implements Listener {
    @EventHandler
    public void onClickTeamMachine(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        if(e.getView().getTitle().equals("Team Machine")){
            e.setCancelled(true);
            switch (e.getCurrentItem().getType()){
                case ARROW:
                    if(Gold.getOrDefault(player.getUniqueId(),0) >= 1000){
                        Bukkit.broadcastMessage(ChatColor.AQUA+player.getName()+" §7activated §a§lAmmo Supply§r §7from §aTeam Machine§7!");
                        for (Player p : Bukkit.getOnlinePlayers()){
                            p.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.5F);
                        }
                        totalBullets.clear();
                        isReloading.clear();
                        magazines.clear();
                        Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())-1000);
                        e.getView().close();
                    }else {
                        player.sendMessage("§cゴールドが足りません");
                    }
                    break;
                case DRAGON_EGG:
                    if(Gold.getOrDefault(player.getUniqueId(),0) >= 3000){
                        Bukkit.broadcastMessage(ChatColor.AQUA+player.getName()+" §7activated §a§lDragon Wrath§r §7from §aTeam Machine§7!");
                        for (Player p : Bukkit.getOnlinePlayers()){
                            p.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.5F);
                        }
                        Collection<LivingEntity> nearPlayer = player.getLocation().getNearbyLivingEntities(15, entity -> (entity != null && !(entity instanceof Player) && !(entity instanceof ArmorStand) && entity.getHealth() != 0.0D));

                        LightningStrike lightningStrike = player.getWorld().strikeLightningEffect(nearPlayer.stream().toList().get(0).getLocation());
                        for(LivingEntity entity :nearPlayer){
                            entity.setKiller(player);
                            entity.damage(80);
                        }
                        Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())-3000);
                        e.getView().close();
                    }else {
                        player.sendMessage("§cゴールドが足りません");
                    }
                    break;
                case GOLDEN_APPLE:
                    if(Gold.getOrDefault(player.getUniqueId(),0) >= 3000){
                        Bukkit.broadcastMessage(ChatColor.AQUA+player.getName()+" §7activated §a§lFull Revive§r §7from §aTeam Machine§7!");
                        for (Player p : Bukkit.getOnlinePlayers()){
                            p.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.5F);
                        }
                        for (Map.Entry<Player,Boolean> a:isDown.entrySet()){
                            if(isDown.get(a.getKey())){
                                a.getKey().teleport(a.getKey().getEyeLocation());
                            }
                        }
                        isDown.clear();
                    }
                    break;
            }
        }
    }
}
