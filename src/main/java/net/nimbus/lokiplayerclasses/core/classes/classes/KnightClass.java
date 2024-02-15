package net.nimbus.lokiplayerclasses.core.classes.classes;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.nimbus.lokiplayerclasses.LPClasses;
import net.nimbus.lokiplayerclasses.Utils;
import net.nimbus.lokiplayerclasses.core.abilities.Activation;
import net.nimbus.lokiplayerclasses.core.classes.PlayerClass;
import net.nimbus.lokiplayerclasses.core.players.LPlayer;
import net.nimbus.lokiplayerclasses.core.players.LPlayers;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class KnightClass extends PlayerClass {
    public KnightClass() {
        super("knight", LPClasses.a.getConfig().getString("Classes.knight.name"));
        setActivation(new Activation(List.of(ClickType.RIGHT, ClickType.RIGHT)));
    }

    @Override
    public boolean isAbilityItem(ItemStack item) {
        return switch (item.getType()) {
            case IRON_SWORD, GOLDEN_SWORD, STONE_SWORD, NETHERITE_SWORD, DIAMOND_SWORD, WOODEN_SWORD -> true;
            default -> false;
        };
    }

    private static Map<UUID, List<LivingEntity>> map = new HashMap<>();
    public static List<LivingEntity> getToDamage(UUID uuid){
        return map.getOrDefault(uuid, new ArrayList<>());
    }

    @Override
    public void useAbility(Player player, Object... obj) {
        if(obj[0] instanceof LivingEntity living) {
            List<LivingEntity> list = living.getWorld().getNearbyEntities(living.getLocation(), 2, 2, 2).stream().
                    filter(e -> e instanceof LivingEntity).
                    map(e -> (LivingEntity) e).
                    collect(Collectors.toList());
            list.remove(player);
            map.put(player.getUniqueId(), list);
            list.forEach(e -> e.damage((double) obj[1], player));
            living.getWorld().spawnParticle(Particle.REDSTONE, living.getLocation().add(0, 0.5, 0), 50, 2, 0, 2, new Particle.DustOptions(Color.fromBGR(109, 106, 158), 2));
            map.remove(player.getUniqueId());
        }
    }

    @Override
    public void activateAbility(Player player) {
        new BukkitRunnable(){
            @Override
            public void run() {
                LPlayer lp = LPlayers.get(player);
                lp.setAbility(false);
                lp.updateCooldown();
                lp.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                        Utils.toColor(LPClasses.a.getMessage("Actions.ability_ended"))));
            }
        }.runTaskLater(LPClasses.a, 200);
    }

    @Override
    public double getItemDamage(Material material) {
        return switch (material) {
            case BOW, CROSSBOW -> 0.8;
            case IRON_SWORD, GOLDEN_SWORD, STONE_SWORD, NETHERITE_SWORD, DIAMOND_SWORD, WOODEN_SWORD -> 1.05;
            case IRON_AXE, GOLDEN_AXE, STONE_AXE, NETHERITE_AXE, DIAMOND_AXE, WOODEN_AXE -> 0.9;
            default -> 1.0;
        };
    }
}