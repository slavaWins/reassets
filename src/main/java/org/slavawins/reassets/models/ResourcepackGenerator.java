package org.slavawins.reassets.models;

import org.slavawins.reassets.FileHasedCopy;
import org.slavawins.reassets.Reassets;
import org.slavawins.reassets.contracts.ItemImageContract;
import org.slavawins.reassets.contracts.OveriderModelMCContrcat;
import org.slavawins.reassets.controllers.CreateOverideTask;
import org.slavawins.reassets.controllers.RegisterImageController;
import org.slavawins.reassets.converters.VanilaParser;
import org.slavawins.reassets.proplugin.OpLog;
import org.slavawins.reassets.repositories.RawImagesRepository;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ResourcepackGenerator {

    public static File rootFoolder;
    public static ResourcepackGenerator instanse;

    public ResourcepackGenerator() {
        instanse = this;
    }

    public void CreateStructureResoursePack() {

        String resourcepackPath = Reassets.myDataFolder.getAbsolutePath() + "/resourcepack";
        rootFoolder = new File(resourcepackPath);

        //  System.out.println(resourcepackPath);

        List<String> foolders = new ArrayList<>();
        foolders.add("/assets/minecraft/textures/generated");
        foolders.add("/assets/minecraft/models/generated");
        foolders.add("/assets/minecraft/models/item");
        foolders.add("/assets/minecraft/atlases");

        for (String p : foolders) {

            File f = new File(resourcepackPath + p);
            if (f.exists()) continue;
            f.mkdirs();
        }

    }

    public void CopyRawImagesToResorsepack(List<File> fileList) {
        String resourcepackPath = Reassets.myDataFolder.getAbsolutePath() + "/resourcepack/assets/minecraft/textures/generated";

        for (File img : fileList) {


            String pathLocal = img.getPath(); // будет /xmob/admin_tool/npc_cloner.png
            pathLocal = pathLocal.replace("\\", "/");
            pathLocal = pathLocal.replaceFirst("plugins", "");
            pathLocal = pathLocal.replaceFirst("/reassets", "");


            //Path sourcePath = img.toPath();
            Path sourcePath = Path.of(img.getAbsolutePath());
            Path destinationPath = Path.of(resourcepackPath, pathLocal);


            //System.out.println(sourcePath);
            //System.out.println("---> " + destinationPath);
            FileHasedCopy.copyImages(sourcePath, destinationPath);

            File imgNew = new File(destinationPath.toAbsolutePath().toString());

            RegisterImageController.AddImage(imgNew, pathLocal);
        }
        RawImagesRepository.fileList.clear();
        OpLog.Debug( "Files coped in respack!");
    }

    public File getFoolder() {
        return rootFoolder;
    }


    List<OveriderModelMCContrcat> overiderModelMCContrcats = new ArrayList<>();

    public void MappingOverides() {

        File materialItems = new File(rootFoolder.getPath() + "/assets/minecraft/models/item");

        List<VanilaOverideFasadeModel> vnailaItemCoreModels = VanilaParser.Parse(materialItems);

        for (VanilaOverideFasadeModel model : vnailaItemCoreModels) {

            OpLog.Debug(model.file.getName() + " max: " + model.maxId);
        }
    }


    public void IndexingPivots() {
        for (ItemImageContract img : RegisterImageController.images) {

            VanilaOverideFasadeModel.OverideFindResponse response = VanilaOverideFasadeModel.GetModelByRealitiveTexturePath(img.modelNameForOveride.replace(".png", ""));

            if (response != null) {

                img.modelId = response.overide.predicate.custom_model_data;
                img.material = response.fasadeModel.material;

                //img.sout();

                continue;
            }

            CreateOverideTask.AddTask(img);

        }

    }

}
