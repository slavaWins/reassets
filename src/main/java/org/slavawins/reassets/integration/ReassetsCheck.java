package org.slavawins.reassets.integration;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.slavawins.reassets.helpers.ContainerHelper;

public class ReassetsCheck {

    public static String isReasset(ItemStack item) {
        if (item == null) return null;
        ItemMeta meta = item.getItemMeta();

        return ContainerHelper.GetString(meta, "reassets");
    }
}
