package org.slavawins.reassets.converters;

import org.slavawins.reassets.repositories.RawImagesRepository;

import java.io.File;

public class PluginScan {

    public static void Scan(File subfolder) {


        File assetsFoolder = new File(subfolder.getPath() + "/reassets/");
        if (!assetsFoolder.exists()) return;


        for (File img : Mapper.MappingImages(assetsFoolder)) {

            if (img.getName().indexOf(".zip") > 0) continue;
            if (img.getName().indexOf(".png") < 0) continue;

            RawImagesRepository.add(img);
        }


    }
}
