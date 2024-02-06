package org.slavawins.reassets.handles;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.slavawins.reassets.contracts.ItemImageContract;
import org.slavawins.reassets.helpers.ContainerHelper;
import org.slavawins.reassets.integration.ReassetsItemCreateEvent;

public class ItemCreate {


    public static ItemStack getByImg(ItemImageContract img) {
        Material mat = Material.BONE;

        if(img.isBlock)mat =Material.PURPLE_STAINED_GLASS;

        ItemStack itemStack = new ItemStack(mat, 1);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setCustomModelData(img.modelId);
        meta.setDisplayName(img.enumName);


        ContainerHelper.Set(meta, "reassets", img.enumName);


        itemStack.setItemMeta(meta);

        ReassetsItemCreateEvent customEvent = new ReassetsItemCreateEvent(itemStack, img.modelNameForOveride, img.enumName);
        Bukkit.getPluginManager().callEvent(customEvent);

        return itemStack;
    }
}
