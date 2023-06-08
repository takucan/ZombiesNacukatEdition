package org.nacukat.zombiesnacukatedition.comands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.nacukat.zombiesnacukatedition.Game.showResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class startCounting implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof  Player))return false;
        Player player = ((Player) commandSender).getPlayer();
        if(strings.length == 2){
            try {
                ;Integer.parseInt(strings[1]);
            } catch (NumberFormatException e) {
                player.sendMessage(Component.text("数字を入力してくさだい"));
                return false;
            }
            new BukkitRunnable(){
                int countDown = 99;
                double set = Integer.parseInt(strings[1]) * 20;
                boolean Result  =false;
                List<Long> holding = new ArrayList<>(Arrays.asList(0L,0L,0L,0L));
                @Override
                public void run() {
                    if(!strings[0].equals("seconds"))cancel();
                    if(countDown > 20){
                        player.sendTitle(String.valueOf(countDown /20),null,0,3,0);

                        countDown--;
                    }else {
                        switch (strings[0]){
                            case "seconds":
                                if(set >0){
                                    set--;
                                    player.sendActionBar(Component.text(String.format("%.2f",set /20)));
                                    isCounting.put(player.getUniqueId(),true);
                                    if(player.getInventory().getHeldItemSlot() < 5&&player.getInventory().getHeldItemSlot()>0){
                                        holding.set(player.getInventory().getHeldItemSlot()-1, holding.get(player.getInventory().getHeldItemSlot()-1)+1L);
                                        slotHolding.put(player.getUniqueId(),holding);
                                    }
                                }else {
                                    Result = true;
                                }
                            default:
                                if(Result) {
                                    new showResult().show(player);
                                    cancel();
                                }


                        }
                    }
                }
            }.runTaskTimer(plugin,0,1);

        }
        return false;
    }
}
