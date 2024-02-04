package org.slavawins.reassets.contracts.vanila;

import java.util.ArrayList;
import java.util.List;

public class VanilaAtlasContract {

    public List<SourcesVanilaAtlasContract> sources = new ArrayList<>();


    public static class SourcesVanilaAtlasContract {
        public String type = "directory";
        public String source = "generated";
        public String prefix = "generated/";
    }
}
