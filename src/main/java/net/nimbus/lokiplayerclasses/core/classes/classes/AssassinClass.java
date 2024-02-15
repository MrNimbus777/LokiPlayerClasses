package net.nimbus.lokiplayerclasses.core.classes.classes;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.nimbus.lokiplayerclasses.LPClasses;
import net.nimbus.lokiplayerclasses.Utils;
import net.nimbus.lokiplayerclasses.core.abilities.Activation;
import net.nimbus.lokiplayerclasses.core.classes.PlayerClass;
import net.nimbus.lokiplayerclasses.core.players.LPlayer;
import net.nimbus.lokiplayerclasses.core.players.LPlayers;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AssassinClass extends PlayerClass {
    public AssassinClass() {
        super("assassin", LPClasses.a.getConfig().getString("Classes.assassin.name"));
        setActivation(new Activation(List.of(ClickType.RIGHT, ClickType.RIGHT)));
    }


    @Override
    public boolean isAbilityItem(ItemStack item) {
        return switch (item.getType()) {
            case IRON_SWORD, GOLDEN_SWORD, STONE_SWORD, NETHERITE_SWORD, DIAMOND_SWORD, WOODEN_SWORD -> true;
            default -> false;
        };
    }

    @Override
    public void useAbility(Player player, Object... obj) {

    }

    @Override
    public void activateAbility(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 1));
        new BukkitRunnable(){
            @Override
            public void run() {
                LPlayer lp = LPlayers.get(player);
                lp.setAbility(false);
                lp.updateCooldown();
                lp.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                        Utils.toColor(LPClasses.a.getMessage("Actions.ability_ended"))));
            }
        }.runTaskLater(LPClasses.a, 300);
    }

    @Override
    public double getItemDamage(Material material) {
        return switch (material) {
            case BOW, CROSSBOW -> 1.1;
            case IRON_SWORD, GOLDEN_SWORD, STONE_SWORD, NETHERITE_SWORD, DIAMOND_SWORD, WOODEN_SWORD -> 1.1;
            case IRON_AXE, GOLDEN_AXE, STONE_AXE, NETHERITE_AXE, DIAMOND_AXE, WOODEN_AXE -> 0.9;
            default -> 1.0;
        };
    }
}
