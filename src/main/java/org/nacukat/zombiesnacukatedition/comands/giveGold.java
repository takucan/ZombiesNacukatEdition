package org.nacukat.zombiesnacukatedition.comands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.GoldDigger;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.Gold;

public class giveGold implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if(!strings[0].isEmpty()&&!strings[1].isEmpty()){
            if(strings[0].equals("set")){
                Gold.put(player.getUniqueId(), Integer.valueOf(strings[1]));
            }
            if(strings[0].equals("add")){
                Gold.put(player.getUniqueId(), Gold.get(player.getUniqueId())+Integer.parseInt(strings[1]));
            }
        }
        
        return false;
    }
}
