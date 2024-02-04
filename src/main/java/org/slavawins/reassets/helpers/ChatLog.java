package org.slavawins.reassets.helpers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.slavawins.reassets.ConfigHelper;

public class ChatLog {

    public static void Say(String text) {
        text = ChatColor.BLUE + "[REASSETS]         " + text;
        Bukkit.broadcastMessage(text);
    }

    public static void Debug(String text) {
        ConfigHelper.GetConfig().getBoolean("debug",false);
        text = ChatColor.BLUE + "[REASSETS]         " + text;
        Bukkit.broadcastMessage(text);
        System.out.println(text);
    }
}
