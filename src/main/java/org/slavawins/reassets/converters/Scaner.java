package org.slavawins.reassets.converters;

import java.io.File;

public class Scaner {

    private static File dataFoolder;

    public static void Init(File _dataFoolder) {

        System.out.println(" \n-------- ASSET LOADER [PLUGINS] --------\n");
        dataFoolder = _dataFoolder;
        ScanPlugins();
    }


    public static void ScanPlugins() {
        // Путь к папке плагина
        String pluginFolderPath = "/";

        // Создание объекта File для указанной папки
        File pluginFolder = dataFoolder.getParentFile();

        System.out.println(dataFoolder.getAbsolutePath());

        // Проверка, является ли указанный путь директорией
        if (!pluginFolder.isDirectory()) return;

        // Получение списка всех папок внутри указанной папки
        File[] subfolders = pluginFolder.listFiles(File::isDirectory);

        if (subfolders == null) return;


        for (File subfolder : subfolders) {
            System.out.println(subfolder.getName());
            PluginScan.Scan(subfolder);
        }

    }
}
