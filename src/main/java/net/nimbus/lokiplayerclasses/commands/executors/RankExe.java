package net.nimbus.lokiplayerclasses.commands.executors;

import net.md_5.bungee.api.ChatColor;
import net.nimbus.lokiplayerclasses.LPClasses;
import net.nimbus.lokiplayerclasses.Utils;
import net.nimbus.lokiplayerclasses.Vars;
import net.nimbus.lokiplayerclasses.core.players.LPlayer;
import net.nimbus.lokiplayerclasses.core.players.LPlayers;
import net.nimbus.lokiplayerclasses.core.ranks.Rank;
import net.nimbus.lokiplayerclasses.core.ranks.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.stream.Collectors;

public class RankExe implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player p)) {
            sender.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.player-only")));
            return true;
        }
        if(args.length == 0) {
            sender.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.rank.usage")));
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "upgrade" : {
                Inventory gui = Bukkit.createInventory(null, 45, Vars.GUI.B.NAME);
                LPlayers.openBGUI(p, gui);
                p.openInventory(gui);
                return true;
            }
            case "list" : {
                p.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.rank.list")));
                LPlayer lp = LPlayers.get(p);
                int rank_index = Ranks.getAll().indexOf(lp.getRank());
                String s = Ranks.getAll().subList(0, rank_index).stream().map(Rank::getName).collect(Collectors.joining("&f, ")) +
                        (rank_index > 0 ? "&f, " : "") + lp.getRank().getName() + (rank_index < Ranks.getAll().size() - 1 ? " &7&o(your rank)&f, " : "") +
                        Ranks.getAll().subList(rank_index+1, Ranks.getAll().size()).stream().map(Rank::getName).collect(Collectors.joining("&f, "));
                p.sendMessage(Utils.toColor(s));
                return true;
            }
            case "info" : {
                if(args.length == 1) {
                    sender.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.rank.info.usage")));
                    return true;
                }
                Rank rank = Ranks.get(args[1].toLowerCase());
                if(rank == null) {
                    sender.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.rank.info.no-rank")));
                    return true;
                }
                p.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.rank.info.success").
                        replace("%rank%", rank.getName()).
                        replace("%exp%", rank.getExp()+"")).split("\n"));
                for(String s : rank.getRewards()){
                    p.sendMessage(Utils.toColor("   &#225e52◦&f "+s));
                }
                return true;
            }
            case "progress" : {
                LPlayer lp = LPlayers.get(p);
                Rank rank = lp.getRank();
                Rank next = rank.getNext();
                if(next == null) {
                    for(String s : LPClasses.a.getMessages().getStringList("Commands.rank.progress")) {
                        if(s.contains("%progress%")) {
                            p.sendMessage(Utils.toPrefix(rank.getName()+" &7&o(last rank)"));
                            continue;
                        }
                        p.sendMessage(Utils.toPrefix(
                                s.replace("%rank%", rank.getName()).
                                        replace("%exp%", lp.getExperience()+"")
                        ));
                    }
                } else {
                    StringBuilder progress = new StringBuilder();
                    ChatColor[] gradient = Utils.createGradient(ChatColor.of("#40c6ff"), ChatColor.of("#515ffc"), 10);
                    int o = (int) (10 * lp.getExperience() / (double) next.getExp() + 0.5);
                    for (int i = 0; i < 10; i++) {
                        if(i < o) {
                            progress.append(gradient[i]).append("■");
                        } else {
                            progress.append("&7■");
                        }
                    }
                    for(String s : LPClasses.a.getMessages().getStringList("Commands.rank.progress")) {
                        p.sendMessage(Utils.toPrefix(
                                s.replace("%rank%", rank.getName()).
                                        replace("%exp%", lp.getExperience()+"").
                                        replace("%progress%", progress.toString()).
                                        replace("%rank1%", next.getName()).
                                        replace("%exp1%", next.getExp()+"")
                        ));
                    }
                }
                return true;
            }
            default: {
                sender.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Commands.rank.usage")));
                return true;
            }
        }
    }
}
