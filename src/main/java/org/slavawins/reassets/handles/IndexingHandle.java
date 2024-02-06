package org.slavawins.reassets.handles;

import org.bukkit.inventory.ItemStack;
import org.slavawins.reassets.Reassets;
import org.slavawins.reassets.contracts.CategoryEnum;
import org.slavawins.reassets.contracts.ItemImageContract;
import org.slavawins.reassets.controllers.RegisterImageController;
import org.slavawins.reassets.integration.ReassetsGet;
import org.slavawins.reassets.models.VanilaOverideFasadeModel;
import org.slavawins.reassets.controllers.CreateOverideTask;
import org.slavawins.reassets.converters.Scaner;
import org.slavawins.reassets.models.ResourcepackGenerator;
import org.slavawins.reassets.repositories.RawImagesRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexingHandle {

    public static float onIndexingTime = 0;


    public static void Clear() {

        RegisterImageController.images.clear();
        CreateOverideTask.Clear();
        VanilaOverideFasadeModel.list.clear();
    }

    public static void Indexing() {

        long currentTime = System.currentTimeMillis();
        Scaner.Init(Reassets.myDataFolder);
        ResourcepackGenerator resourcepackGenerator = new ResourcepackGenerator();
        resourcepackGenerator.CreateStructureResoursePack();

        resourcepackGenerator
                .CopyRawImagesToResorsepack(RawImagesRepository.imagesItems, CategoryEnum.items);
        resourcepackGenerator.CopyRawImagesToResorsepack(RawImagesRepository.testureList, CategoryEnum.textures);
        resourcepackGenerator.CopyRawImagesToResorsepack(RawImagesRepository.uiList, CategoryEnum.ui);
        resourcepackGenerator.CopyRawImagesToResorsepack(RawImagesRepository.sounds, CategoryEnum.sounds);
        resourcepackGenerator.Copy3DModelsToResorsepack(RawImagesRepository.models3dList);

        RawImagesRepository.uiList.clear();
        RawImagesRepository.imagesItems.clear();
        RawImagesRepository.testureList.clear();
        RawImagesRepository.models3dList.clear();

        FontMappingHandle.Indexing();
        FontMappingHandle.Addder();

        SoundMappingHandle.Indexing();
        SoundMappingHandle.Addder();

        RawImagesRepository.sounds.clear();
        //resourcepackGenerator.CopyRawImagesToResorsepack( RawImagesRepository.models3dList);
        resourcepackGenerator.MappingOverides();
        resourcepackGenerator.IndexingPivots();
        IndexingPluginPrefixs();
        onIndexingTime = (System.currentTimeMillis() - currentTime) / 1000f;

    }


    public static Map<String, ItemStack> pluginPrefixs = new HashMap<>();

    public static Map<String, ItemStack> getPluginsPrefixs() {
        return pluginPrefixs;
    }

    public static void IndexingPluginPrefixs() {
        for (ItemImageContract img : RegisterImageController.images) {
            if(!(img.categoryTyep==CategoryEnum.items || img.categoryTyep==CategoryEnum.models))continue;

            int indexOfUnderscore = img.enumName.indexOf("_");
            String plugin = img.enumName.substring(0, indexOfUnderscore);

            if (pluginPrefixs.containsKey(plugin)) continue;


            pluginPrefixs.put(plugin, ReassetsGet.item(img.modelNameForOveride, plugin));
        }

    }
}
