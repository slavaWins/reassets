package org.slavawins.reassets.controllers;

import org.slavawins.reassets.contracts.CategoryEnum;
import org.slavawins.reassets.contracts.ItemImageContract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RegisterImageController {

    public static String convertToNames(String name) {

        name = name.replace(".json", "");
        //   name = name.replace(Reassets.dataFolderPlugins.getAbsolutePath(), "");
        name = name.replace(".png", "").toUpperCase().replaceAll("[^A-Z0-9_]", "_");
        name = name.replace("_ASSETS", "");

        name = name.replace("__", "_");
        name = name.substring(1);
        return name;
    }

    public static List<ItemImageContract> images = new ArrayList<>();

    public static void AddAsItem(File file, String relitivePath, CategoryEnum categoryTyep) {
        if (!file.exists()) {
            //System.out.println("PROBLEM EMPTY FILE IN CONTROLLER REGISTE");
            return;
        }

        String enumName = convertToNames(relitivePath);


        ItemImageContract itemImageContract = new ItemImageContract();
        itemImageContract.enumName = enumName;
        itemImageContract.file = file;
        itemImageContract.categoryTyep = categoryTyep;
        itemImageContract.modelNameForOveride = "generated" + relitivePath;


        if (file.getAbsolutePath().indexOf("\\models\\block\\"+file.getName()) > 0) {
            itemImageContract.isBlock = true;
        }

        if (itemImageContract.categoryTyep == CategoryEnum.ui) {
            //itemImageContract.modelNameForOveride = "reassets_ui" + relitivePath;
        }

        // System.out.println("\n ----- AddImage");
        // itemImageContract.sout();
        images.add(itemImageContract);

    }
}
