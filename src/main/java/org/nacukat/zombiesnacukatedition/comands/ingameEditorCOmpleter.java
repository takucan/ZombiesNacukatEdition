package org.nacukat.zombiesnacukatedition.comands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ingameEditorCOmpleter implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> comp = new ArrayList<>();
        switch (strings.length){
            case 1:
                comp.add("window");
                comp.add("door");
                comp.add("shop");
                break;
            case 2:
                switch (strings[0]){
                    case "window":
                        comp.add("new");
                        comp.add("first");
                        comp.add("second");
                        comp.add("break");
                        comp.add("rep");
                        comp.add("spawn");
                }
        }
        return comp;
    }
}
