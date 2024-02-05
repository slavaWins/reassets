package org.slavawins.reassets.handles;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.slavawins.reassets.contracts.CategoryEnum;
import org.slavawins.reassets.contracts.ItemImageContract;
import org.slavawins.reassets.controllers.RegisterImageController;
import org.slavawins.reassets.proplugin.Lang;

import java.util.ArrayList;
import java.util.List;

public class ShowItemsHandle {
    public static void ShowList(Player sender) {


        String msg = net.md_5.bungee.api.ChatColor.GREEN + "[REASSETS] " + net.md_5.bungee.api.ChatColor.RESET + Lang.translaste("list.title", "Список ресов:");
        TextComponent message = new TextComponent(msg);


        List<String> plugins = new ArrayList<>();
        for (ItemImageContract img : RegisterImageController.images) {
            int indexOfUnderscore = img.enumName.indexOf("_");
            String plugin = img.enumName.substring(0, indexOfUnderscore);

            if(plugins.contains(plugin))continue;
            plugins.add(plugin);
        }

        for (String plugin : plugins) {
            TextComponent plug = new TextComponent("\n" + plugin);
            plug.setColor(net.md_5.bungee.api.ChatColor.AQUA);
            message.addExtra(plug);


            TextComponent btnEnum = new TextComponent("           ENUM");
            btnEnum.setBold(true);
            btnEnum.setColor(net.md_5.bungee.api.ChatColor.AQUA);
            btnEnum.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Lang.translaste("list.enum", "Copy all enum codes"))));


            String code = "";

            TextComponent inplugin = new TextComponent("");

            for (ItemImageContract img : RegisterImageController.images) {

                if (!img.enumName.startsWith(plugin+"_")) continue;

                if (!(img.categoryTyep == CategoryEnum.items || img.categoryTyep == CategoryEnum.models)) continue;
                msg = "";
                msg += "\n  " + img.enumName + " ";
                msg += img.material.toUpperCase();
                if (img.modelId >= 0) {
                    msg += "#" + img.modelId;
                } else {
                    msg += ChatColor.DARK_RED + Lang.translaste("list.not-added", "не добавлена в пак");
                    ;
                }
                TextComponent line = new TextComponent(msg);

                TextComponent btnGive = new TextComponent("  TAKE");
                btnGive.setBold(true);
                btnGive.setColor(net.md_5.bungee.api.ChatColor.AQUA);
                btnGive.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Lang.translaste("list.take", "Получить предмет"))));
                btnGive.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/give @p minecraft:" + img.material.toLowerCase() + "{CustomModelData:" + img.modelId + ",display:{Name:'[{\"text\":\"" + img.enumName + "\"}]'}} 1"));
                line.addExtra(btnGive);

                btnGive = new TextComponent("  NAME");
                btnGive.setBold(true);
                btnGive.setColor(net.md_5.bungee.api.ChatColor.AQUA);
                btnGive.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Lang.translaste("list.to-code", "Копировать название итема для кода"))));
                btnGive.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, img.enumName));
                line.addExtra(btnGive);


                code += "\n" + img.enumName +",";
                inplugin.addExtra(line);
            }

            btnEnum.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, code));
            message.addExtra(btnEnum);


            message.addExtra(inplugin);
        }
        sender.spigot().sendMessage(message);

    }
}
