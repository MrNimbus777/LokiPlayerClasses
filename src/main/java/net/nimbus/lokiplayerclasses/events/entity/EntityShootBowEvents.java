package net.nimbus.lokiplayerclasses.events.entity;

import net.nimbus.lokiplayerclasses.core.classes.classes.ArcherClass;
import net.nimbus.lokiplayerclasses.core.players.LPlayer;
import net.nimbus.lokiplayerclasses.core.players.LPlayers;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class EntityShootBowEvents implements Listener {
    @EventHandler
    public void onEvent(EntityShootBowEvent e) {
        if(e.getEntity() instanceof Player p) {
            LPlayer lp = LPlayers.get(p);
            if(lp.getPlayerClassNonNull() instanceof ArcherClass playerClass) {
                if(lp.isAbility()) {
                    ProjectileHitEvents.flying.add((Projectile) e.getProjectile());
                    playerClass.useAbility(p, e.getProjectile());
                    lp.setAbility(false);
                }
            }
        }
    }
}
