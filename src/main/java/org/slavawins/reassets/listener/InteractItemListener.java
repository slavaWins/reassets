package org.slavawins.reassets.listener;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.slavawins.reassets.integration.PlayerReassetsEatEvent;
import org.slavawins.reassets.integration.PlayerReassetsInteractEvent;
import org.slavawins.reassets.integration.ReassetsCheck;

public final class InteractItemListener implements Listener {


    @org.bukkit.event.EventHandler(priority = EventPriority.LOWEST)
    public static void OnRClick(PlayerInteractEvent event) {


        ItemStack itemStack = event.getItem();

        if (itemStack == null) {
            itemStack = event.getPlayer().getInventory().getItemInOffHand();
            if (itemStack == null) return;
        }

        ItemMeta meta = itemStack.getItemMeta();
        if(meta==null)return;

        if (!meta.hasCustomModelData ()) return;


        String id = ReassetsCheck.isReasset(itemStack);
        if (id == null) return;


        PlayerReassetsInteractEvent event1 = new PlayerReassetsInteractEvent(itemStack, id, event.getPlayer(), event.getAction());
        Bukkit.getPluginManager().callEvent(event1);
    }


    @org.bukkit.event.EventHandler(priority = EventPriority.HIGH)
    public static void OnRClick(PlayerItemConsumeEvent event) {


        ItemStack itemStack = event.getItem();

        if (itemStack == null) {
            itemStack = event.getPlayer().getInventory().getItemInOffHand();
            if (itemStack == null) return;
        }

        ItemMeta meta = itemStack.getItemMeta();
        if(meta==null)return;
        if (!meta.hasCustomModelData ()) return;


        String id = ReassetsCheck.isReasset(itemStack);
        if (id == null) return;

 
        PlayerReassetsEatEvent event1 = new PlayerReassetsEatEvent(itemStack, id, event.getPlayer());
        Bukkit.getPluginManager().callEvent(event1);

        if (itemStack.getType() == Material.POTION) {


            Player player = event.getPlayer();

            if(player.getInventory().getItemInMainHand().equals(itemStack)){
                player.getInventory().setItemInMainHand(null);
            }
            if(player.getInventory().getItemInOffHand().equals(itemStack)){
                player.getInventory().setItemInOffHand(null);
            }
            event.setCancelled(true);
        }

    }


}