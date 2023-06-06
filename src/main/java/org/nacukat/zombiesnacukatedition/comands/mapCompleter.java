package org.nacukat.zombiesnacukatedition.comands;

import com.fasterxml.jackson.databind.JsonNode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.node;

public class mapCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            JsonNode mapsNode = node.get("Maps");
            List<String> childNames = new ArrayList<>();
            Iterator<String> fieldNames = mapsNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                childNames.add(fieldName);
            }
            return childNames;
        }
        return null;
    }
}
