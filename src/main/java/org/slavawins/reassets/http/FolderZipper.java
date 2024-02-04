package org.slavawins.reassets.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class FolderZipper {
    public static void Arhivate(File file, String zipPath) {
        String folderPath = file.getPath();

        try {
            File folder = new File(folderPath);
            File zipFile = new File(zipPath);

            if (!zipFile.getParentFile().exists()) {
                zipFile.getParentFile().mkdirs();
            }
            if(zipFile.exists()){
                zipFile.delete();
            }

            // Создание объекта ZipOutputStream для записи в ZIP-архив
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));

            // Рекурсивное добавление файлов из папки в архив
            addFolderToZip(folder, zipOutputStream, "");

            // Закрытие потока ZipOutputStream
            zipOutputStream.close();

            System.out.println("Папка успешно запакована в ZIP-архив.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addFolderToZip(File folder, ZipOutputStream zipOutputStream, String parentFolder) throws IOException {
        // Получение списка файлов и папок в заданной папке
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Рекурсивное добавление подпапки в архив
                    addFolderToZip(file, zipOutputStream, parentFolder + file.getName() + "/");
                } else {
                    // Создание объекта ZipEntry для текущего файла
                    ZipEntry zipEntry = new ZipEntry(parentFolder + file.getName());

                    // Добавление ZipEntry в архив
                    zipOutputStream.putNextEntry(zipEntry);

                    // Копирование содержимого файла в архив
                    FileInputStream fileInputStream = new FileInputStream(file);
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                        zipOutputStream.write(buffer, 0, bytesRead);
                    }

                    // Закрытие потока FileInputStream
                    fileInputStream.close();
                }
            }
        }
    }
}