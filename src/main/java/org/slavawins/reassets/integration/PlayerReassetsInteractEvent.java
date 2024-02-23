package org.slavawins.reassets.integration;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

/**
 * Когда создался новый моб
 * PlayerReassetsInteractEvent customEvent = new PlayerReassetsInteractEvent(args);
 * Bukkit.getPluginManager().callEvent(customEvent);
 */
public class PlayerReassetsInteractEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final ItemStack item;
    private final String enumName;

    private final Player player;
    private final Action action;

    public PlayerReassetsInteractEvent(ItemStack item, String enumName, Player player, Action action) {

        this.item = item;
        this.enumName = enumName;
        this.player = player;
        this.action = action;
    }

    public ItemStack getItem() {
        return item;
    }

    public String getEnumName() {
        return enumName;
    }

    public boolean isPlugin(String pluginName) {
        return enumName.toUpperCase().startsWith(pluginName.toUpperCase() + "_");
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }


    public static HandlerList getHandlerList() {
        return handlers;

    }

    public Player getPlayer() {
        return player;
    }

    public Action getAction() {
        return action;
    }
}
