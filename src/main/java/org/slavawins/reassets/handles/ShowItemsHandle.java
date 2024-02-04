package org.slavawins.reassets.handles;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.slavawins.reassets.contracts.ItemImageContract;
import org.slavawins.reassets.controllers.RegisterImageController;
import org.slavawins.reassets.lang.LangHelper;

public class ShowItemsHandle {
    public static void ShowList(Player sender) {


        String msg = net.md_5.bungee.api.ChatColor.GREEN + "[REASSETS] " + net.md_5.bungee.api.ChatColor.RESET + LangHelper.translaste("list.title", "Список ресов:");
        TextComponent message = new TextComponent(msg);


        for (ItemImageContract img : RegisterImageController.images) {

            msg = "";
            msg += "\n" + img.enumName + " ";
            msg += img.material.toUpperCase();
            if (img.modelId >= 0) {
                msg += "#" + img.modelId;
            } else {
                msg += ChatColor.DARK_RED + LangHelper.translaste("list.not-added", "не добавлена в пак");
                ;
            }
            TextComponent line = new TextComponent(msg);

            TextComponent btnGive = new TextComponent("  TAKE");
            btnGive.setBold(true);
            btnGive.setColor(net.md_5.bungee.api.ChatColor.AQUA);
            btnGive.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(LangHelper.translaste("list.take", "Получить предмет"))));
            btnGive.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/give @p minecraft:" + img.material.toLowerCase() + "{CustomModelData:" + img.modelId + ",display:{Name:'[{\"text\":\"" + img.enumName + "\"}]'}} 1"));
            line.addExtra(btnGive);

            btnGive = new TextComponent("  NAME");
            btnGive.setBold(true);
            btnGive.setColor(net.md_5.bungee.api.ChatColor.AQUA);
            btnGive.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(LangHelper.translaste("list.to-code", "Копировать название итема для кода"))));
            btnGive.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, img.enumName));
            line.addExtra(btnGive);


            message.addExtra(line);

        }
        sender.spigot().sendMessage(message);

    }
}
