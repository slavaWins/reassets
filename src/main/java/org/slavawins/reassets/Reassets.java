package org.slavawins.reassets;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.slavawins.reassets.controllers.CreateOverideTask;
import org.slavawins.reassets.converters.MappingOverides;
import org.slavawins.reassets.converters.Scaner;
import org.slavawins.reassets.models.ResourcepackGenerator;
import org.slavawins.reassets.repositories.RawImagesRepository;

import java.io.File;

public final class Reassets extends JavaPlugin {

    public static File dataFolderPlugins;
    public static File myDataFolder;

    @Override
    public void onEnable() {
        // Plugin startup logic

        myDataFolder = getDataFolder();
        dataFolderPlugins = getDataFolder().getParentFile();



        Scaner.Init(getDataFolder());


        ResourcepackGenerator resourcepackGenerator = new ResourcepackGenerator();

       // MappingOverides.Mapping(resourcepackGenerator.getFoolder());
        Bukkit.shutdown();


        resourcepackGenerator.CreateStructureResoursePack();
        resourcepackGenerator.CopyRawImagesToResorsepack(RawImagesRepository.fileList);
        resourcepackGenerator.MappingOverides();
        resourcepackGenerator.IndexingEmptyTextures();
        CreateOverideTask.Run();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
