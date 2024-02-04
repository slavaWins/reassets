package org.slavawins.reassets.converters;

import com.google.gson.Gson;
import org.slavawins.reassets.contracts.blockbrench.BlockbrenchSaveContract;
import org.slavawins.reassets.contracts.vanila.VnailaItemCoreModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class BlockBrenchConverter {

    public static void AddPrefixToTextures(File file, String prefixPath) {
        BlockbrenchSaveContract blockbrenchSaveContract = Parse(file);

        if (blockbrenchSaveContract == null) return;
        try {
            String content = Files.readString(file.toPath());

            for (Map.Entry<String, String> entry : blockbrenchSaveContract.textures.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
/*
                System.out.println("AddPrefixToTextures: ");
                System.out.println("----  " + key + " = " + value);
                System.out.println("----  ==> " + prefixPath + value);*/
                content = content.replace("\"" + value + "\"", "\"" + prefixPath + value + "\"");
            }

            Writer writer = new FileWriter(file, false);
            writer.write(content);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static BlockbrenchSaveContract Parse(File file) {

        Gson gson = new Gson();
        BlockbrenchSaveContract mod = null;
        try {
            mod = gson.fromJson(Files.readString(file.toPath()), BlockbrenchSaveContract.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mod;
    }

}
