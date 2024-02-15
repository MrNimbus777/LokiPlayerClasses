package net.nimbus.lokiplayerclasses.core.classes.classes;

import net.nimbus.lokiplayerclasses.LPClasses;
import net.nimbus.lokiplayerclasses.core.abilities.Activation;
import net.nimbus.lokiplayerclasses.core.classes.PlayerClass;
import net.nimbus.lokiplayerclasses.core.players.LPlayer;
import net.nimbus.lokiplayerclasses.core.players.LPlayers;
import net.nimbus.lokiplayerclasses.events.entity.ProjectileHitEvents;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ArcherClass extends PlayerClass {

    public ArcherClass() {
        super("archer", LPClasses.a.getConfig().getString("Classes.archer.name"));
        setActivation(new Activation(List.of(ClickType.LEFT, ClickType.LEFT)));
    }


    @Override
    public boolean isAbilityItem(ItemStack item) {
        return switch (item.getType()){
            case BOW, CROSSBOW -> true;
            default -> false;
        };
    }

    @Override
    public void useAbility(Player player, Object... obj) {
        List<LivingEntity> hit = new ArrayList<>();
        hit.add(player);
        Projectile projectile = (Projectile) obj[0];
        new BukkitRunnable(){
            @Override
            public void run() {
                if(!ProjectileHitEvents.flying.contains(projectile)) {
                    cancel();
                    for(int x = -1; x <= 1; x++) {
                        for(int y = -1; y <= 1; y++) {
                            projectile.getWorld().spawnEntity(projectile.getLocation().add(x, 0, y), EntityType.LIGHTNING);
                        }
                    }
                    return;
                }
                for(Entity ent : projectile.getNearbyEntities(5, 5, 5)){
                    if(ent instanceof LivingEntity living) {
                        if(hit.contains(living)) continue;
                        projectile.getWorld().spawnEntity(living.getLocation(), EntityType.LIGHTNING);
                        living.damage(20, player);
                        hit.add(living);
                    }
                }
            }
        }.runTaskTimer(LPClasses.a, 0, 1);
        LPlayers.get(player).updateCooldown();
    }

    @Override
    public void activateAbility(Player player) {

    }

    @Override
    public double getItemDamage(Material material) {
        return switch (material) {
            case BOW, CROSSBOW -> 1.25;
            case IRON_SWORD, GOLDEN_SWORD, STONE_SWORD, NETHERITE_SWORD, DIAMOND_SWORD, WOODEN_SWORD -> 0.85;
            case IRON_AXE, GOLDEN_AXE, STONE_AXE, NETHERITE_AXE, DIAMOND_AXE, WOODEN_AXE -> 0.9;
            default -> 1.0;
        };
    }
}
