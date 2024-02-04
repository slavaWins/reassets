package org.slavawins.reassets.integration;

import org.bukkit.plugin.java.JavaPlugin;
import org.slavawins.reassets.proplugin.fileutils.JarUtil;

import java.io.IOException;

public class ResourceExtractor {


    /**
     * Извлекает папку из ресурсов плагина.
     *
     * @param plugin     плагин JavaPlugin, для которого нужно извлечь ресурсы
     * @param folderName название папки ресурсов, которую нужно извлечь
     */
    public static void extract(JavaPlugin plugin, String folderName) {
        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();

        try {
            JarUtil.copyFolderFromJar(folderName, plugin.getDataFolder(), JarUtil.CopyOption.COPY_IF_NOT_EXIST);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
