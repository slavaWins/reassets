package org.slavawins.reassets.converters;

import org.slavawins.reassets.Reassets;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Mapper {




    public static List<File> MappingImages(File assets) {
        List<File> fileList = new ArrayList<>();
        traverseDirectory(assets, fileList);
        return fileList;
    }

    private static void traverseDirectory(File directory, List<File> fileList) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        traverseDirectory(file, fileList);
                    } else if (file.getName().toLowerCase().endsWith(".png") || file.getName().toLowerCase().endsWith(".json")) {
                        fileList.add(file);
                    }
                }
            }
        }
    }
}
