package net.nimbus.lokiplayerclasses.events.player;

import net.nimbus.lokiplayerclasses.Utils;
import net.nimbus.lokiplayerclasses.core.classes.PlayerClass;
import net.nimbus.lokiplayerclasses.core.classes.PlayerClasses;
import net.nimbus.lokiplayerclasses.core.players.LPlayer;
import net.nimbus.lokiplayerclasses.core.players.LPlayers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickEvents implements Listener {
    @EventHandler
    public void onEvent(InventoryClickEvent e){
        if(e.getClickedInventory() != null) {
            if(Utils.readTag(e.getInventory().getItem(0), "lpcGUI").equals("lpc1")){
                e.setCancelled(true);
                if(e.getClickedInventory().equals(e.getInventory())) {
                    PlayerClass playerClass = PlayerClasses.get(Utils.readTag(e.getInventory().getItem(e.getSlot()), "lokiclass"));
                    if(playerClass != null){
                        LPlayers.chooseClass((Player) e.getWhoClicked(), playerClass);
                        e.getWhoClicked().closeInventory();
                    }
                }
            } else if(Utils.readTag(e.getInventory().getItem(0), "lpcGUI").equals("lpc2")) {
                e.setCancelled(true);
                if (e.getClickedInventory().equals(e.getInventory())) {
                    String key = Utils.readTag(e.getInventory().getItem(e.getSlot()), "upgrade");
                    if (key.equals("")) return;
                    LPlayer lp = LPlayers.get((Player) e.getWhoClicked());
                    if (lp.getPoints() > 0) {
                        switch (key) {
                            case "speed": {
                                lp.setSpeedLvl(lp.getSpeedLvl() + 1);
                                break;
                            }
                            case "strength": {
                                lp.setStrengthLvl(lp.getStrengthLvl() + 1);
                                break;
                            }
                            case "dexterity": {
                                lp.setDexterityLvl(lp.getDexterityLvl() + 1);
                                break;
                            }
                            case "magic": {
                                lp.setMagicLvl(lp.getMagicLvl() + 1);
                                break;
                            }
                            case "health": {
                                lp.setHealthLvl(lp.getHealthLvl() + 1);
                                break;
                            }
                            default:
                                return;
                        }
                        lp.setPoints(lp.getPoints() - 1);
                        lp.updatePassiveProperties();
                        LPlayers.openBGUI(lp.getPlayer(), e.getInventory());
                    }
                }
            }
        }
    }
}
