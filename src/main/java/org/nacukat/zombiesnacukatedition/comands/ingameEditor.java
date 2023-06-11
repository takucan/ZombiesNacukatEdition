package org.nacukat.zombiesnacukatedition.comands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class ingameEditor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 0)return false;
        Player player = (Player)commandSender;
        Block block = player.getTargetBlock(null,3);
        String filePath = plugin.getDataFolder()+"/Config.json";
        // ObjectMapperを作成
        ObjectMapper mapper = new ObjectMapper();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode root = (ObjectNode) node;
        ObjectNode firstPoint = objectMapper.createObjectNode();
        ArrayNode editWindowLocs = objectMapper.createArrayNode();
        ArrayNode editWindowArray = null;
        if(!strings[1].equals("new")) editWindowArray = (ArrayNode) onode.path("Maps").path(currentMap).path("Windows").path(Integer.parseInt(strings[2])).path("window");

        switch (strings[0]){
            case "window":
                switch (strings[1]){
                    case "new":
                        ObjectNode newWindow = objectMapper.createObjectNode();
                        ArrayNode spawnPoint = objectMapper.createArrayNode();
                        spawnPoint.add(0);
                        spawnPoint.add(0);
                        spawnPoint.add(0);

                        ArrayNode windowPos = objectMapper.createArrayNode();

                        ArrayNode windowPoss = objectMapper.createArrayNode();
                        ArrayNode windowPoss1 = objectMapper.createArrayNode();
                        ArrayNode windowPoss2 = objectMapper.createArrayNode();
                        ArrayNode windowPoss3 = objectMapper.createArrayNode();
                        windowPoss.add(0);
                        windowPoss.add(0);
                        windowPoss.add(0);

                        windowPoss1.add(0);
                        windowPoss1.add(0);
                        windowPoss1.add(0);

                        windowPoss2.add(0);
                        windowPoss2.add(0);
                        windowPoss2.add(0);

                        windowPoss3.add(0);
                        windowPoss3.add(0);
                        windowPoss3.add(0);

                        windowPos.add(windowPoss);
                        windowPos.add(windowPoss1);
                        windowPos.add(windowPoss2);
                        windowPos.add(windowPoss3);

                        newWindow.put("Door",strings[2]);
                        newWindow.set("spawnPoint",spawnPoint);
                        newWindow.set("window",windowPos);
                        ArrayNode addArray = (ArrayNode) onode.path("Maps").path(currentMap).path("Windows");

                        addArray.add(newWindow);

                        int index = 0;
                        for (int i = 0;i<addArray.size();i++){
                            if(addArray.get(i).equals(newWindow)){
                                index = i;
                                player.sendMessage(Component.text(index));
                                break;
                            }
                        }
                        break;
                    case "first":
                        editWindowLocs.add(block.getX());
                        editWindowLocs.add(block.getY());
                        editWindowLocs.add(block.getZ());

                        editWindowArray.set(0,editWindowLocs);

                        break;
                    case "second":
                        editWindowLocs.add(block.getX());
                        editWindowLocs.add(block.getY());
                        editWindowLocs.add(block.getZ());

                        editWindowArray.set(1,editWindowLocs);

                        break;
                    case "break":
                        editWindowLocs.add((double)Math.round(player.getEyeLocation().getX()*10)/10);
                        editWindowLocs.add((double)Math.round(player.getEyeLocation().getY()*10)/10);
                        editWindowLocs.add((double)Math.round(player.getEyeLocation().getZ()*10)/10);

                        editWindowArray.set(2,editWindowLocs);

                        break;
                    case "rep":
                        editWindowLocs.add((double)Math.round(player.getLocation().getX()*10)/10);
                        editWindowLocs.add((double)Math.round(player.getLocation().getY()*10)/10);
                        editWindowLocs.add((double)Math.round(player.getLocation().getZ()*10)/10);

                        editWindowArray.set(3,editWindowLocs);

                        break;
                    case"spawn":
                        ObjectNode spawnPointItem = objectMapper.createObjectNode();
                        ArrayNode editSpawnPoint = objectMapper.createArrayNode();

                        editSpawnPoint.add((double)Math.round(player.getLocation().getX()*10)/10);
                        editSpawnPoint.add((double)Math.round(player.getLocation().getY()*10)/10);
                        editSpawnPoint.add((double)Math.round(player.getLocation().getZ()*10)/10);

                        ObjectNode editSpawnPointArray = (ObjectNode) onode.path("Maps").path(currentMap).path("Windows").path(Integer.parseInt(strings[2]));
                        editSpawnPointArray.set("spawnPoint",editSpawnPoint);


                }
                try {
                    mapper.writeValue(new File(filePath), onode);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }

        return false;
    }
}
