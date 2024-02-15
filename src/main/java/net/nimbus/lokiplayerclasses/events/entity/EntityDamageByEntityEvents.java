package net.nimbus.lokiplayerclasses.events.entity;

import net.nimbus.lokiplayerclasses.Vars;
import net.nimbus.lokiplayerclasses.core.classes.PlayerClass;
import net.nimbus.lokiplayerclasses.core.classes.classes.AssassinClass;
import net.nimbus.lokiplayerclasses.core.classes.classes.BerserkClass;
import net.nimbus.lokiplayerclasses.core.classes.classes.KnightClass;
import net.nimbus.lokiplayerclasses.core.players.LPlayer;
import net.nimbus.lokiplayerclasses.core.players.LPlayers;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

public class EntityDamageByEntityEvents implements Listener {
    @EventHandler
    public void onEvent(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player p){
            LPlayer lp = LPlayers.get(p.getUniqueId());
            PlayerClass playerClass = lp.getPlayerClassNonNull();
            e.setDamage(e.getDamage()*playerClass.getItemDamage(p.getEquipment().getItemInMainHand().getType())*(1+Vars.Multipliers.STRENGTH*(playerClass.getStrength() + lp.getStrengthLvl())));
            if (playerClass instanceof KnightClass knight) {
                if(e.getEntity() instanceof LivingEntity living) {
                    if(lp.isAbility()) {
                        if (!KnightClass.getToDamage(lp.getUUID()).contains(living)) {
                            knight.useAbility(p, living, e.getDamage());
                            e.setCancelled(true);
                        }
                    }
                }
            } else if(playerClass instanceof AssassinClass) {
                if(!lp.isAbility()) return;
                int rand = new Random().nextInt(10);
                if(rand < 3) {
                    e.setDamage(e.getDamage()*1.5);
                    e.getEntity().getWorld().spawnParticle(Particle.REDSTONE, e.getEntity().getLocation().add(0, 0.5, 0), 15, 0.35, 0, 0.35, new Particle.DustOptions(Color.fromBGR(49, 11, 61), 1));
                }
            } else if(playerClass instanceof BerserkClass) {
                if(!lp.isAbility()) return;
                e.getEntity().getWorld().spawnParticle(Particle.REDSTONE, e.getEntity().getLocation().add(0, 0.5, 0), 15, 0.35, 0, 0.35, new Particle.DustOptions(Color.RED, 1));
            }
        } else if(e.getDamager() instanceof Arrow arrow) {
            if(arrow.getShooter() instanceof Player p){
                LPlayer lp = LPlayers.get(p.getUniqueId());
                PlayerClass playerClass = lp.getPlayerClassNonNull();
                e.setDamage(e.getDamage()*playerClass.getItemDamage(p.getEquipment().getItemInMainHand().getType()));
            }
        }
    }
}
