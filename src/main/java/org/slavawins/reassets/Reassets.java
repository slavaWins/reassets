package org.slavawins.reassets;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.slavawins.reassets.handles.IndexingHandle;
import org.slavawins.reassets.proplugin.OpLog;
import org.slavawins.reassets.integration.ResourceExtractor;
import org.slavawins.reassets.proplugin.Lang;
import org.slavawins.reassets.listener.ComandListener;
import org.slavawins.reassets.listener.StartListener;

import java.io.File;

public final class Reassets extends JavaPlugin {

    public static File dataFolderPlugins;
    public static File myDataFolder;

    public static Plugin getInstance() {
        return instance;
    }

    private static Plugin instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        myDataFolder = getDataFolder();
        dataFolderPlugins = getDataFolder().getParentFile();


        ConfigHelper.Init(getDataFolder());

        ResourceExtractor.extract(this, "lang");
        Lang.loadLanguageFile(ConfigHelper.GetConfig().getString("lang", "en"));


        ComandListener comandListener = new ComandListener("reassets");
        getCommand("reassets").setExecutor(comandListener);
        getCommand("reassets").setTabCompleter(comandListener);

        IndexingHandle.Indexing();


        OpLog.SayOp(Lang.translaste("on-enable", "Плагин успешно загрузился"));

        getServer().getPluginManager().registerEvents(new StartListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
