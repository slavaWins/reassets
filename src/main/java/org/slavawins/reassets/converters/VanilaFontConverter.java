package org.slavawins.reassets.converters;

import com.google.gson.Gson;
import org.slavawins.reassets.contracts.vanila.VanilaFontContract;
import org.slavawins.reassets.contracts.vanila.VanilaProviderFontContract;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;

public class VanilaFontConverter {

    public static void AddProvider(File font, VanilaProviderFontContract providerFontContract) {
        VanilaFontContract data = ReadFont(font);
        if (data == null) return;
        data.providers.add(providerFontContract);

        try {
            Gson gson = new Gson();
            Writer writer = new FileWriter(font, false);
            gson.toJson(data, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static VanilaFontContract ReadFont(File font) {

        Gson gson = new Gson();
        VanilaFontContract data = null;
        try {
            data = gson.fromJson(Files.readString(font.toPath()), VanilaFontContract.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
