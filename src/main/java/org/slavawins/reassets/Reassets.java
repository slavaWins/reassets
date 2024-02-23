package org.slavawins.reassets;

import org.bukkit.plugin.java.JavaPlugin;
import org.slavawins.reassets.configs.ConfigHelper;
import org.slavawins.reassets.configs.ConfigNames;
import org.slavawins.reassets.handles.IndexingHandle;
import org.slavawins.reassets.integration.ResourceExtractor;
import org.slavawins.reassets.listener.ComandListener;
import org.slavawins.reassets.listener.InteractItemListener;
import org.slavawins.reassets.listener.StartListener;
import org.slavawins.reassets.proplugin.Lang;

import java.io.File;

public final class Reassets extends JavaPlugin {

    public static File dataFolderPlugins;
    public static File myDataFolder;

    public static JavaPlugin getInstance() {
        return instance;
    }

    private static JavaPlugin instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        myDataFolder = getDataFolder();
        dataFolderPlugins = getDataFolder().getParentFile();


        if (!myDataFolder.exists()) {
            myDataFolder.mkdirs();
            ResourceExtractor.extract(this, "reassets");
        }

        ConfigHelper.Init(getDataFolder());
        ConfigNames.Init(getDataFolder());


        ResourceExtractor.extract(this, "lang");
        Lang.init(this,ConfigHelper.GetConfig().getString("lang", "en"));


        ComandListener comandListener = new ComandListener("reassets");
        getCommand("reassets").setExecutor(comandListener);
        getCommand("reassets").setTabCompleter(comandListener);

        IndexingHandle.Indexing();


      //  OpLog.SayOp(Lang.translaste("on-enable", "Плагин успешно загрузился"));

        getServer().getPluginManager().registerEvents(new StartListener(), this);
        getServer().getPluginManager().registerEvents(new InteractItemListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
