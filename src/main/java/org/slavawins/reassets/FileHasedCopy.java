package org.slavawins.reassets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class FileHasedCopy {

    public static void copyFile(Path sourcePath, Path destinationPath) {


        File file = new File(destinationPath.toAbsolutePath().toString());
       // System.out.println("PARENT:" + file.getParentFile().getAbsolutePath());

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (isFileDuplicate(sourcePath, destinationPath)) {
            //System.out.println("--------< DUBLIC!");
            //return;
        }

        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
          //  System.out.println("File  " + sourcePath.getFileName() + " copyed.");
        } catch (IOException e) {
            System.err.println("Error copy img " + e.getMessage());
        }
    }


    private static boolean isFileDuplicate(Path sourcePath, Path destinationPath) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Рассчитываем хэш для исходного файла
            byte[] sourceHash = digest.digest(Files.readAllBytes(sourcePath));

            // Если целевой файл уже существует, рассчитываем его хэш и сравниваем с исходным хэшем
            if (Files.exists(destinationPath)) {
                byte[] destinationHash = digest.digest(Files.readAllBytes(destinationPath));
                return MessageDigest.isEqual(sourceHash, destinationHash);
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
