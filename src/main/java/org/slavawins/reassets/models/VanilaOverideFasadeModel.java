package org.slavawins.reassets.models;

import com.google.gson.Gson;
import org.slavawins.reassets.contracts.ItemImageContract;
import org.slavawins.reassets.contracts.vanila.VnailaItemCoreModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class VanilaOverideFasadeModel {

    public static List<VanilaOverideFasadeModel> list = new ArrayList<>();

    public static VanilaOverideFasadeModel FindMaterial(String material) {
        for (VanilaOverideFasadeModel van : list) {
            if (van.material.equalsIgnoreCase(material)) return van;
        }
        return null;
    }

    public static class OverideFindResponse {
        public VanilaOverideFasadeModel fasadeModel;
        public VnailaItemCoreModel._OverrideVanila overide;
    }

    public static OverideFindResponse GetModelByRealitiveTexturePath(String realitiveTexturePath) {
        for (VanilaOverideFasadeModel van : list) {
            for (VnailaItemCoreModel._OverrideVanila overide : van.model.overrides) {
                if (overide.predicate == null) continue;
                if (overide.model.equalsIgnoreCase(realitiveTexturePath)) {
                    OverideFindResponse response = new OverideFindResponse();
                    response.fasadeModel = van;
                    response.overide = overide;
                    return response;
                }
            }
        }
        return null;
    }

    public VnailaItemCoreModel model;
    public File file;
    public String material;

    public int maxId = 0;


    public VanilaOverideFasadeModel(File fileModel, VnailaItemCoreModel _model) {
        model = _model;
        file = fileModel;

        material = fileModel.getName().replace(".json", "");

        list.add(this);


        for (VnailaItemCoreModel._OverrideVanila overide : _model.overrides) {


            if (overide.predicate == null) continue;
            maxId = Math.max(overide.predicate.custom_model_data, maxId);


        }
    }


    public static boolean WriteSingleCustomModel(ItemImageContract img) {
        Gson gson = new Gson();

        String path = ResourcepackGenerator.rootFoolder.getPath() + "/assets/minecraft/models/" + img.modelNameForOveride.replace(".png", "") + ".json";


        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }


        VnailaItemCoreModel vnailaItemCoreModel = new VnailaItemCoreModel();
        vnailaItemCoreModel.overrides = null;
        vnailaItemCoreModel.parent = "minecraft:item/generated";
        vnailaItemCoreModel.textures = new VnailaItemCoreModel.Textures();
        vnailaItemCoreModel.textures.layer0 = img.modelNameForOveride.replace(".png", "");


        try {
            file.getParentFile().mkdir();

            file.createNewFile();

            Writer writer = new FileWriter(file, false);
            gson.toJson(vnailaItemCoreModel, writer);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public static boolean Write(File file, VnailaItemCoreModel vnailaItemCoreModel) {
        Gson gson = new Gson();


        try {
            file.getParentFile().mkdir();

            file.createNewFile();

            Writer writer = new FileWriter(file, false);
            gson.toJson(vnailaItemCoreModel, writer);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static VanilaOverideFasadeModel CreateItem(String material) {

        VnailaItemCoreModel vnailaItemCoreModel = new VnailaItemCoreModel();
        vnailaItemCoreModel.textures.layer0 = "minecraft:item/" + material;


        File file = new File(ResourcepackGenerator.rootFoolder.getPath() + "/assets/minecraft/models/item/" + material + ".json");

        if (!Write(file, vnailaItemCoreModel)) return null;


        VanilaOverideFasadeModel nav = new VanilaOverideFasadeModel(file, vnailaItemCoreModel);

        return nav;
    }

}
