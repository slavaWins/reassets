package org.slavawins.reassets.integration;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.slavawins.reassets.contracts.CategoryEnum;
import org.slavawins.reassets.contracts.ItemImageContract;
import org.slavawins.reassets.controllers.RegisterImageController;
import org.slavawins.reassets.handles.FontMappingHandle;
import org.slavawins.reassets.handles.ItemCreate;

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


    /**
     * Получать итем с текстурой из моего плагина
     *
     * @param path
     * @return
     */
    public static ItemStack item(String path) {

        path = path.trim();
        String pathOriginal = path;
        path = path.replace(".png", "").replace(".json", "");
        path = path.replace("generated/", "");
        int val = 1+1;

        String s1 = path.replace("/reassets/", "");
        s1 = path.replace("reassets/", "");
        s1 = s1.replace("//", "");
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        String pAsEnum = path.toUpperCase().replace("/", "_");

        for (ItemImageContract img : RegisterImageController.images) {
            if (img.categoryTyep != CategoryEnum.models) continue;
            if (img.categoryTyep != CategoryEnum.items) continue;

            //не очень эффективно надо где-то закэшировать реплейснутый вариант чтоб искать быстрее
            // if (img.modelNameForOveride.replace(".png", "").replace(".json", "").indexOf(path) <= -1 && !img.enumName.equalsIgnoreCase(path))

            if (img._search.indexOf(path) > -1 || img.enumName.equalsIgnoreCase(path) || img.enumName.equalsIgnoreCase(pathOriginal)) {
                return ItemCreate.getByImg(img);
            }
           // System.out.println(pathOriginal + "==" + img.enumName + "=" + (img.enumName.equalsIgnoreCase(pathOriginal)));


        }

        //Если не нашли по полному нейму, то ищем по относительному
        for (ItemImageContract img : RegisterImageController.images) {
            if (img.categoryTyep != CategoryEnum.models && img.categoryTyep != CategoryEnum.items) continue;
            //  System.out.println(img._search);

            if ((img._search.indexOf(s1) > 0) || img.enumName.equalsIgnoreCase(pAsEnum)) {
                return ItemCreate.getByImg(img);
            }
        }
        System.out.println(ChatColor.YELLOW + "[reassets] no item:" + path);
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
            if (key.indexOf(ENUM_VARIANT) > 0) return value;
            if (key.indexOf(s1) > 0) return value;
        }


        return null;
    }
}
