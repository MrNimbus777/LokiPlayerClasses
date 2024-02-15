package net.nimbus.lokiplayerclasses.events.player;

import net.nimbus.lokiplayerclasses.core.players.LPlayer;
import net.nimbus.lokiplayerclasses.core.players.LPlayers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEvents implements Listener {
    @EventHandler
    public void onEvent(PlayerJoinEvent e){
        Player p = e.getPlayer();
        LPlayer lp = LPlayers.load(p.getUniqueId());
        if(lp == null) return;
        LPlayers.register(lp);
        lp.updatePassiveProperties();
    }
}
