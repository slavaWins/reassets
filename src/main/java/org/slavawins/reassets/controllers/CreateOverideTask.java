package org.slavawins.reassets.controllers;

import org.bukkit.Material;
import org.slavawins.reassets.contracts.CategoryEnum;
import org.slavawins.reassets.contracts.ItemImageContract;
import org.slavawins.reassets.models.VanilaOverideFasadeModel;
import org.slavawins.reassets.contracts.vanila.VnailaItemCoreModel;
import org.slavawins.reassets.proplugin.OpLog;

import java.util.ArrayList;
import java.util.List;

public class CreateOverideTask {


    public static String GetMaterialFromNameFile(ItemImageContract contract) {
        String filename = contract.file.getName();

        if (contract.isBlock) {
            return Material.PURPLE_STAINED_GLASS.toString().toLowerCase();
        }

        if (filename.indexOf("_eat.") > 0) return "apple";
        return "bone";
    }


    static List<String> materials = new ArrayList<>();
    static List<ItemImageContract> imgs = new ArrayList<>();

    public static void AddTask(ItemImageContract img) {

        img.material = GetMaterialFromNameFile(img);
        imgs.add(img);

        if (!materials.contains(img.material)) {
            materials.add(img.material);
        }
    }

    public static void Run() {

        for (String material : materials) {

            OpLog.Debug("------ MATERIAL RENDER " + material);

            VanilaOverideFasadeModel vanilaOverideFasadeModel = VanilaOverideFasadeModel.FindMaterial(material);

            if (vanilaOverideFasadeModel == null) {
                vanilaOverideFasadeModel = VanilaOverideFasadeModel.CreateItem(material);
            }

            for (ItemImageContract img : imgs) {

                vanilaOverideFasadeModel.maxId++;

                VnailaItemCoreModel._OverrideVanila over = new VnailaItemCoreModel._OverrideVanila();
                over.predicate = new VnailaItemCoreModel.Predicate();
                over.predicate.custom_model_data = vanilaOverideFasadeModel.maxId;
                over.model = img.modelNameForOveride.replace(".png", "").replace(".json", "");

                vanilaOverideFasadeModel.model.overrides.add(over);

                VanilaOverideFasadeModel.Write(vanilaOverideFasadeModel.file, vanilaOverideFasadeModel.model);


                if (img.categoryTyep == CategoryEnum.items) {
                    VanilaOverideFasadeModel.WriteSingleCustomModel(img);
                } else {
                    //VanilaOverideFasadeModel.WriteSingleCustomModel(img);
                }

            }

        }

        Clear();

    }

    public static int GetCountEmpty() {
        return imgs.size();
    }


    public static void Clear() {
        imgs.clear();
        materials.clear();
    }
}
