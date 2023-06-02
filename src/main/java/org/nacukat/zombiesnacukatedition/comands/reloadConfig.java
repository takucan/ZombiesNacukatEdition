package org.nacukat.zombiesnacukatedition.comands;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class reloadConfig implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        ObjectMapper mapper = new ObjectMapper();
        File json = new File(plugin.getDataFolder()+"/Config.json");
        try {
            node = mapper.readTree(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
