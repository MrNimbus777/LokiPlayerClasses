package net.nimbus.lokiplayerclasses.events.player;

import net.nimbus.lokiplayerclasses.core.players.LPlayer;
import net.nimbus.lokiplayerclasses.core.players.LPlayers;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEvents implements Listener {
    @EventHandler
    public void onEvent(PlayerQuitEvent e){
        LPlayer lp = LPlayers.get(e.getPlayer().getUniqueId());
        lp.save();
        LPlayers.unregister(lp);
    }
}
