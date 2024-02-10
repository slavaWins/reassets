package org.slavawins.reassets.configs;

import org.bukkit.configuration.file.YamlConfiguration;
import org.slavawins.reassets.Reassets;

import java.io.File;
import java.io.IOException;

public class ConfigNames {


    private static File file;
    private static YamlConfiguration yamlConfiguration;
    private static File dataFoolder;


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
        file = new File(dataFoolder, "names.yml");

        if (!file.exists()) {
            Reassets.getInstance().saveResource("names.yml", false);
        }
        yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public static void Init(File _dataFoolder) {
        if (!_dataFoolder.exists()) _dataFoolder.mkdirs();

        dataFoolder = _dataFoolder;

        Reload();

    }


}
