package org.nacukat.zombiesnacukatedition.Game;

import com.fasterxml.jackson.databind.JsonNode;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.bukkit.entity.EntityType.ZOMBIE;
import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class Zombies {
    public void spawnZombie(int num){
        List<LivingEntity> nearbyPoints = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()){
            nearbyPoints.addAll(player.getEyeLocation().getNearbyLivingEntities(50,2,livingEntity -> livingEntity instanceof ArmorStand&&livingEntity.getMetadata("spawn").get(0).asBoolean()));

        }
        nearbyPoints.removeIf(livingEntity -> !openedDoors.getOrDefault(livingEntity.getMetadata("Door").get(0).asString(),false));
        JsonNode info = node.get("Zombies").get(num);
        Random random = new Random();

        Location location = nearbyPoints.get(random.nextInt(nearbyPoints.size())).getLocation();
        LivingEntity zombie = null;
        switch(info.get("type").asText()){
            case "GUARDIAN":
//                GameProfile newSkinProfile = new GameProfile(UUID.randomUUID(), null);
//                newSkinProfile.getProperties().put("textures", new Property("textures", Base64Coder.encodeString("{textures:{SKIN:{url:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBiZjM0YTcxZTc3MTViNmJhNTJkNWRkMWJhZTVjYjg1Zjc3M2RjOWIwZDQ1N2I0YmZjNWY5ZGQzY2M3Yzk0In19fQ==\"}}}")));

                Zombie guardian = (Zombie) Bukkit.getWorld("world").spawnEntity(location,EntityType.ZOMBIE,false);
                guardian.setMetadata("TYPE",new FixedMetadataValue(plugin,"GUARDIAN"));
                guardian.getEquipment().setHelmet(new CustomHead().get("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBiZjM0YTcxZTc3MTViNmJhNTJkNWRkMWJhZTVjYjg1Zjc3M2RjOWIwZDQ1N2I0YmZjNWY5ZGQzY2M3Yzk0In19fQ=="));
                ItemStack gc = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack gl = new ItemStack(Material.LEATHER_LEGGINGS);
                ItemStack gb   = new ItemStack(Material.LEATHER_BOOTS);
                LeatherArmorMeta gcd = (LeatherArmorMeta) gc.getItemMeta();
                LeatherArmorMeta gld = (LeatherArmorMeta) gl.getItemMeta();
                LeatherArmorMeta gbd = (LeatherArmorMeta) gb.getItemMeta();
                gcd.setColor(Color.fromRGB(0,160,233));
                gld.setColor(Color.fromRGB(0,160,233));
                gbd.setColor(Color.fromRGB(0,160,233));
                gc.setItemMeta(gcd);
                gl.setItemMeta(gld);
                gb.setItemMeta(gbd);
                guardian.getEquipment().setChestplate(gc);
                guardian.getEquipment().setLeggings(gl);
                guardian.getEquipment().setBoots(gb);
                guardian.setMaxHealth(100);
                guardian.setHealth(100);
                guardian.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20);
                guardian.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3);
                zombie = guardian;
                new BukkitRunnable(){

                    @Override
                    public void run() {
                        if(guardian.isDead()) cancel();
                        if(guardian.getTarget() == null)return;
                        Location guardianLoc = guardian.getEyeLocation();
                        Location playerLoc = guardian.getTarget().getEyeLocation();
                        new BukkitRunnable(){

                            @Override
                            public void run() {
                                RayTraceResult raytrace = Bukkit.getWorld("world").rayTrace(guardianLoc,playerLoc.subtract(guardianLoc).toVector(),20, FluidCollisionMode.NEVER,true,0.2, entity -> entity.getType() == EntityType.PLAYER);
                                if(raytrace!=null&&raytrace.getHitEntity() != null){
                                    ((LivingEntity)raytrace.getHitEntity()).damage(15,guardian);
                                    for(Player player : Bukkit.getOnlinePlayers()){
                                        player.playSound(guardianLoc, Sound.ENTITY_LIGHTNING_BOLT_THUNDER,1,2);
                                    }
                                }
                            }
                        }.runTaskLater(plugin,new Random().nextLong(30));

                    }
                }.runTaskTimer(plugin,100,40);
                break;
            case "ENDER":
                Zombie ender = (Zombie) Bukkit.getWorld("world").spawnEntity(location,EntityType.ZOMBIE,false);
                ender.setMetadata("TYPE",new FixedMetadataValue(plugin,"ENDER"));
                ender.getEquipment().setHelmet(new CustomHead().get("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzA5ZjFkZTYxMzVmNGJlYTc4MWM1YThlMGQ2MTA5NWY4MzNlZTI2ODVkODE1NGVjZWE4MTRlZTZkMzI4YTVjNiJ9fX0="));
                ItemStack ec = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack el = new ItemStack(Material.LEATHER_LEGGINGS);
                ItemStack eb   = new ItemStack(Material.LEATHER_BOOTS);
                LeatherArmorMeta ecd = (LeatherArmorMeta) ec.getItemMeta();
                LeatherArmorMeta eld = (LeatherArmorMeta) el.getItemMeta();
                LeatherArmorMeta ebd = (LeatherArmorMeta) eb.getItemMeta();
                ecd.setColor(Color.fromRGB(31,47,84));
                eld.setColor(Color.fromRGB(31,47,84));
                ebd.setColor(Color.fromRGB(31,47,84));
                ec.setItemMeta(ecd);
                el.setItemMeta(eld);
                eb.setItemMeta(ebd);
                ender.getEquipment().setChestplate(ec);
                ender.getEquipment().setLeggings(el);
                ender.getEquipment().setBoots(eb);

                ender.setMaxHealth(100);
                ender.setHealth(100);
                ender.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(20);
                ender.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3);
                zombie = ender;
                break;
            default:

                zombie = (LivingEntity) Bukkit.getWorld("world").spawnEntity(location,EntityType.valueOf(info.get("type").asText()),false);
                zombie.setMetadata("TYPE",new FixedMetadataValue(plugin,"NORMAL"));
                zombie.setMaxHealth(info.get("Health").asDouble());
                zombie.setHealth(info.get("Health").asDouble());
                zombie.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(info.get("Attack").asDouble());
                zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(info.get("Speed").asDouble());

                if(!info.get("Head").isBoolean()){
                    zombie.getEquipment().setHelmet(new ItemStack(Material.valueOf(info.get("Head").asText())));
                }
                if(!info.get("Chest").isBoolean()){
                    zombie.getEquipment().setChestplate(new ItemStack(Material.valueOf(info.get("Chest").asText())));
                }
                if(!info.get("Leg").isBoolean()){
                    zombie.getEquipment().setLeggings(new ItemStack(Material.valueOf(info.get("Leg").asText())));
                }
                if(!info.get("Boots").isBoolean()){
                    zombie.getEquipment().setBoots(new ItemStack(Material.valueOf(info.get("Boots").asText())));
                }
                if(!info.get("Hand").isBoolean()){
                    zombie.getEquipment().setItemInMainHand(new ItemStack(Material.valueOf(info.get("Hand").asText())));
                }
                if(info.get("Boss").asBoolean()){
                    zombie.setMetadata("isBoss", new FixedMetadataValue(plugin,true));
                }else {
                    zombie.setMetadata("isBoss", new FixedMetadataValue(plugin,true));
                }
                if(zombie.getType().equals(ZOMBIE)) ((Zombie)zombie).setShouldBurnInDay(false);
        }



        for (JsonNode window : node.get("Maps").get(currentMap).get("Windows")){
            if(Objects.equals(window.get("spawnPoint").toString(), "["+location.getX()+","+(int)location.getY()+","+location.getZ()+"]")){

                Location targetLoc = new Location(location.getWorld(), window.get("window").get(3).get(0).asDouble(),window.get("window").get(3).get(1).asDouble(),window.get("window").get(3).get(2).asDouble());
                ((Mob)zombie).setTarget(targetLoc.getNearbyLivingEntities(0.1,livingEntity -> livingEntity instanceof ArmorStand).stream().toList().get(0));

                LivingEntity finalZombie = zombie;
                new BukkitRunnable(){

                    @Override
                    public void run() {
                        if(finalZombie.getLocation().distance(targetLoc) <1){
                            Player nearestPlayer = null;
                            double nearestDistance = Double.MAX_VALUE;
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                isDown.putIfAbsent(player,false);
                                if(!isDown.get(player)){
                                    double distance = player.getLocation().distance(finalZombie.getLocation());
                                    if (distance < nearestDistance) {
                                        nearestPlayer = player;
                                        nearestDistance = distance;
                                    }
                                }
                            }
                            ((Mob)finalZombie).setTarget(nearestPlayer);
                            cancel();
                        }
                        if(finalZombie.isDead()){
                            cancel();
                        }
                    }
                }.runTaskTimer(plugin,40,2);
                break;
            }
        }
//        zombie.setTarget(zombie.getLocation().getNearbyLivingEntities(3,livingEntity -> livingEntity instanceof ArmorStand).stream().toList().get(0));
    }
}
