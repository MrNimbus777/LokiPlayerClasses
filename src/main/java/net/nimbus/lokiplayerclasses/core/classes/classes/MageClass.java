package net.nimbus.lokiplayerclasses.core.classes.classes;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.nimbus.lokiplayerclasses.LPClasses;
import net.nimbus.lokiplayerclasses.Utils;
import net.nimbus.lokiplayerclasses.core.abilities.Activation;
import net.nimbus.lokiplayerclasses.core.classes.PlayerClass;
import net.nimbus.lokiplayerclasses.core.players.LPlayers;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MageClass extends PlayerClass {
    private Activation activation2;

    private static Map<UUID, Long> mageCooldown = new HashMap<>();
    public MageClass() {
        super("mage", LPClasses.a.getConfig().getString("Classes.mage.name"));
        setActivation(new Activation(List.of(ClickType.LEFT, ClickType.LEFT)));
        activation2 = new Activation(List.of(ClickType.RIGHT, ClickType.RIGHT));
    }

    public static boolean isCooldown(UUID uuid) {
        return mageCooldown.getOrDefault(uuid, 0L)  >= System.currentTimeMillis();
    }
    public static void setCooldown(UUID uuid){
        mageCooldown.put(uuid, System.currentTimeMillis()+30000);
    }
    public static int getCooldown(UUID uuid){
        return (int) ((mageCooldown.getOrDefault(uuid, 0L)-System.currentTimeMillis())/1000);
    }
    @Override
    public boolean isAbilityItem(ItemStack item) {
        return item.getType() == Material.STICK;
    }

    @Override
    public void useAbility(Player player, Object... obj) {

    }

    @Override
    public void activateAbility(Player player) {
        Fireball fireball = (Fireball) player.getWorld().spawnEntity(player.getEyeLocation().add(player.getLocation().getDirection().multiply(0.1)), EntityType.FIREBALL);
        fireball.setDirection(player.getLocation().getDirection());
        fireball.setVelocity(player.getLocation().getDirection().multiply(2));
        fireball.setYield(2);
        LPlayers.get(player).updateCooldown();
        LPlayers.get(player).setAbility(false);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(" "));
    }


    public void activateAbility2(Player player) {
        player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().add(0, 3, 0), 35, 1.5, 0, 1.5, new Particle.DustOptions(Color.YELLOW, 2));
        List<Player> list = player.getWorld().getPlayers().stream().
                filter(p -> p.getLocation().distance(player.getLocation()) <= 5).
                toList();
        list.forEach(p -> {
            p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
            p.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation().add(0, 0.5, 0), 15, 0.35, 0, 0.35, new Particle.DustOptions(Color.LIME, 1));
        });
        setCooldown(player.getUniqueId());
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(" "));
    }

    public Activation getActivation2() {
        return activation2;
    }

    @Override
    public double getItemDamage(Material material) {
        return switch (material) {
            case IRON_SWORD, GOLDEN_SWORD, STONE_SWORD, NETHERITE_SWORD, DIAMOND_SWORD, WOODEN_SWORD -> 0.85;
            case IRON_AXE, GOLDEN_AXE, STONE_AXE, NETHERITE_AXE, DIAMOND_AXE, WOODEN_AXE -> 0.9;
            default -> 1.0;
        };
    }
}