package org.slavawins.reassets.handles;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.slavawins.reassets.contracts.CategoryEnum;
import org.slavawins.reassets.contracts.ItemImageContract;
import org.slavawins.reassets.contracts.vanila.VanilaFontContract;
import org.slavawins.reassets.contracts.vanila.VanilaProviderFontContract;
import org.slavawins.reassets.contracts.vanila.VanilaSoundContract;
import org.slavawins.reassets.controllers.RegisterImageController;
import org.slavawins.reassets.converters.VanilaFontConverter;
import org.slavawins.reassets.helpers.UnicodeHelper;
import org.slavawins.reassets.models.ResourcepackGenerator;
import org.slavawins.reassets.repositories.RawImagesRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoundMappingHandle {

    public static List<String> soundsExist = new ArrayList<>();

    public static Map<String, String> sounds = new HashMap<>();

    public static void AddSearchIndex(String file, String symol) {
        file = file.replace(".ogg", "");
        file = file.replace("minecraft:", "");
        file = file.replace("generated/", "");
        sounds.put(file, symol);
    }


    public static Map<String, VanilaSoundContract> Parse(File soundFile) {

        Gson gson = new Gson();
        Map<String, VanilaSoundContract> data = new HashMap<>();

        if (!soundFile.exists()) return data;
        try {
            TypeToken<Map<String, VanilaSoundContract>> typeToken = new TypeToken<Map<String, VanilaSoundContract>>() {
            };

            data = gson.fromJson(Files.readString(soundFile.toPath()), typeToken.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void Indexing() {
        File file = new File(ResourcepackGenerator.rootFoolder, "/assets/minecraft/sounds.json");

        for (Map.Entry<String, VanilaSoundContract> prov : Parse(file).entrySet()) {
            AddSearchIndex(prov.getKey(), prov.getValue().sounds.get(0));
            soundsExist.add(prov.getKey());
        }
    }


    public static void Addder() {
        File file = new File(ResourcepackGenerator.rootFoolder, "/assets/minecraft/sounds.json");

        Map<String, VanilaSoundContract> soundPack = Parse(file);



        for (ItemImageContract img : RegisterImageController.images) {
            if (img.categoryTyep != CategoryEnum.sounds) continue;


            String name = img.modelNameForOveride;
            name = name.replace("generated/", "");
            name = name.replace("//", "/");
            name = name.replace(".ogg", "");

            String localPath = name.toLowerCase();

            name = name.replace("/", ".").toLowerCase();


            if (soundsExist.contains(name)) {
                continue;
            }

            soundPack.put(name, new VanilaSoundContract(localPath));

        }


        Gson gson = new Gson();
        String content = gson.toJson(soundPack);


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
