package org.slavawins.reassets;

import org.bukkit.plugin.java.JavaPlugin;
import org.slavawins.reassets.converters.Scaner;
import org.slavawins.reassets.http.Uploader;
import org.slavawins.reassets.listener.ComandListener;
import org.slavawins.reassets.listener.StartListener;
import org.slavawins.reassets.models.ResourcepackGenerator;
import org.slavawins.reassets.repositories.RawImagesRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public final class Reassets extends JavaPlugin {

    public static File dataFolderPlugins;
    public static File myDataFolder;
    public static float onIndexingTime = 0;


    @Override
    public void onEnable() {
        // Plugin startup logic

        myDataFolder = getDataFolder();
        dataFolderPlugins = getDataFolder().getParentFile();



        ComandListener comandListener = new ComandListener("reassets");
        getCommand("reassets").setExecutor(comandListener);
        getCommand("reassets").setTabCompleter(comandListener);

        long currentTime = System.currentTimeMillis();
        Scaner.Init(getDataFolder());
        ResourcepackGenerator resourcepackGenerator = new ResourcepackGenerator();
        resourcepackGenerator.CreateStructureResoursePack();
        resourcepackGenerator.CopyRawImagesToResorsepack(RawImagesRepository.fileList);
        resourcepackGenerator.MappingOverides();
        resourcepackGenerator.IndexingPivots();
        onIndexingTime = (System.currentTimeMillis() - currentTime) / 1000f;



        getServer().getPluginManager().registerEvents(new StartListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
