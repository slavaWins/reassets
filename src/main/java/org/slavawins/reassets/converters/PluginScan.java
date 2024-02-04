package org.slavawins.reassets.converters;

import org.slavawins.reassets.repositories.RawImagesRepository;

import java.io.File;

public class PluginScan {

    public static void ScanPluginFoolder(File pluginFoolder) {


        File reassetsFoolder = new File(pluginFoolder.getPath() + "/reassets/");
        if (!reassetsFoolder.exists()) return;


        for (File categorty : reassetsFoolder.listFiles()) {

            if (!categorty.isDirectory()) continue;

            if (categorty.getName().equalsIgnoreCase("items")) {
                for (File img : Mapper.MappingImages(categorty)) {
                    if (img.getName().indexOf(".png") < 0) continue;
                    RawImagesRepository.add(img);
                }
            }


        }

    }
}
