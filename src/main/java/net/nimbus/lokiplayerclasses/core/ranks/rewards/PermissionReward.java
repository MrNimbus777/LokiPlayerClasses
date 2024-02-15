package net.nimbus.lokiplayerclasses.core.ranks.rewards;

import net.nimbus.lokiplayerclasses.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PermissionReward extends Reward {
    public PermissionReward(String reward, String message) {
        super(reward, message);
    }

    @Override
    public void reward(Player player) {
        Bukkit.getLogger().info("Permission reward for Player "+ player.getName()+": "+reward);
        Utils.addPermission(player.getUniqueId(), reward);
    }
}
