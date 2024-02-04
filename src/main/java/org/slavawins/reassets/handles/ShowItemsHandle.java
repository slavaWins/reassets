package org.slavawins.reassets.handles;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.slavawins.reassets.contracts.ItemImageContract;
import org.slavawins.reassets.controllers.RegisterImageController;

public class ShowItemsHandle {
    public static void ShowList(Player sender) {


        String msg = net.md_5.bungee.api.ChatColor.GREEN + "[REASSETS] " + net.md_5.bungee.api.ChatColor.RESET + " Список всех предметов: ";
        TextComponent message = new TextComponent(msg);


        for (ItemImageContract img : RegisterImageController.images) {

            msg = "";
            msg += "\n" + img.modelNameForOveride + " ";
            msg += img.material.toUpperCase();
            if (img.modelId >= 0) {
                msg += "#" + img.modelId;
            } else {
                msg += ChatColor.DARK_RED + " не добавлена в пак";
            }
            TextComponent line = new TextComponent(msg);

            TextComponent btnGive = new TextComponent("  TAKE");
            btnGive.setBold(true);
            btnGive.setColor(net.md_5.bungee.api.ChatColor.AQUA);
            btnGive.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Взять предмет")));
            btnGive.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/give @p minecraft:" + img.material.toLowerCase() + "{CustomModelData:" + img.modelId + ",display:{Name:'[{\"text\":\"" + img.enumName + "\"}]'}} 1"));
            line.addExtra(btnGive);

            message.addExtra(line);

        }
        sender.spigot().sendMessage(message);

    }
}
