package org.slavawins.reassets;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigHelper {

    private static File file;
    private static YamlConfiguration yamlConfiguration;
    private static File dataFoolder;

    public static void DefYmlConfi() {
        yamlConfiguration.set("debug", true);

    }

    public static boolean IsDebug() {
        return yamlConfiguration.getBoolean("debug", true);
    }

    public static void Save() {
        try {
            yamlConfiguration.save(file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static YamlConfiguration GetConfig() {
        return yamlConfiguration;
    }


    public static void Reload() {
        file = new File(dataFoolder, "config.yml");

        if (!file.exists()) {
            Reassets.getInstance().saveResource("config.yml", false);
        }
        yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public static void Init(File _dataFoolder) {
        if (!_dataFoolder.exists()) _dataFoolder.mkdirs();

        dataFoolder = _dataFoolder;
                

        Reload();


    }


}
