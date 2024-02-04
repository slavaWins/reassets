package org.slavawins.reassets.contracts;

import org.bukkit.Material;

import java.io.File;

public class ItemImageContract {

    public File file;
    public String enumName;
    public String material;
    public int modelId = -1;
    public String modelNameForOveride;
    public CategoryEnum categoryTyep;

    public void sout() {
        if (material == null) {
            System.out.println(enumName + " - " + modelNameForOveride);
        } else {
            System.out.println(enumName + " - " + material + ":" + modelId);
        }

    }

}
