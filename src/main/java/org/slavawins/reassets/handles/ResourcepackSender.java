package org.slavawins.reassets.handles;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.slavawins.reassets.configs.ConfigHelper;

public class ResourcepackSender {

    public static void toAllPlayers() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            toPlayer(player);
        }
    }

    public static void toPlayer(Player player) {
        if (ConfigHelper.GetConfig().getString("resource-pack-url").isEmpty()) return;
        if (!ConfigHelper.GetConfig().getBoolean("resource-pack-send-enabled")) return;

        player.setResourcePack(ConfigHelper.GetConfig().getString("resource-pack-url"));
    }
}
