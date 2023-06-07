package org.nacukat.zombiesnacukatedition.comands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

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
                int countDown = 59;
                double set = Integer.parseInt(strings[1]) * 10;
                @Override
                public void run() {
                    if(!strings[0].equals("seconds"))cancel();
                    if(countDown > 10){
                        player.sendTitle(String.valueOf(countDown /10),null,0,3,0);

                        countDown--;
                    }else {
                        switch (strings[0]){
                            case "seconds":
                                if(set >0){
                                    set--;
                                    player.sendActionBar(Component.text(set /10));
                                    isCounting.put(player.getUniqueId(),true);
                                }else {
                                    player.sendActionBar(Component.text("§bEnded!"));
                                    isCounting.put(player.getUniqueId(),false);
                                    String str = "スロットを保持していた時間: ";
                                    for (double time : slotHolding.get(player.getUniqueId())){
                                        time /=1000;
                                        str = str+time+"s - ";
                                    }
                                    player.sendMessage(str);
                                    slotHolding.remove(player.getUniqueId());
                                    cancel();
                                }
                                break;

                        }
                    }
                }
            }.runTaskTimer(plugin,0,2);

        }
        return false;
    }
}
