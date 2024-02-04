package org.slavawins.reassets.integration;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.slavawins.reassets.proplugin.fileutils.JarUtil;

import java.io.File;
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

        extractTo(plugin, folderName, plugin.getDataFolder().getAbsolutePath());
    }

    public static void extractTo(JavaPlugin plugin, String folderName, String to) {
        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();

        try {
            System.out.println("[" + plugin.getName() + "] extract " + folderName);
            JarUtil.copyFolderFromJar(plugin, folderName, new File(to), JarUtil.CopyOption.COPY_IF_NOT_EXIST);

        } catch (IOException e) {
            System.out.println(ChatColor.RED + "[" + plugin.getName() + "] ERROR!!! extract " + folderName);
            throw new RuntimeException(e);
        }
    }

}
