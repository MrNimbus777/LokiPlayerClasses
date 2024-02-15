package net.nimbus.lokiplayerclasses.commands.executors;

import net.nimbus.lokiplayerclasses.LPClasses;
import net.nimbus.lokiplayerclasses.Utils;
import net.nimbus.lokiplayerclasses.core.classes.PlayerClass;
import net.nimbus.lokiplayerclasses.core.classes.PlayerClasses;
import net.nimbus.lokiplayerclasses.core.players.LPlayer;
import net.nimbus.lokiplayerclasses.core.players.LPlayers;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LPCExe implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player p) if(!p.hasPermission("lpc.admin")) {
            p.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.no-permission")));
            return true;
        }
        if(args.length == 0) {
            sender.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.lpc.usage")));
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "choose" : {
                if(args.length == 1) {
                    sender.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.lpc.choose.usage")));
                    return true;
                }
                Player p = Bukkit.getPlayer(args[1]);
                if(p == null) {
                    sender.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.lpc.no-player")));
                    return true;
                }
                LPlayers.openAGUI(p);
                return true;
            }
            case "setclass" : {
                if(args.length == 2) {
                    sender.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.lpc.setClass.usage")));
                    return true;
                }
                Player p = Bukkit.getPlayer(args[1]);
                if(p == null) {
                    sender.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.lpc.no-player")));
                    return true;
                }
                PlayerClass playerClass = PlayerClasses.get(args[2]);
                if(playerClass == null){
                    if(args[2].equalsIgnoreCase("default")) {
                        LPlayers.get(p).setPlayerClass(null);
                        sender.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.lpc.setClass.success").
                                replace("%player%", p.getName()).
                                replace("%class%", "Default")));
                        return true;
                    }
                    sender.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.lpc.setClass.no-class")));
                    return true;
                }
                LPlayers.get(p).setPlayerClass(playerClass);
                sender.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.lpc.setClass.success").
                        replace("%player%", p.getName()).
                        replace("%class%", playerClass.getName())));
                return true;
            }
            case "giveattribute" : {
                if(args.length == 2) {
                    sender.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.lpc.giveAttribute.usage")));
                    return true;
                }
                Player player = Bukkit.getPlayer(args[1]);
                if(player == null) {
                    sender.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.no-player")));
                    return true;
                }
                try {
                    int amount = Integer.parseInt(args[2]);
                    LPlayer lp = LPlayers.get(player);
                    lp.setPoints(lp.getPoints() + amount);
                    sender.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.lpc.giveAttribute.success").
                            replace("%amount%", amount+"").
                            replace("%player%", player.getName())));
                    return true;
                } catch (Exception e) {
                    sender.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.lpc.giveAttribute.nan").replace("%nan%", args[2])));
                    return true;
                }
            }
            default: {
                sender.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.lpc.usage")));
                return true;
            }
        }
    }
}
