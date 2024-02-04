package org.slavawins.reassets.lang;

import org.bukkit.configuration.file.YamlConfiguration;
import org.slavawins.reassets.Reassets;

import java.io.File;
import java.io.IOException;

public class LangHelper {

    private static File langFile;

    public static String translaste(String key, String text) {

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

    public static void loadLanguageFile(String language) {
        langFile = new File(Reassets.myDataFolder, "lang/" + language + ".yml");

        if (!langFile.exists()) {
            // Если файл локализации не найден, используем английский язык
            langFile = new File(Reassets.myDataFolder, "lang/ru.yml");
        }

        System.out.println("---- LOADIN: " + langFile.getAbsolutePath());
        langConfig = YamlConfiguration.loadConfiguration(langFile);
    }
}
