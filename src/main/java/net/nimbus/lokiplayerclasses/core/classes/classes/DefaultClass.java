package net.nimbus.lokiplayerclasses.core.classes.classes;

import net.nimbus.lokiplayerclasses.core.classes.PlayerClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DefaultClass extends PlayerClass {
    public DefaultClass() {
        super("default", "Default");
    }

    @Override
    public boolean isAbilityItem(ItemStack item) {
        return false;
    }

    @Override
    public void useAbility(Player player, Object... obj) {

    }

    @Override
    public void activateAbility(Player player) {

    }

    @Override
    public double getItemDamage(Material material) {
        return switch (material) {
            case BOW, CROSSBOW,  IRON_SWORD, GOLDEN_SWORD, STONE_SWORD, NETHERITE_SWORD, DIAMOND_SWORD, WOODEN_SWORD, IRON_AXE, GOLDEN_AXE, STONE_AXE, NETHERITE_AXE, DIAMOND_AXE, WOODEN_AXE -> 0.5;
            default -> 1.0;
        };
    }
}
