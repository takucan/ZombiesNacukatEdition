package org.nacukat.zombiesnacukatedition.Game;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Method;
import java.util.UUID;

public class CustomHead {
    public ItemStack get(String base64String){
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta)head.getItemMeta();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        gameProfile.getProperties().put("textures", new Property("textures", base64String));
        try {
            Method method = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            method.setAccessible(true);
            method.invoke(skullMeta, gameProfile);
        } catch (IllegalAccessException|NoSuchMethodException|java.lang.reflect.InvocationTargetException e) {
            e.printStackTrace();
        }
        head.setItemMeta(skullMeta);
        return head;
    }
}
