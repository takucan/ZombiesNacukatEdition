package org.nacukat.zombiesnacukatedition.Game;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class showResult {
    public void show(Player player){
        {
            player.sendActionBar(Component.text("§bEnded!"));
            isCounting.put(player.getUniqueId(),false);
            player.sendMessage(Component.text("§a-----------------------"));
            String str = "§e§lスロットを保持していた時間§r§7>> §a";

            {
                int i = 0;
                for (double time : slotHolding.get(player.getUniqueId())) {
                    time /= 20;
                    if(i == 3){
                        str = str + time + "s";
                    }else {
                        str = str + time + "s §7- §a";
                    }
                    i++;
                }
            }
            player.sendMessage(str);
            slotHolding.remove(player.getUniqueId());

            String str1 = "§e§lスロットごとのクリック数§r§7>> §a";
            {
                int i = 0;
                slotClicks.putIfAbsent(player.getUniqueId(),new ArrayList<>(Arrays.asList(0L,0L,0L,0L)));
                for (Long slotclick : slotClicks.get(player.getUniqueId())) {
                    if(i == 3){
                        str1 = str1 + slotclick;
                    }else {
                        str1 = str1 + slotclick + " §7- §a";
                    }
                    i++;
                }
            }
            player.sendMessage(str1);
            slotClicks.remove(player.getUniqueId());

            String str4 = "§e§lスロットごとの撃った回数§r§7>> §a";
            {
                int i = 0;
                slotShoots.putIfAbsent(player.getUniqueId(),new ArrayList<>(Arrays.asList(0L,0L,0L,0L)));
                for (Long slotShoot : slotShoots.get(player.getUniqueId())) {
                    if(i == 3) {
                        str4 = str4 + slotShoot;
                    }else {
                        str4 = str4 + slotShoot + " §7- §a";
                    }
                    i++;
                }
            }
            player.sendMessage(str4);
            slotShoots.remove(player.getUniqueId());


            String str2 = "§e§l銃ごとのクリック数§r§7>> ";
            int i = 0;
            gunClicks.putIfAbsent(player.getUniqueId(),new ArrayList<>(Arrays.asList(0L,0L,0L,0L)));
            for (Long gunclick : gunClicks.get(player.getUniqueId())){
                switch (i){
                    case 0:
                        str2 = str2+"§3ZZ§f: §a"+gunclick;
                        break;
                    case 1:
                        str2 = str2+" §7/ §eRR§f: §a"+gunclick;
                        break;
                    case 2:
                        str2 = str2+" §7/ §6GD§f: §a"+gunclick;
                        break;
                    case 3:
                        str2 = str2+" §7/ §fD§7B§8S§f: §a"+gunclick;
                        break;
                    case 4:
                        str2 = str2+" §7/ §fS§7G§f: §a"+gunclick;
                        break;
                }
                i++;
            }
            player.sendMessage(str2);
            gunClicks.remove(player.getUniqueId());



            String str3 = "§e§l銃ごとの撃った回数§r§7>> §a";
            int i1 = 0;
            gunShoots.putIfAbsent(player.getUniqueId(),new ArrayList<>(Arrays.asList(0L,0L,0L,0L)));
            for (Long gunshoot : gunShoots.get(player.getUniqueId())){
                switch (i1){
                    case 0:
                        str3 = str3+"§3ZZ§f: §a"+gunshoot;
                        break;
                    case 1:
                        str3 = str3+" §7/ §eRR§f: §a"+gunshoot;
                        break;
                    case 2:
                        str3 = str3+" §7/ §6GD§f: §a"+gunshoot;
                        break;
                    case 3:
                        str3 = str3+" §7/ §fD§7B§8S§f: §a"+gunshoot;
                        break;
                    case 4:
                        str3 = str3+" §7/ §fS§7G§f: §a"+gunshoot;
                        break;
                }
                i1++;
            }
            gunShoots.remove(player.getUniqueId());
            player.sendMessage(str3);
            player.sendMessage(Component.text("§e§l与ダメージ§r§7>> §a"+damage.get(player.getUniqueId())));
            player.sendMessage(Component.text("     §7(Slot: 2, 3, 4, 5)"));
            player.sendMessage(Component.text("§a-----------------------"));
        }
    }
}
