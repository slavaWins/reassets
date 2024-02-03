package org.slavawins.reassets.converters;

import org.slavawins.reassets.repositories.RawImagesRepository;

import java.io.File;

public class PluginScan {

    public static void Scan(File subfolder) {


        File assetsFoolder = new File(subfolder.getPath() + "/assets/");
        if (!assetsFoolder.exists()) return;

        //   System.out.println(" \n\n-------- SCAN:" + subfolder.getName());


        for (File img : Mapper.MappingImages(assetsFoolder)) {

            if (!img.getName().contains(".zip")) continue;

            RawImagesRepository.add(img);
        }


    }
}
