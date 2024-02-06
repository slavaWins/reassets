package org.slavawins.reassets.integration;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.slavawins.reassets.contracts.ItemImageContract;
import org.slavawins.reassets.controllers.RegisterImageController;
import org.slavawins.reassets.handles.FontMappingHandle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReassetsGet {


    public static ItemStack item(IEnumString path, String displayName) {
        return item(path.toString(), displayName);
    }

    public static ItemStack item(IEnumString path) {
        return item(path.toString());
    }


    public static List<String> namesByPlugin(JavaPlugin plugin) {
        List<String> list = new ArrayList<>();

        String foolderName = plugin.getDataFolder().getName().toLowerCase();

        for (ItemImageContract img : RegisterImageController.images) {

            if (img.modelNameForOveride.startsWith(foolderName) || img.enumName.startsWith(foolderName.toUpperCase() + "_")) {
                list.add(img.enumName);
            }
        }
        return list;
    }

    /**
     * Получать итем с текстурой из моего плагина
     *
     * @param path
     * @return
     */
    public static ItemStack item(String path, String displayName) {
        ItemStack item = item(path);
        if (item == null) return null;

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        return item;
    }


    static ItemStack getByImg(ItemImageContract img) {
        ItemStack itemStack = new ItemStack(Material.BONE, 1);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setCustomModelData(img.modelId);
        meta.setDisplayName(img.enumName);
        itemStack.setItemMeta(meta);

        ReassetsItemCreateEvent customEvent = new ReassetsItemCreateEvent(itemStack, img.modelNameForOveride, img.enumName);
        Bukkit.getPluginManager().callEvent(customEvent);

        return itemStack;
    }

    /**
     * Получать итем с текстурой из моего плагина
     *
     * @param path
     * @return
     */
    public static ItemStack item(String path) {

        path = path.replace(".png", "").replace(".json", "");


        String s1 = path.replace("/reassets/", "");
        s1 = path.replace("reassets/", "");
        s1 = s1.replace("//", "");

        ItemImageContract selected = null;
        for (ItemImageContract img : RegisterImageController.images) {


            //не очень эффективно надо где-то закэшировать реплейснутый вариант чтоб искать быстрее
            if (img.modelNameForOveride.replace(".png", "").replace(".json", "").indexOf(path) <= -1 && !img.enumName.equalsIgnoreCase(path))
                continue;

            return getByImg(img);
        }


        //Если не нашли по полному нейму, то ищем по относительному
        for (ItemImageContract img : RegisterImageController.images) {
            if ((img.modelNameForOveride.indexOf(s1) > 0)) {
                return getByImg(img);
            }
        }


        System.out.println(ChatColor.RED + "[reassets] NOT EXIST ITEM " + path);
        return null;
    }

    /**
     * Получать юникод картинки по её внутреннему пути в вашем плагине
     *
     * @param localPath
     * @return
     */
    public static String image(String localPath) {

        localPath = localPath.replace(".png", "");
        String ENUM_VARIANT = localPath.replace("?", "_").toUpperCase();


        if (FontMappingHandle.uis.containsKey(localPath)) {
            return FontMappingHandle.uis.get(localPath);
        }

        String s1 = localPath.replace("/reassets", "");
        s1 = s1.replace("reassets/", "");

        for (Map.Entry<String, String> entry : FontMappingHandle.uis.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            //  System.out.println(key);
            if (key.indexOf(s1) > 0) return value;
        }


        return null;
    }
}
