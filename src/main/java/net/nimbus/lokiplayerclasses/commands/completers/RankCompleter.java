package net.nimbus.lokiplayerclasses.commands.completers;

import net.nimbus.lokiplayerclasses.core.ranks.Rank;
import net.nimbus.lokiplayerclasses.core.ranks.Ranks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class RankCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> result = new ArrayList<>();
        switch (args.length) {
            case 0 : return result;
            case 1 : {
                List<String> options = List.of("upgrade", "info", "progress", "list");
                for(String option : options) {
                    if(option.startsWith(args[0].toLowerCase())) result.add(option);
                }
                return result;
            }
            case 2 : {
                if(args[0].equalsIgnoreCase("info")) {
                    List<String> options = Ranks.getAll().stream().map(Rank::getId).toList();
                    for(String option : options) {
                        if(option.startsWith(args[1].toLowerCase())) result.add(option);
                    }
                }
                return result;
            }
        }
        return result;
    }
}
