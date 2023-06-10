package org.nacukat.zombiesnacukatedition.Guns;

import com.fasterxml.jackson.databind.JsonNode;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.nacukat.zombiesnacukatedition.Guns.gunFunc.*;
import org.nacukat.zombiesnacukatedition.Skill.RightLightningRod;

import java.util.*;

import static org.nacukat.zombiesnacukatedition.ZombiesNacukatEdition.*;

public class invokeGun implements Listener {

    @EventHandler
    public void onClickedArmStand(PlayerInteractAtEntityEvent e){
        Action action = Action.RIGHT_CLICK_AIR;
        Player player = e.getPlayer();
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        if(!e.getRightClicked().hasMetadata("Shop")) {
            clickEvent(player, item, action);
            return;
        }
        if(e.getRightClicked().getMetadata("Shop").get(0).asBoolean()){
            if(Gold.get(e.getPlayer().getUniqueId())>=e.getRightClicked().getMetadata("price").get(0).asInt()){
                Gold.put(player.getUniqueId(),Gold.get(player.getUniqueId())-e.getRightClicked().getMetadata("price").get(0).asInt());
                switch (e.getRightClicked().getMetadata("type").get(0).asString()) {
                    case "gun" -> {
                        if (Material.valueOf(e.getRightClicked().getMetadata("item").get(0).asString()).equals(item.getType())) {
                            totalBullets.put(item.getItemMeta().getCustomModelData(), totalbulletsMaterial.get(item.getType())[Ultimates.get(item.getItemMeta().getCustomModelData())]);
                            isReloading.put(item.getItemMeta().getCustomModelData(), false);
                            player.setExp(1);
                            player.setLevel(totalBullets.get(item.getItemMeta().getCustomModelData()));

//                    totalBullets.put(item.getItemMeta().getCustomModelData(),totalbulletsMaterial.get(Material.valueOf(e.getRightClicked().getMetadata("item").get(0).asString()))[Ultimates.get(item.getItemMeta().getCustomModelData())]);
//                    player.setExp(1);
//                    player.setLevel(totalBullets.get(item.getItemMeta().getCustomModelData()));
                        } else {
                            player.getInventory().addItem(new ItemStack(Material.valueOf(e.getRightClicked().getMetadata("item").get(0).asString())));
                        }
                    }
                    case "perks" -> {

                        switch (e.getRightClicked().getMetadata("perk").get(0).asString()) {
                            case "QF" -> {
                                HasQF.putIfAbsent(player.getName(), Boolean.valueOf(false));
                                if (!HasQF.get(player.getName()).booleanValue()) {
                                    player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1.0F, 1.0F);
                                    player.sendMessage(ChatColor.YELLOW + "You activated " + ChatColor.BLUE + "Quick Fire Perk" + ChatColor.YELLOW + "!");
                                    HasQF.put(player.getName(), Boolean.valueOf(true));
                                    break;
                                }
                                player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 0.1F);
                                player.sendMessage(ChatColor.YELLOW + "You already activated " + ChatColor.BLUE + "Quick Fire Perk" + ChatColor.YELLOW + "!");
                            }
                            case "FB" -> {
                                HasFB.putIfAbsent(player.getName(), Boolean.valueOf(false));
                                if (!HasFB.get(player.getName()).booleanValue()) {
                                    player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1.0F, 1.0F);
                                    player.sendMessage(ChatColor.YELLOW + "You activated " + ChatColor.BLUE + "Frozen Bullet Perk" + ChatColor.YELLOW + "!");
                                    HasFB.put(player.getName(), Boolean.valueOf(true));
                                    break;
                                }
                                player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 0.1F);
                                player.sendMessage(ChatColor.YELLOW + "You already activated " + ChatColor.BLUE + "Frozen Bullet Perk" + ChatColor.YELLOW + "!");
                            }
                            case "EH" -> {
                                EHs.putIfAbsent(player.getName(), 0);
                                if (EHs.get(player.getName()) < 10) {
                                    EHs.put(player.getName(), EHs.get(player.getName()) + 1);
                                    player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1.0F, 1.0F);
                                    String ehValue = "I";
                                    switch (EHs.get(player.getName())) {
                                        case 1:
                                            ehValue = "I";
                                            break;
                                        case 2:
                                            ehValue = "II";
                                            break;
                                        case 3:
                                            ehValue = "III";
                                            break;
                                        case 4:
                                            ehValue = "IV";
                                            break;
                                        case 5:
                                            ehValue = "V";
                                            break;
                                        case 6:
                                            ehValue = "VI";
                                            break;
                                        case 7:
                                            ehValue = "VII";
                                            break;
                                        case 8:
                                            ehValue = "VIII";
                                            break;
                                        case 9:
                                            ehValue = "IX";
                                            break;
                                        case 10:
                                            ehValue = "X";
                                            break;
                                    }
                                    player.sendMessage(ChatColor.YELLOW + "You activated " + ChatColor.BLUE + "Extra Health Perk " + ehValue + ChatColor.YELLOW + "!");
                                    player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                                    PotionEffect potionEffect = new PotionEffect(PotionEffectType.HEALTH_BOOST, -1, EHs.get(player.getName()) - 1, false, false, false);
                                    player.addPotionEffect(potionEffect);

                                    break;
                                }
                                player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 0.1F);
                                player.sendMessage(ChatColor.YELLOW + "You already activated " + ChatColor.BLUE + "Extra Health Perk" + ChatColor.YELLOW + "!");
                            }
                            case "FR" -> {
                                HasFR.putIfAbsent(player.getName(), Boolean.valueOf(false));
                                if (!HasFR.get(player.getName()).booleanValue()) {
                                    player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1.0F, 1.0F);
                                    player.sendMessage(ChatColor.YELLOW + "You activated " + ChatColor.BLUE + "Fast Revive Perk" + ChatColor.YELLOW + "!");
                                    HasFR.put(player.getName(), Boolean.valueOf(true));
                                    break;
                                }
                                player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 0.1F);
                                player.sendMessage(ChatColor.YELLOW + "You already activated " + ChatColor.BLUE + "Fast Revive Perk" + ChatColor.YELLOW + "!");
                            }
                        }

                    }
                    case "tops" -> {
                        ItemStack itemStack1 = new ItemStack(Material.valueOf(e.getRightClicked().getMetadata("met").get(0).asString()));
                        ItemStack itemStack2 = new ItemStack(Material.valueOf(e.getRightClicked().getMetadata("chest").get(0).asString()));
                        ItemMeta itemMeta1 = itemStack1.getItemMeta();
                        ItemMeta itemMeta2 = itemStack1.getItemMeta();
                        itemMeta1.setUnbreakable(true);
                        itemMeta2.setUnbreakable(true);
                        itemStack1.setItemMeta(itemMeta1);
                        itemStack2.setItemMeta(itemMeta2);
                        player.getInventory().setHelmet(itemStack1);
                        player.getInventory().setChestplate(itemStack2);
                    }
                    case "bottoms" -> {
                        ItemStack itemStack3 = new ItemStack(Material.valueOf(e.getRightClicked().getMetadata("leg").get(0).asString()));
                        ItemStack itemStack4 = new ItemStack(Material.valueOf(e.getRightClicked().getMetadata("boots").get(0).asString()));
                        ItemMeta itemMeta3 = itemStack3.getItemMeta();
                        ItemMeta itemMeta4 = itemStack4.getItemMeta();
                        itemMeta3.setUnbreakable(true);
                        itemMeta4.setUnbreakable(true);
                        itemStack3.setItemMeta(itemMeta3);
                        itemStack4.setItemMeta(itemMeta4);
                        player.getInventory().setLeggings(itemStack3);
                        player.getInventory().setBoots(itemStack4);
                    }
                }

            }
        }
    }
    @EventHandler
    public void playerShoot(PlayerInteractEvent e){
        Action action = e.getAction();
        Player player = e.getPlayer();
        ItemStack item = e.getItem();
        if(isDown.get(player))e.setCancelled(true);


        clickEvent(player,item,action);
    }

    public void clickEvent(Player player, ItemStack item, Action action){
        isDown.putIfAbsent(player,false);
        Gold.putIfAbsent(player.getUniqueId(),0);
        isCounting.putIfAbsent(player.getUniqueId(),false);
        if(isCounting.get(player.getUniqueId())){
            if(player.getInventory().getHeldItemSlot() < 5&&player.getInventory().getHeldItemSlot()>0) {
                slotClicks.putIfAbsent(player.getUniqueId(), new ArrayList<>(Arrays.asList(0L, 0L, 0L, 0L)));
                List<Long> slotClickList = new ArrayList<>(slotClicks.get(player.getUniqueId()));
                slotClickList.set(player.getInventory().getHeldItemSlot()-1, slotClickList.get(player.getInventory().getHeldItemSlot()-1) + 1);
                slotClicks.put(player.getUniqueId(), slotClickList);
            }

        }

        if (item == null)return;


        if(item.getType().equals(Material.BLAZE_ROD)&&action.isRightClick()){
            new RightLightningRod().lightningRod(player);
        }

        if (item.getType().equals(Material.IRON_SWORD)&&action.isRightClick()) {
            isBlock.putIfAbsent(player, Boolean.FALSE);
            lastBlock.putIfAbsent(player, 0L);
            if (System.currentTimeMillis() - lastBlock.get(player) >= 100L) {
                isBlock.replace(player, Boolean.TRUE);
                ItemStack shield = new ItemStack(Material.SHIELD);
                ItemMeta itemMeta = shield.getItemMeta();
                itemMeta.setUnbreakable(true);
                shield.setItemMeta(itemMeta);
                player.setShieldBlockingDelay(0);
                player.getInventory().setItemInOffHand(shield);
                (new BukkitRunnable() {
                    public void run() {
                        if (!player.isBlocking() && !player.isHandRaised() || !player.getInventory().getItemInMainHand().getType().equals(Material.IRON_SWORD)) {
                            isBlock.replace(player, Boolean.FALSE);
                            lastBlock.put(player, System.currentTimeMillis());
                            player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
                            cancel();
                        }
                    }
                }).runTaskTimer(plugin, 6L, 1L);
            }
        }

        if(!action.isRightClick()||!(item.getType().equals(Material.WOODEN_HOE) ||item.getType().equals(Material.IRON_HOE) ||item.getType().equals(Material.DIAMOND_PICKAXE) || item.getType().equals(Material.GOLDEN_PICKAXE) || item.getType().equals(Material.GOLDEN_SHOVEL) || item.getType().equals(Material.FLINT_AND_STEEL)))return;

        ItemMeta meta = item.getItemMeta();
        HasQF.putIfAbsent(player.getName(),false);
        HasFB.putIfAbsent(player.getName(),false);
        isCounting.putIfAbsent(player.getUniqueId(),false);
        if(isCounting.get(player.getUniqueId())&&guns.contains(player.getInventory().getItemInMainHand().getType())){
            gunClicks.putIfAbsent(player.getUniqueId(),new ArrayList<>(Arrays.asList(0L,0L,0L,0L,0L,0L)));
            List<Long> gunClickList = new ArrayList<>(gunClicks.get(player.getUniqueId()));
            gunClickList.set(guns.indexOf(player.getInventory().getItemInMainHand().getType()),gunClickList.get(guns.indexOf(player.getInventory().getItemInMainHand().getType()))+1);
            gunClicks.put(player.getUniqueId(),gunClickList);
        }

        if (!meta.hasCustomModelData()) {
            Random random = new Random();
            int randomMeta = random.nextInt(100000);
            meta.setCustomModelData(randomMeta);
            switch (item.getType()) {
                case DIAMOND_PICKAXE -> meta.setDisplayName("§6Zombie Zapper");
                case GOLDEN_PICKAXE -> meta.setDisplayName("§6Gold Digger");
                case GOLDEN_SHOVEL -> meta.setDisplayName("§6Rainbow Rifle");
                case FLINT_AND_STEEL -> meta.setDisplayName("§6Double Barrel Shotgun");
                case IRON_HOE -> meta.setDisplayName("§6Shotgun");
                case WOODEN_HOE -> meta.setDisplayName("§6Pistol");
            }
            item.setItemMeta(meta);
        }
        isReloading.putIfAbsent(item.getItemMeta().getCustomModelData(),false);
        Ultimates.putIfAbsent(item.getItemMeta().getCustomModelData(),0);
        totalBullets.putIfAbsent(item.getItemMeta().getCustomModelData(),totalbulletsMaterial.get(item.getType())[Ultimates.get(item.getItemMeta().getCustomModelData())]);

        if(isReloading.get(item.getItemMeta().getCustomModelData())||isDown.get(player))return;
        player.setExp(1);
        if(totalBullets.get(item.getItemMeta().getCustomModelData())<0)
            totalBullets.put(item.getItemMeta().getCustomModelData(),0);

        player.setLevel(totalBullets.get(item.getItemMeta().getCustomModelData()));

        switch (item.getType()){
            case DIAMOND_PICKAXE:
                new ZombieZapper().zombieZapper(lastShotTimes,isReloading,magazines,player,item);
                break;
            case GOLDEN_PICKAXE:
                new GoldDigger().goldDigger(lastShotTimes,isReloading,magazines,player,item);
                break;
            case GOLDEN_SHOVEL:
                new RainbowRifle().rainbowRifle(isShootingRR,lastShotTimes,isReloading,magazines,player,item);
                break;
            case FLINT_AND_STEEL:
                new dbs().doubleBarrel(lastShotTimes,isReloading,magazines,player,item);
                break;
            case IRON_HOE:
                new ShotGun().shotgun(lastShotTimes,isReloading,magazines,player,item);
                break;
            case WOODEN_HOE:
                new Pistol().Pistol(lastShotTimes,isReloading,magazines,player,item);
            default:
        }

    }

}
