package net.nimbus.lokiplayerclasses.events.entity;

import net.nimbus.lokiplayerclasses.core.players.LPlayer;
import net.nimbus.lokiplayerclasses.core.players.LPlayers;
import net.nimbus.lokiplayerclasses.core.ranks.ExpAction;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathEvents implements Listener {
    @EventHandler
    public void onEvent(EntityDeathEvent e){
        if(e.getEntity().getKiller() != null) {
            LPlayer killer = LPlayers.get(e.getEntity().getKiller());
            if(e.getEntity() instanceof Player) {
                killer.addExperience(ExpAction.KILL_PLAYER);
            } else if(e.getEntity() instanceof Boss) {
                killer.addExperience(ExpAction.KILL_BOSS);
            } else if(e.getEntity() instanceof Monster) {
                killer.addExperience(ExpAction.KILL_MOB);
            } else killer.addExperience(ExpAction.KILL_ANIMAL);
        }
    }
}
