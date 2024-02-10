package org.slavawins.reassets.models;

import com.google.gson.Gson;
import org.slavawins.reassets.FileHasedCopy;
import org.slavawins.reassets.Reassets;
import org.slavawins.reassets.contracts.CategoryEnum;
import org.slavawins.reassets.contracts.ItemImageContract;
import org.slavawins.reassets.contracts.OveriderModelMCContrcat;
import org.slavawins.reassets.contracts.vanila.VanilaAtlasContract;
import org.slavawins.reassets.controllers.CreateOverideTask;
import org.slavawins.reassets.controllers.RegisterImageController;
import org.slavawins.reassets.converters.BlockBrenchConverter;
import org.slavawins.reassets.converters.VanilaParser;
import org.slavawins.reassets.integration.ResourceExtractor;
import org.slavawins.reassets.proplugin.OpLog;
import org.slavawins.reassets.repositories.RawImagesRepository;

import java.io.*;
import java.nio.file.Files;
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


        ResourceExtractor.extract(Reassets.getInstance(), "resourcepack");

        rootFoolder = new File(resourcepackPath);

        //  System.out.println(resourcepackPath);

        List<String> foolders = new ArrayList<>();
        foolders.add("/assets/minecraft/atlases");
        foolders.add("/assets/minecraft/textures/generated");
        foolders.add("/assets/minecraft/models/generated");
        foolders.add("/assets/minecraft/models/item");
        foolders.add("/assets/minecraft/atlases");
        foolders.add("/assets/minecraft/sounds");


        for (String p : foolders) {

            File f = new File(resourcepackPath + p);
            if (f.exists()) continue;
            f.mkdirs();
        }
        AtllasUpdate();
    }

    public void AtllasUpdate() {
        // System.out.println("-------------AtllasUpdate");
        try {
            File file = new File(rootFoolder, "/assets/minecraft/atlases/blocks.json");

            if (!file.exists()) {
                ResourceExtractor.extract(Reassets.getInstance(), "resourcepack/assets/minecraft/atlases");
                return;
            }
            //..Reader reader = new FileReader(file);
            String content = Files.readString(file.toPath());

            //  System.out.println(content);

            if (content.indexOf("\"generated/\"") > 0) return;
            //   System.out.println("-------------AtllasUpdate neeed");

            Gson gson = new Gson();
            VanilaAtlasContract vnailaItemCoreModel = gson.fromJson(content, VanilaAtlasContract.class);

            vnailaItemCoreModel.sources.add(new VanilaAtlasContract.SourcesVanilaAtlasContract());

            Writer writer = new FileWriter(file, false);
            gson.toJson(vnailaItemCoreModel, writer);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            System.out.println("----- [reassets] AtllasUpdate error");
            e.printStackTrace();
        }
    }

    private String RenamingImage(String pathLocal) {
        // System.out.println(pathLocal);
        pathLocal = pathLocal.replace("\\", "/");
        pathLocal = pathLocal.replaceFirst("plugins", "/");
        pathLocal = pathLocal.replaceFirst("/reassets", "/");


        pathLocal = pathLocal.replace("//", "/");
        pathLocal = pathLocal.replace("//", "/");

        //pathLocal = pathLocal.replace("//", "/");

        if (pathLocal.endsWith("/")) {
            pathLocal = pathLocal.substring(0, pathLocal.length() - 1);
        }
        pathLocal = pathLocal.toLowerCase();
        //System.out.println("====>  RenamingImage   " + pathLocal);
        return pathLocal;
    }

    public void CopyRawImagesToResorsepack(List<File> imagesItems, CategoryEnum categoryEnum) {
        String resourcepackPath = Reassets.myDataFolder.getAbsolutePath() + "/resourcepack/assets/minecraft/textures/generated";

        if (categoryEnum == CategoryEnum.sounds) {
            resourcepackPath = Reassets.myDataFolder.getAbsolutePath() + "/resourcepack/assets/minecraft/sounds";
        }

        for (File img : imagesItems) {

            String pathLocal = img.getPath();

            pathLocal = RenamingImage(pathLocal);     //plugins\trashitem\reassets\items\gun.png  ====>  /trashitem/items/gun.png

            if (categoryEnum == CategoryEnum.textures) {
                pathLocal = pathLocal.replace("minecraft/textures/", "textures/");
            }

            if (categoryEnum == CategoryEnum.sounds) {
                pathLocal = pathLocal.replace("/sounds", "/");
                pathLocal = pathLocal.replace("//", "/");
            }

            Path destinationPath = Path.of(resourcepackPath, pathLocal);


            //Path sourcePath = Path.of(img.getAbsolutePath());
            FileHasedCopy.copyFile(img.toPath(), destinationPath);

            File imgNew = new File(destinationPath.toAbsolutePath().toString());

            if (categoryEnum == CategoryEnum.items) {
                RegisterImageController.AddAsItem(imgNew, pathLocal, CategoryEnum.items);
            }

            if (categoryEnum == CategoryEnum.ui) {
                RegisterImageController.AddAsItem(imgNew, pathLocal, CategoryEnum.ui);
            }

            if (categoryEnum == CategoryEnum.sounds) {
                RegisterImageController.AddAsItem(imgNew, pathLocal, CategoryEnum.sounds);
            }

        }
        RawImagesRepository.imagesItems.clear();

        OpLog.Debug("Files coped in respack!");
    }

    public void Copy3DModelsToResorsepack(List<File> imagesItems) {
        String resourcepackPath = Reassets.myDataFolder.getAbsolutePath() + "/resourcepack/assets/minecraft/models/generated";

      //  System.out.println("--- Copy3DModelsToResorsepack ");

        for (File img : imagesItems) {

            String pathLocal = img.getPath();



            pathLocal = pathLocal.replace("\\minecraft\\", "/");
            pathLocal = RenamingImage(pathLocal);     //plugins\trashitem\reassets\items\gun.png  ====>  /trashitem/items/gun.png


            Path destinationPath = Path.of(resourcepackPath, pathLocal);


            FileHasedCopy.copyFile(img.toPath(), destinationPath);


            File imgNew = new File(destinationPath.toAbsolutePath().toString());

            //BlockbrenchSaveContract blockbrenchSaveContract = BlockBrenchConverter.Parse(imgNew);
            String pathToTextures = "generated" + new File(pathLocal).getParent().toString() + '/';
            pathToTextures = pathToTextures.replace("\\", "/");
            pathToTextures = pathToTextures.replace("/models/", "/textures/");
            pathToTextures = pathToTextures.toLowerCase();


            pathToTextures = pathToTextures.substring(0, pathToTextures.indexOf("/textures/") + "/textures/".length());
           // pathToTextures += imgNew.getName().toLowerCase().replace(".png", "");


            BlockBrenchConverter.AddPrefixToTextures(imgNew, pathToTextures);


            RegisterImageController.AddAsItem(imgNew, pathLocal, CategoryEnum.models);
        }

        OpLog.Debug("Files coped in respack!");
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

            if (img.categoryTyep == CategoryEnum.ui) continue;
            if (img.categoryTyep == CategoryEnum.sounds) continue;

            VanilaOverideFasadeModel.OverideFindResponse response = VanilaOverideFasadeModel.GetModelByRealitiveTexturePath(img.modelNameForOveride.replace(".png", "").replace(".json", ""));

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
