package org.slavawins.reassets.integration;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * Когда создался новый моб
 * ReassetsItemCreateEvent customEvent = new ReassetsItemCreateEvent(args);
 * Bukkit.getPluginManager().callEvent(customEvent);
 */
public class ReassetsItemCreateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final ItemStack item;
    private final String enumName;
    private final String pathName;

    public ReassetsItemCreateEvent(ItemStack item, String pathName, String enumName) {
        this.pathName = pathName;
        this.item = item;
        this.enumName = enumName;
    }

    public ItemStack getItem() {
        return item;
    }

    public String getPathName() {
        return pathName;
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
}
