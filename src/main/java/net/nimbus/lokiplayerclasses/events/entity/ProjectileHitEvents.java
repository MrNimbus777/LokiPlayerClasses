package net.nimbus.lokiplayerclasses.events.entity;

import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.ArrayList;
import java.util.List;

public class ProjectileHitEvents implements Listener {
    public static List<Projectile> flying = new ArrayList<>();
    @EventHandler
    public void onEvent(ProjectileHitEvent e) {
        flying.remove(e.getEntity());
    }
}
