package org.slavawins.reassets.repositories;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RawImagesRepository {

    public static List<File> uiList = new ArrayList<>();
    public static List<File> sounds = new ArrayList<>();
    public static List<File> models3dList = new ArrayList<>();
    public static List<File> testureList = new ArrayList<>();
    public static List<File> imagesItems = new ArrayList<>();

    public static void add(File img) {
        imagesItems.add(img);
    }
}
