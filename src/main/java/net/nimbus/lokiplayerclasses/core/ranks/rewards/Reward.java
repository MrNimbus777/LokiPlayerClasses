package net.nimbus.lokiplayerclasses.core.ranks.rewards;

import net.nimbus.lokiplayerclasses.Utils;
import org.bukkit.entity.Player;

public abstract class Reward {
    final String reward;
    private final String message;
    public Reward(String reward, String message){
        this.reward = reward;
        this.message = message;
    }
    public void message(Player player){
        player.sendMessage(Utils.toPrefix("&fYou got "+message));
    }
    public String getMessage() {
        return message;
    }

    public abstract void reward(Player player);
}
