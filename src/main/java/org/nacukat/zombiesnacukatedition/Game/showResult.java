package org.nacukat.zombiesnacukatedition.Game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class showResult {
    public void show(Player player){
        {
            player.sendActionBar(Component.text("§bEnded!"));
            isCounting.put(player.getUniqueId(),false);
            player.sendMessage(Component.text("§a-----------------------"));
            StringBuilder str = new StringBuilder("§e§lスロットを保持していた時間§r§7>> §a");

            {
                int i = 0;
                for (double time : slotHolding.get(player.getUniqueId())) {
                    time /= 20;
                    if(i == 3){
                        str.append(time).append("s");
                    }else {
                        str.append(time).append("s §7- §a");
                    }
                    i++;
                }
            }
            player.sendMessage(str.toString());
            slotHolding.remove(player.getUniqueId());

            StringBuilder str1 = new StringBuilder("§e§lスロットごとのクリック数§r§7>> §a");
            {
                int i = 0;
                slotClicks.putIfAbsent(player.getUniqueId(),new ArrayList<>(Arrays.asList(0L,0L,0L,0L)));
                for (Long slotclick : slotClicks.get(player.getUniqueId())) {
                    if(i == 3){
                        str1.append(slotclick);
                    }else {
                        str1.append(slotclick).append(" §7- §a");
                    }
                    i++;
                }
            }
            player.sendMessage(str1.toString());
            slotClicks.remove(player.getUniqueId());

            StringBuilder str4 = new StringBuilder("§e§lスロットごとの撃った回数§r§7>> §a");
            {
                int i = 0;
                slotShoots.putIfAbsent(player.getUniqueId(),new ArrayList<>(Arrays.asList(0L,0L,0L,0L)));
                for (Long slotShoot : slotShoots.get(player.getUniqueId())) {
                    if(i == 3) {
                        str4.append(slotShoot);
                    }else {
                        str4.append(slotShoot).append(" §7- §a");
                    }
                    i++;
                }
            }
            player.sendMessage(str4.toString());
            slotShoots.remove(player.getUniqueId());


            StringBuilder str2 = new StringBuilder("§e§l銃ごとのクリック数§r§7>> ");
            StringBuilder str21 = new StringBuilder("                  ");
            int i = 0;
            gunClicks.putIfAbsent(player.getUniqueId(),new ArrayList<>(Arrays.asList(0L,0L,0L,0L)));
            for (Long gunclick : gunClicks.get(player.getUniqueId())){
                switch (i) {
                    case 0 -> str2.append("§3ZZ§f: §a").append(gunclick);
                    case 1 -> str2.append(" §7/ §eRR§f: §a").append(gunclick);
                    case 2 -> str2.append(" §7/ §6GD§f: §a").append(gunclick);
                    case 3 -> str21.append(" §7/ §fD§7B§8S§f: §a").append(gunclick);
                    case 4 -> str21.append(" §7/ §fS§7G§f: §a").append(gunclick);
                    case 5 ->str21.append(" §7/ ").append(TextColor.color(201, 127, 71)).append("Pis§f: §a").append(gunclick);
                }
                i++;
            }
            player.sendMessage(str2.toString());
            player.sendMessage(str21.toString());
            gunClicks.remove(player.getUniqueId());



            StringBuilder str3 = new StringBuilder("§e§l銃ごとの撃った回数§r§7>> §a");
            StringBuilder str31 = new StringBuilder("                  ");
            int i1 = 0;
            gunShoots.putIfAbsent(player.getUniqueId(),new ArrayList<>(Arrays.asList(0L,0L,0L,0L)));
            for (Long gunshoot : gunShoots.get(player.getUniqueId())){
                switch (i1) {
                    case 0 -> str3.append("§3ZZ§f: §a").append(gunshoot);
                    case 1 -> str3.append(" §7/ §eRR§f: §a").append(gunshoot);
                    case 2 -> str3.append(" §7/ §6GD§f: §a").append(gunshoot);
                    case 3 -> str31.append(" §7/ §fD§7B§8S§f: §a").append(gunshoot);
                    case 4 -> str31.append(" §7/ §fS§7G§f: §a").append(gunshoot);
                    case 5 ->str31.append(" §7/ ").append(TextColor.color(201, 127, 71)).append("Pis§f: §a").append(gunshoot);
                }
                i1++;
            }
            gunShoots.remove(player.getUniqueId());
            player.sendMessage(str3.toString());
            player.sendMessage(str31.toString());
            player.sendMessage(Component.text("§e§l与ダメージ§r§7>> §a"+damage.get(player.getUniqueId())));
            player.sendMessage(Component.text("     §7(Slot: 2, 3, 4, 5)"));
            player.sendMessage(Component.text("§a-----------------------"));
        }
    }
}
