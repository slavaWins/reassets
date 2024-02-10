package org.slavawins.reassets.handles;

import org.bukkit.configuration.file.YamlConfiguration;
import org.slavawins.reassets.configs.ConfigNames;
import org.slavawins.reassets.contracts.CategoryEnum;
import org.slavawins.reassets.contracts.ItemImageContract;
import org.slavawins.reassets.controllers.RegisterImageController;

public class IndexingNames {


    public static String generateTitleFromId(String name) {

        name = name + " ";
        name = name.replace("_BLOCK_", "_");
        name = name.replace("_MODELS_", "_");
        name = name.replace("ITEMS", "_");
        name = name.replace("__", "_");
        name = name.replace("__", "_");
        name = name.replace("_EAT ", "");
        name = name.replace("_", " ");
        name = name.trim();
        name = name.toLowerCase();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);


        return name;
    }

    public static void Run() {


        YamlConfiguration configNames = ConfigNames.GetConfig();

        boolean isChangge = false;
        for (ItemImageContract img : RegisterImageController.images) {
            if (!(img.categoryTyep == CategoryEnum.items || img.categoryTyep == CategoryEnum.models)) continue;


            if (!configNames.contains(img.enumName + ".title")) {
                String name = generateTitleFromId(img.enumName);
                isChangge = true;
                configNames.set(img.enumName + ".title", name);
                img.title = name;
            } else {
                img.title = configNames.getString(img.enumName + ".title");
            }

        }

        if (isChangge) {
            ConfigNames.Save();
        }


    }
}
