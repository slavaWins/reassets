package org.slavawins.reassets.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.slavawins.reassets.Reassets;
import org.slavawins.reassets.contracts.ItemImageContract;
import org.slavawins.reassets.controllers.CreateOverideTask;
import org.slavawins.reassets.controllers.RegisterImageController;
import org.slavawins.reassets.http.Uploader;
import org.slavawins.reassets.libs.Fastcommand;

import java.io.IOException;

public class ComandListener extends Fastcommand {


    public ComandListener(String rootCommand) {
        super(rootCommand);


        CommandElemet com = new CommandElemet();
        com.subcommond = "generate";
        com.descrip = "Собрать ресруспак";
        com.event = this::GenerateCommand;
        commands.add(com);


        com = new CommandElemet();
        com.subcommond = "status";
        com.descrip = "Статус ресурспака";
        com.event = this::StatusCommand;
        commands.add(com);


        com = new CommandElemet();
        com.subcommond = "list";
        com.descrip = "Вывести список итемов";
        com.event = this::ListCommand;
        commands.add(com);


        com = new CommandElemet();
        com.subcommond = "upload";
        com.descrip = "Загрузить ресурспак на сервер и раздать игрокам";
        com.event = this::UploadCommand;
        commands.add(com);

    }


    public void ListCommand(CommandSender sender, String[] args) {

        sender.sendMessage(ChatColor.GREEN + " Список ресов:");
        String text = "";
        for (ItemImageContract img : RegisterImageController.images) {
            text += "\n" + img.modelNameForOveride + " ";
            text += img.material.toUpperCase();
            if (img.modelId >= 0) {
                text += "#" + img.modelId;
            } else {
                text += ChatColor.DARK_RED + " не добавлена в пак";
            }
        }
        sender.sendMessage(text);

    }

    public void UploadCommand(CommandSender sender, String[] args) {

        sender.sendMessage(ChatColor.GREEN + " Отправляю файл на сервре");
        try {
            Uploader.send();
        } catch (IOException e) {
            System.err.println("Error send file " + e.getMessage());
        }
        sender.sendMessage(ChatColor.GREEN + " файл отправлен");

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.setResourcePack("http://minehelper.test/uploads/xz.zip?dl=1");
        }
    }

    public void GenerateCommand(CommandSender sender, String[] args) {

        sender.sendMessage(ChatColor.GREEN + " Генерация ресурспака запущена");
        // long currentTime = System.currentTimeMillis();
        CreateOverideTask.Run();
        sender.sendMessage(ChatColor.GRAY + " Генерация выполнена. Загрузите ресурспак на сайт что бы обновить его у игроков!");

    }

    public void StatusCommand(CommandSender sender, String[] args) {

        sender.sendMessage(ChatColor.GRAY + " Статус ресурспака");
        sender.sendMessage(ChatColor.GRAY + " Плагин загрузился и проиндексировал паки за: " + (Reassets.onIndexingTime) + " сек.");
        sender.sendMessage(ChatColor.GRAY + " Найдено новых не промоделеных итемов: " + CreateOverideTask.GetCountEmpty() + " шт.");
        sender.sendMessage(ChatColor.DARK_GRAY + " Всего итемов в базе " + RegisterImageController.images.size() + " шт.");


    }
}
