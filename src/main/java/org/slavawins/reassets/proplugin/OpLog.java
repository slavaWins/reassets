package org.slavawins.reassets.proplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.slavawins.reassets.ConfigHelper;

public class OpLog {

    public static void SayOp(String text) {
        text = ChatColor.BLUE + "[REASSETS]         " + text;

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.sendMessage(text);
        }
    }

    public static void Debug(String text) {
        if (!ConfigHelper.GetConfig().getBoolean("debug", false)) return;

        SayOp(text);
        System.out.println(text);
    }
}
