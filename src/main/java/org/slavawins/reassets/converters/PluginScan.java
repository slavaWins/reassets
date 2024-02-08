package org.slavawins.reassets.converters;

import org.slavawins.reassets.repositories.RawImagesRepository;

import java.io.File;

public class PluginScan {

    public static void ScanPluginFoolder(File pluginFoolder) {


        File reassetsFoolder = new File(pluginFoolder.getPath() + "/reassets/");
        if (!reassetsFoolder.exists()) return;


        for (File categorty : reassetsFoolder.listFiles()) {

            if (!categorty.isDirectory()) continue;

            if (categorty.getName().equalsIgnoreCase("minecraft")) {
                if (!(new File(categorty, "models").exists() && new File(categorty, "textures").exists())) continue;

                for (File img : ScanTargetTypesFiles.MappingImages(categorty)) {
                    if (img.getName().endsWith(".png")) RawImagesRepository.testureList.add(img);
                    if (img.getName().endsWith(".json")) RawImagesRepository.models3dList.add(img);
                }
            }

            if (categorty.getName().equalsIgnoreCase("items")) {

                for (File img : ScanTargetTypesFiles.MappingImages(categorty)) {
                    if (img.getName().indexOf(".png") < 0) continue;
                    RawImagesRepository.add(img);
                }

            }

            if (categorty.getName().equalsIgnoreCase("sounds")) {

                for (File img : ScanTargetTypesFiles.MappingImages(categorty)) {
                    if (img.getName().indexOf(".ogg") < 0) continue;
                    RawImagesRepository.sounds.add(img);
                }

            }


            if (categorty.getName().equalsIgnoreCase("ui")) {
                for (File img : ScanTargetTypesFiles.MappingImages(categorty)) {
                    if (img.getName().indexOf(".png") < 0) continue;
                    RawImagesRepository.uiList.add(img);
                }
            }


        }

    }
}
