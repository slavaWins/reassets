package org.slavawins.reassets.integration;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.slavawins.reassets.contracts.ItemImageContract;
import org.slavawins.reassets.controllers.RegisterImageController;

public class ItemLoad {

    /**
     * Получать итем с текстурой из моего плагина
     * @param path
     * @return
     */
    public static ItemStack get(String path) {
        return getByPath(path);
    }


    public static ItemStack getByPath(String path) {

        path = path.replace(".png", "").replace(".json", "");

        for (ItemImageContract img : RegisterImageController.images) {

            //не очень эффективно надо где-то закэшировать реплейснутый вариант чтоб искать быстрее
            if (img.modelNameForOveride.replace(".png", "").replace(".json", "").indexOf(path) <= -1 && !img.enumName.equalsIgnoreCase(path))
                continue;

            ItemStack itemStack = new ItemStack(Material.BONE, 1);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setCustomModelData(img.modelId);
            meta.setDisplayName(img.enumName);
            itemStack.setItemMeta(meta);

            return itemStack;
        }
        return null;
    }

}
