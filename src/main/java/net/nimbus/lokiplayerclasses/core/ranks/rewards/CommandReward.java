package net.nimbus.lokiplayerclasses.core.ranks.rewards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandReward extends Reward {
    public CommandReward(String reward, String message) {
        super(reward, message);
    }

    @Override
    public void reward(Player player) {
        Bukkit.getLogger().info("Command reward for Player "+ player.getName()+": "+reward.replace("%player%", player.getName()));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), reward.replace("%player%", player.getName()));
    }
}
