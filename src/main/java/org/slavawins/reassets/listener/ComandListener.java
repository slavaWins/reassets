package org.slavawins.reassets.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.slavawins.reassets.contracts.ItemImageContract;
import org.slavawins.reassets.controllers.CreateOverideTask;
import org.slavawins.reassets.controllers.RegisterImageController;
import org.slavawins.reassets.handles.IndexingHandle;
import org.slavawins.reassets.handles.ShowItemsHandle;
import org.slavawins.reassets.http.FolderZipper;
import org.slavawins.reassets.http.Uploader;
import org.slavawins.reassets.libs.Fastcommand;
import org.slavawins.reassets.models.ResourcepackGenerator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ComandListener extends Fastcommand {


    public ComandListener(String rootCommand) {
        super(rootCommand);

        onlyOp =true;

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

        if(sender instanceof  Player){

            ShowItemsHandle.ShowList((Player)sender);
            return;
        }
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


        FolderZipper.Arhivate(ResourcepackGenerator.rootFoolder, ResourcepackGenerator.rootFoolder.getParentFile().getAbsolutePath() + "/resourcepack.zip");

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


        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
        String formattedDate = "resourcepack_" + dateFormat.format(currentDate) + ".zip";
        sender.sendMessage(ChatColor.GREEN + " Делаем бэкап ресурспака");
        FolderZipper.Arhivate(ResourcepackGenerator.rootFoolder, ResourcepackGenerator.rootFoolder.getParentFile().getAbsolutePath() + "/backup/" + formattedDate);

        IndexingHandle.Clear();
        IndexingHandle.Indexing();

        sender.sendMessage(ChatColor.GRAY + " Будет добавлено итемов: " + CreateOverideTask.GetCountEmpty() + " шт.");
        sender.sendMessage(ChatColor.GRAY + " Генерация ресурспака запущена");

        // long currentTime = System.currentTimeMillis();
        CreateOverideTask.Run();
        sender.sendMessage(ChatColor.GREEN + " Генерация выполнена. Загрузите ресурспак на сайт что бы обновить его у игроков!");

    }

    public void StatusCommand(CommandSender sender, String[] args) {

        sender.sendMessage(ChatColor.GRAY + " Статус ресурспака");
        sender.sendMessage(ChatColor.GRAY + " Плагин загрузился и проиндексировал паки за: " + (IndexingHandle.onIndexingTime) + " сек.");
        sender.sendMessage(ChatColor.GRAY + " Найдено новых не промоделеных итемов: " + CreateOverideTask.GetCountEmpty() + " шт.");
        sender.sendMessage(ChatColor.RESET + " Всего итемов в базе " + RegisterImageController.images.size() + " шт.");


    }
}
