package org.nacukat.zombiesnacukatedition.comands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class startCountingCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> Completes = new ArrayList<>();
        if(strings.length == 1){
            Completes.add("seconds");
            Completes.add("damages");
            Completes.add("kill-enemy");
            Completes.add("total-shoots");
            Completes.add("shoots-guns");
            Completes.add("total-clicks");
            Completes.add("clicks-guns");
        }

        return Completes;
    }
}
