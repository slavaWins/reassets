package org.slavawins.reassets.handles;

import com.google.gson.Gson;
import org.slavawins.reassets.Reassets;
import org.slavawins.reassets.contracts.CategoryEnum;
import org.slavawins.reassets.contracts.ItemImageContract;
import org.slavawins.reassets.contracts.vanila.VanilaFontContract;
import org.slavawins.reassets.contracts.vanila.VanilaProviderFontContract;
import org.slavawins.reassets.controllers.RegisterImageController;
import org.slavawins.reassets.converters.VanilaFontConverter;
import org.slavawins.reassets.helpers.UnicodeHelper;
import org.slavawins.reassets.integration.ResourceExtractor;
import org.slavawins.reassets.models.ResourcepackGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FontMappingHandle {

    public static List<String> chars = new ArrayList<>();
    public static List<String> imagesExist = new ArrayList<>();

    public static Map<String, String> uis = new HashMap<>();

    public static void AddSearchIndex(String file, String symol) {
        file = file.replace(".png", "");
        file = file.replace("minecraft:", "");
        file = file.replace("generated/", "");
        uis.put(file, symol);
    }

    public static void Indexing() {
        File file = new File(ResourcepackGenerator.rootFoolder, "/assets/minecraft/font/reassets.json");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            ResourceExtractor.extract(Reassets.getInstance(), "resourcepack/assets/minecraft/font");
            return;
        }

        VanilaFontContract font = VanilaFontConverter.ReadFont(file);
        for (VanilaProviderFontContract prov : font.providers) {

            AddSearchIndex(prov.file, prov.chars.get(0));
            chars.add(prov.chars.get(0));
            imagesExist.add(prov.file);
        }

    }


    public static void Addder() {
        File file = new File(ResourcepackGenerator.rootFoolder, "/assets/minecraft/font/reassets.json");
        VanilaFontContract font = VanilaFontConverter.ReadFont(file);
        if (font == null) return;

        int unicodeIteration = chars.size();
        //unicodeIteration = 0;

        for (ItemImageContract img : RegisterImageController.images) {
            if (img.categoryTyep != CategoryEnum.ui) continue;


            String name = "minecraft:" + img.modelNameForOveride;


            if (imagesExist.contains(name)) {
                continue;
            }


            String code = UnicodeHelper.GenIteration(unicodeIteration);
            while (chars.contains(code)) {
                unicodeIteration++;
                code = UnicodeHelper.GenIteration(unicodeIteration);
            }

            AddSearchIndex(name, code);
            chars.add(code);
            VanilaProviderFontContract providerFontContract = new VanilaProviderFontContract();
            providerFontContract.chars.add(code);
            providerFontContract.file = name;

            font.providers.add(providerFontContract);
        }

        Gson gson = new Gson();
        String content = gson.toJson(font);
        content = content.replace("\\\\u", "\\u");

        try {

            Writer writer = new FileWriter(file, false);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
