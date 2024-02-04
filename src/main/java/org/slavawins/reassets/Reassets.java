package org.slavawins.reassets;

import org.bukkit.plugin.java.JavaPlugin;
import org.slavawins.reassets.handles.IndexingHandle;
import org.slavawins.reassets.listener.ComandListener;
import org.slavawins.reassets.listener.StartListener;

import java.io.File;

public final class Reassets extends JavaPlugin {

    public static File dataFolderPlugins;
    public static File myDataFolder;


    @Override
    public void onEnable() {
        // Plugin startup logic

        myDataFolder = getDataFolder();
        dataFolderPlugins = getDataFolder().getParentFile();



        ComandListener comandListener = new ComandListener("reassets");
        getCommand("reassets").setExecutor(comandListener);
        getCommand("reassets").setTabCompleter(comandListener);

        IndexingHandle.Indexing();

        getServer().getPluginManager().registerEvents(new StartListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
