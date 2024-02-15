package net.nimbus.lokiplayerclasses.commands.completers;

import net.nimbus.lokiplayerclasses.core.classes.PlayerClass;
import net.nimbus.lokiplayerclasses.core.classes.PlayerClasses;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LPCCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> result = new ArrayList<>();
        switch (args.length) {
            case 1: {
                List<String> options = List.of("choose", "setClass", "giveAttribute");
                for (String option : options) {
                    if (option.toLowerCase().startsWith(args[0].toLowerCase())) result.add(option);
                }
                return result;
            }
            case 2 : {
                switch (args[0].toLowerCase()) {
                    case "setclass" :
                    case "giveattribute" :
                    case "choose" : {
                        List<String> options = Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
                        for(String option : options) {
                            if(option.toLowerCase().startsWith(args[1])) result.add(option);
                        }
                        return result;
                    }
                }
            }
            case 3 : {
                switch (args[0].toLowerCase()) {
                    case "setclass" : {
                        List<String> options = PlayerClasses.getAll().stream().map(PlayerClass::getId).toList();
                        for(String option : options) {
                            if(option.toLowerCase().startsWith(args[2])) result.add(option);
                        }
                        return result;
                    }
                    case "giveattribute" : {
                        List<String> options = List.of("1", "2", "3");
                        for(String option : options) {
                            if(option.toLowerCase().startsWith(args[2])) result.add(option);
                        }
                        return result;
                    }
                }
            }
        }
        return result;
    }
}
