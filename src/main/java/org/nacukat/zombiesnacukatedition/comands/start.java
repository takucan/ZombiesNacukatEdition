package org.nacukat.zombiesnacukatedition.comands;

import com.fasterxml.jackson.databind.JsonNode;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.nacukat.zombiesnacukatedition.Game.StartGame;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class start implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {



        if(currentMap != null){
            new StartGame().start();
        }
        return false;
    }
}
