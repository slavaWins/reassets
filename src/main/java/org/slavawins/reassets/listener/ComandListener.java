package org.slavawins.reassets.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.slavawins.reassets.ConfigHelper;
import org.slavawins.reassets.contracts.ItemImageContract;
import org.slavawins.reassets.contracts.UploadResponseContract;
import org.slavawins.reassets.controllers.CreateOverideTask;
import org.slavawins.reassets.controllers.RegisterImageController;
import org.slavawins.reassets.handles.IndexingHandle;
import org.slavawins.reassets.handles.ShowItemsHandle;
import org.slavawins.reassets.http.FolderZipper;
import org.slavawins.reassets.http.Uploader;
import org.slavawins.reassets.lang.LangHelper;
import org.slavawins.reassets.libs.Fastcommand;
import org.slavawins.reassets.models.ResourcepackGenerator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ComandListener extends Fastcommand {


    public ComandListener(String rootCommand) {
        super(rootCommand);

        onlyOp = true;

        CommandElemet com = new CommandElemet();
        com.subcommond = "generate";
        com.descrip = LangHelper.translaste("cmd.help.generate", "Собрать ресруспак");
        com.event = this::GenerateCommand;
        commands.add(com);


        com = new CommandElemet();
        com.subcommond = "status";
        com.descrip = LangHelper.translaste("cmd.help.status", "Статус ресурспака");
        com.event = this::StatusCommand;
        commands.add(com);


        com = new CommandElemet();
        com.subcommond = "list";
        com.descrip = LangHelper.translaste("cmd.help.list", "Вывести список итемов");
        com.event = this::ListCommand;
        commands.add(com);


        com = new CommandElemet();
        com.subcommond = "upload";
        com.descrip = LangHelper.translaste("cmd.help.upload", "Загрузить ресурспак на сервер и раздать игрокам");
        com.event = this::UploadCommand;
        commands.add(com);


        com = new CommandElemet();
        com.subcommond = "reload";
        com.descrip = LangHelper.translaste("cmd.help.reload", "Перезагрузить конфиг");
        com.event = this::ReloadCommand;
        commands.add(com);

    }


    public void ListCommand(CommandSender sender, String[] args) {

        if (sender instanceof Player) {
            ShowItemsHandle.ShowList((Player) sender);
            return;
        }
        sender.sendMessage(ChatColor.GREEN + LangHelper.translaste("list.title", "Список ресов:"));
        String text = "";
        for (ItemImageContract img : RegisterImageController.images) {
            text += "\n" + img.enumName + " ";
            text += img.material.toUpperCase();
            if (img.modelId >= 0) {
                text += "#" + img.modelId;
            } else {
                text += ChatColor.DARK_RED + LangHelper.translaste("list.not-added", "не добавлена в пак");
            }
        }
        sender.sendMessage(text);

    }


    public void ReloadCommand(CommandSender sender, String[] args) {
        ConfigHelper.Reload();

        sender.sendMessage(ChatColor.GREEN + LangHelper.translaste("reloaded", "Конфиг перезагружен"));
    }

    public void UploadCommand(CommandSender sender, String[] args) {


        if (!ConfigHelper.GetConfig().getBoolean("upload-enabled", false)) {
            sender.sendMessage(ChatColor.RED + LangHelper.translaste("info.upload-enabled", "upload-enabled: false. Включите в конфиге отправку!"));
            return;
        }

        FolderZipper.Arhivate(ResourcepackGenerator.rootFoolder, ResourcepackGenerator.rootFoolder.getParentFile().getAbsolutePath() + "/resourcepack.zip");

        UploadResponseContract response = UploadResponseContract.Error("Error uploading");
        try {
            response = Uploader.SendRP();


        } catch (IOException e) {
            System.err.println("Error send file " + e.getMessage());
        }

        if (!response.success) {
            sender.sendMessage(ChatColor.RED + LangHelper.translaste("info.upload-enabled", "Ошибка отправки конфгиа: ") + response.message);
            return;
        }

        ConfigHelper.GetConfig().set("resource-pack-url", response.url);
        ConfigHelper.Save();

        sender.sendMessage(ChatColor.GREEN + LangHelper.translaste("upload-ok", " файл ресурспакак успешно отправлен на хостинг"));

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.setResourcePack(ConfigHelper.GetConfig().getString("resource-pack-url"));
        }
    }

    public void GenerateCommand(CommandSender sender, String[] args) {


        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
        String formattedDate = "resourcepack_" + dateFormat.format(currentDate) + ".zip";
        sender.sendMessage(ChatColor.GREEN + LangHelper.translaste("backup.start", "Делаем бэкап ресурспака"));
        FolderZipper.Arhivate(ResourcepackGenerator.rootFoolder, ResourcepackGenerator.rootFoolder.getParentFile().getAbsolutePath() + "/backup/" + formattedDate);


        IndexingHandle.Clear();
        IndexingHandle.Indexing();

        sender.sendMessage(ChatColor.GRAY + LangHelper.translaste("generate-amount-to-adding", " Будет добавлено итемов: ") + CreateOverideTask.GetCountEmpty() + " X");

        CreateOverideTask.Run();
        sender.sendMessage(ChatColor.GREEN + LangHelper.translaste("generate-ok", " Генерация выполнена. Загрузите ресурспак на сайт что бы обновить его у игроков! Или введите команду отправки ресурспака.") + " /reassets upload");


        IndexingHandle.Clear();
        IndexingHandle.Indexing();

    }

    public void StatusCommand(CommandSender sender, String[] args) {

        sender.sendMessage(ChatColor.GRAY + LangHelper.translaste("status.title", "Статус ресурспака"));
        sender.sendMessage(ChatColor.GRAY + LangHelper.translaste("status.time", "Плагин загрузился и проиндексировал паки за: ") + (IndexingHandle.onIndexingTime) + " sec.");
        sender.sendMessage(ChatColor.GRAY + LangHelper.translaste("status.new-items", "Найдено новых не промоделеных итемов:") + CreateOverideTask.GetCountEmpty() + " X");
        sender.sendMessage(ChatColor.RESET + LangHelper.translaste("status.amount", "Всего итемов в базе: ") + RegisterImageController.images.size() + " X");


    }
}
