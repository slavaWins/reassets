package org.slavawins.reassets.proplugin;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Lang {

    private static File langFile;

    public static String translaste(String key, String text) {


      //  System.out.println("---- LOADIN: " + langFile.getAbsolutePath());

        String res = langConfig.getString(key, "");
        if (!res.isEmpty()) return res;

        langConfig.set(key, text);

        try {
            langConfig.save(langFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return text;

    }

    public static YamlConfiguration langConfig;

    public static File myDataFolder;

    public static void init(JavaPlugin plugin, String language) {
        myDataFolder = plugin.getDataFolder();
        loadLanguageFile(language);
    }

    public static void loadLanguageFile(String language) {
        langFile = new File(myDataFolder, "lang/" + language + ".yml");

    //    System.out.println("---- START LOAD LANG: " + langFile.getAbsolutePath());

        if (!langFile.exists()) {
            // Если файл локализации не найден, используем ru язык
            langFile = new File(myDataFolder, "lang/ru.yml");
        }

      //  System.out.println("---- LOADIN: " + langFile.getAbsolutePath());
        langConfig = YamlConfiguration.loadConfiguration(langFile);
    }
}
