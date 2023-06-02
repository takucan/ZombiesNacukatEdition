package org.nacukat.zombiesnacukatedition.comands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.showParticle;

public class toggleParticle implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!showParticle.get(commandSender)){
            showParticle.put((Player) commandSender,true);
        }else {
            showParticle.put((Player) commandSender,false);
        }
        return false;
    }
}
