package org.slavawins.reassets.contracts.vanila;

import java.util.ArrayList;
import java.util.List;

public class VnailaItemCoreModel {
   // //@SerializedName("parent")
    public String parent = "minecraft:item/generated";

    //@SerializedName("textures")
    public Textures textures = new Textures();

    //@SerializedName("overrideVanilas")
    public List<_OverrideVanila> overrides =new ArrayList<>();


    //private File _file;

    public static class Textures {
        //@SerializedName("layer0")
        public String layer0 ;

    }

    public static class _OverrideVanila {
        public Predicate predicate;

        public String model;

    }

    public static class Predicate {
        //@SerializedName("custom_model_data")
        public int custom_model_data;

    }
}