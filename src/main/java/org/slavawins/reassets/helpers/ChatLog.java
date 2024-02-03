package org.slavawins.reassets.helpers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ChatLog {
    public static void Write(String text) {
        text = ChatColor.BLUE + "[REASSETS]         " + text;
        Bukkit.broadcastMessage(text);
        System.out.println(text);
    }
}
