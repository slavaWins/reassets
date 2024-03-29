package org.slavawins.reassets.proplugin.fileutils;


import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class JarUtil {
    public static final char JAR_SEPARATOR = '/';

    public static void copyFolderFromJar(JavaPlugin plugin, String folderName, File destFolder, CopyOption option) throws IOException {
        copyFolderFromJar(plugin, folderName, destFolder, option, null);
    }

    public static void copyFolderFromJar(JavaPlugin plugin,String folderName, File destFolder, CopyOption option, PathTrimmer trimmer) throws IOException {



        if (!destFolder.exists())
            destFolder.mkdirs();

        byte[] buffer = new byte[1024];

        File fullPath = null;
        //String path = JarUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String path =  plugin.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
          //path = plugin.class.getProtectionDomain().getCodeSource().getLocation().getPath();
       // System.out.println(ChatColor.YELLOW +""+path);
        if (trimmer != null)
            path = trimmer.trim(path);
        try {
            if (!path.startsWith("file"))
                path = "file://" + path;

            fullPath = new File(new URI(path));
        } catch (URISyntaxException e) {

            System.out.println("  extract  error" + folderName);
            e.printStackTrace();
        }
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fullPath));

        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            if (!entry.getName().startsWith(folderName + JAR_SEPARATOR))
                continue;

            String fileName = entry.getName();

            if (fileName.charAt(fileName.length() - 1) == JAR_SEPARATOR) {
                File file = new File(destFolder + File.separator + fileName);
                if (file.isFile()) {
                    file.delete();
                }
                file.mkdirs();
                continue;
            }

            File file = new File(destFolder + File.separator + fileName);
            if (option == CopyOption.COPY_IF_NOT_EXIST && file.exists())
                continue;

            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();

            if (!file.exists())
                file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);

            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
        }

        zis.closeEntry();
        zis.close();
    }

    public enum CopyOption {
        COPY_IF_NOT_EXIST, REPLACE_IF_EXIST;
    }

    @FunctionalInterface
    public interface PathTrimmer {
        String trim(String original);
    }

}