package org.slavawins.reassets.handles;

import org.slavawins.reassets.Reassets;
import org.slavawins.reassets.controllers.RegisterImageController;
import org.slavawins.reassets.models.VanilaOverideFasadeModel;
import org.slavawins.reassets.controllers.CreateOverideTask;
import org.slavawins.reassets.converters.Scaner;
import org.slavawins.reassets.models.ResourcepackGenerator;
import org.slavawins.reassets.repositories.RawImagesRepository;

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
                .CopyRawImagesToResorsepack( RawImagesRepository.imagesItems, true);
        resourcepackGenerator.CopyRawImagesToResorsepack( RawImagesRepository.testureList, false);
        resourcepackGenerator.Copy3DModelsToResorsepack( RawImagesRepository.models3dList);

        RawImagesRepository.imagesItems.clear();
        RawImagesRepository.testureList.clear();
        RawImagesRepository.models3dList.clear();

        //resourcepackGenerator.CopyRawImagesToResorsepack( RawImagesRepository.models3dList);
        resourcepackGenerator.MappingOverides();
        resourcepackGenerator.IndexingPivots();

        onIndexingTime = (System.currentTimeMillis() - currentTime) / 1000f;

    }
}
