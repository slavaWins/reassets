package org.slavawins.reassets.libs;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Fastcommand implements CommandExecutor, TabCompleter {

    public boolean onlyOp = true;
    private final String rootCommand;
    public List<CommandElemet> commands = new ArrayList<>();


    public Fastcommand(String rootCommand) {

        this.rootCommand = rootCommand;


        CommandElemet com = new CommandElemet();
        com.subcommond = "help";
        com.descrip = "Help";
        com.event = this::sendHelpCommand;
        commands.add(com);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {


        if (!sender.isOp()) return null;

        if (!command.getName().equalsIgnoreCase(rootCommand)) return null;
        List<String> completions = new ArrayList<>();


        if (args.length == 1) {
            for (CommandElemet com : commands) {
                completions.add(com.subcommond);
            }
            return completions;
        }

        return null;
    }

    public static class CommandElemet {
        public String subcommond = "list";
        public String descrip = "Описание команды";
        public List<String> arguments = new ArrayList<>();

        public BiConsumer<CommandSender, String[]> event;
    }

    public void sendHelpCommand(CommandSender sender, String[] args) {
        String text = ChatColor.BLUE + "[reassets] +" + ChatColor.GOLD + " Helps:";
        for (CommandElemet com : commands) {
            text += "\n" + ChatColor.GOLD + "/" + rootCommand + " " + com.subcommond;
            for (String arg : com.arguments) {
                text += " <" + arg + ">";
            }
            text += ChatColor.GRAY + "   " + com.descrip;
        }
        sender.sendMessage(text);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp() && onlyOp) return false;


        if (!label.equalsIgnoreCase(rootCommand)) return false;

        if (args.length == 0) {
            sendHelpCommand(sender, args);
            return true;
        }

        for (CommandElemet com : commands) {
            if (!com.subcommond.equalsIgnoreCase(args[0])) continue;
            if (com.arguments.size() != args.length - 1) continue;

            String[] postArgs = new String[args.length - 1];
            for (int i = 1; i < args.length; i++) postArgs[i] = args[i];

            com.event.accept(sender, postArgs);
            return true;
        }

        return false;
    }
}
