package net.nimbus.lokiplayerclasses.events.player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.nimbus.lokiplayerclasses.LPClasses;
import net.nimbus.lokiplayerclasses.Utils;
import net.nimbus.lokiplayerclasses.core.abilities.Activation;
import net.nimbus.lokiplayerclasses.core.classes.PlayerClass;
import net.nimbus.lokiplayerclasses.core.classes.classes.MageClass;
import net.nimbus.lokiplayerclasses.core.players.LPlayer;
import net.nimbus.lokiplayerclasses.core.players.LPlayers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerInteractEvents implements Listener {
    private static final Map<UUID, Activation> clicksMap = new HashMap<>();
    @EventHandler
    public void onEvent(PlayerInteractEvent e){
        LPlayer lp = LPlayers.get(e.getPlayer());
        if(!e.getPlayer().isSneaking()) return;
        if(lp.hasClass()) {
            PlayerClass playerClass = lp.getPlayerClass();
            if (playerClass instanceof MageClass mage) {
                if (!playerClass.isAbilityItem(lp.getPlayer().getEquipment().getItemInMainHand())) return;
                Activation playerActivation = clicksMap.getOrDefault(lp.getUUID(), new Activation(new ArrayList<>()));
                ClickType click = Activation.convert(e.getAction());
                if (click == null) return;
                playerActivation.addClick(click);
                clicksMap.put(lp.getUUID(), playerActivation);
                if (mage.getActivation().isKey(playerActivation)) {
                    if(lp.isAbility()) return;
                    if (lp.isCooldown()) {
                        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                                Utils.toColor(LPClasses.a.getMessage("Actions.ability_cooldown").
                                        replace("%time%", lp.getCooldown() + ""))));
                        return;
                    }
                    e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                            Utils.toColor(LPClasses.a.getMessage("Actions.ability_activated"))));
                    lp.setAbility(true);
                    playerClass.activateAbility(lp.getPlayer());
                    clicksMap.remove(lp.getUUID());
                } else if (mage.getActivation2().isKey(playerActivation)) {
                    if (MageClass.isCooldown(lp.getUUID())) {
                        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                                Utils.toColor(LPClasses.a.getMessage("Actions.ability_cooldown").
                                        replace("%time%", MageClass.getCooldown(lp.getUUID()) + ""))));
                        return;
                    }
                    e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                            Utils.toColor(LPClasses.a.getMessage("Actions.ability_activated"))));
                    mage.activateAbility2(lp.getPlayer());
                    MageClass.setCooldown(lp.getUUID());
                    clicksMap.remove(lp.getUUID());
                }
            } else {
                if (!playerClass.isAbilityItem(lp.getPlayer().getEquipment().getItemInMainHand())) return;
                Activation playerActivation = clicksMap.getOrDefault(lp.getUUID(), new Activation(new ArrayList<>()));
                ClickType click = Activation.convert(e.getAction());
                if (click == null) return;
                playerActivation.addClick(click);
                clicksMap.put(lp.getUUID(), playerActivation);
                if (playerClass.getActivation().isKey(playerActivation)) {
                    if(lp.isAbility()) return;
                    if (lp.isCooldown()) {
                        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                                Utils.toColor(LPClasses.a.getMessage("Actions.ability_cooldown").
                                        replace("%time%", lp.getCooldown() + ""))));
                        return;
                    }
                    e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                            Utils.toColor(LPClasses.a.getMessage("Actions.ability_activated"))));
                    lp.setAbility(true);
                    playerClass.activateAbility(lp.getPlayer());
                    clicksMap.remove(lp.getUUID());
                }
            }
        }
    }
}
