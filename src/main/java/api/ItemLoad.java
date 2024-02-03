package api;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.slavawins.reassets.contracts.ItemImageContract;
import org.slavawins.reassets.controllers.RegisterImageController;

public class ItemLoad {

    public static ItemStack getByPath(String path) {

        for (ItemImageContract img : RegisterImageController.images) {

            if (img.modelNameForOveride.replace(".png", "").indexOf(path) <= -1 && !img.enumName.equalsIgnoreCase(path))
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
