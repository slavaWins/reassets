package org.slavawins.reassets;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.FileUtil;

import java.io.File;

public class ExtractInstall {
    public static void copyResources() {
        try {
            // Получаем путь до папки плагина
            File pluginFolder = Reassets.getInstance().getDataFolder();

            Reassets.getInstance().saveResource("config.yml", false);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
