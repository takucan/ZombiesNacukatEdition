package org.nacukat.zombiesnacukatedition.comands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.isDown;

public class revive implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    isDown.put((Player)sender, Boolean.valueOf(false));
    return false;
  }
}
