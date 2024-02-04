package org.slavawins.reassets.integration;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.slavawins.reassets.contracts.ItemImageContract;
import org.slavawins.reassets.controllers.RegisterImageController;
import org.slavawins.reassets.handles.FontMappingHandle;

public class ImageLoad {

    /**
     * Получать юникод картинки по её внутреннему пути в вашем плагине
     * @param localPath
     * @return
     */
    public static String get(String localPath) {

        localPath = localPath.replace(".png", "");

        if(FontMappingHandle.uis.containsKey(localPath)){
            return  FontMappingHandle.uis.get(localPath);
        }

        return null;
    }

}
