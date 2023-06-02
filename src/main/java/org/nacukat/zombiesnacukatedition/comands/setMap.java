package org.nacukat.zombiesnacukatedition.comands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.currentMap;
import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.node;

public class setMap implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length==0)return false;
        if (node.get("Maps").get(strings[0])!= null){
            currentMap = node.get("Maps").get(strings[0]).toString();
        }
        return false;
    }
}
