package org.slavawins.reassets.repositories;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImagesRepository {

    public static List<File> fileList = new ArrayList<>();

    public static void add(File img) {
        fileList.add(img);
    }
}
