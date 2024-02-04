package org.slavawins.reassets.converters;

import com.google.gson.Gson;
import org.slavawins.reassets.models.VanilaOverideFasadeModel;
import org.slavawins.reassets.contracts.vanila.VnailaItemCoreModel;
import org.slavawins.reassets.proplugin.OpLog;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class VanilaParser {

    public static List<VanilaOverideFasadeModel> Parse(File materialItems) {



        List<VanilaOverideFasadeModel> result = new ArrayList<>();
        File[] files = materialItems.listFiles();
        for (File matFile : files) {

            try {

                OpLog.Debug(matFile.getName());
                Reader reader = new FileReader(matFile);
                Gson gson = new Gson();
                VnailaItemCoreModel vnailaItemCoreModel = gson.fromJson(reader, VnailaItemCoreModel.class);
               // vnailaItemCoreModel._file = matFile;
                result.add(new VanilaOverideFasadeModel(matFile,vnailaItemCoreModel));


            } catch (IOException e) {

                System.out.println("----- [reassets] Error file loading #244");
                e.printStackTrace();
            }
        }
        return result;
    }
}
