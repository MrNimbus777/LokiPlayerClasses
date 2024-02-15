package net.nimbus.lokiplayerclasses.core.classes;

import net.nimbus.lokiplayerclasses.LPClasses;
import net.nimbus.lokiplayerclasses.Utils;
import net.nimbus.lokiplayerclasses.core.abilities.Activation;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayerClass {
    private final String id;
    private final String name;
    private final Material gui_material;
    private final List<String> description;

    private final int speed;
    private final int strength;
    private final int health;
    private final int dexterity;
    private final int magic;
    private Activation activation;
    private final int cooldown;

    public PlayerClass(String id, String name){
        this.id = id;
        this.name = Utils.toColor(name);
        speed = LPClasses.a.getConfig().getInt("Classes."+id+".properties.speed", 0);
        strength = LPClasses.a.getConfig().getInt("Classes."+id+".properties.strength", 0);
        health = LPClasses.a.getConfig().getInt("Classes."+id+".properties.health", 0);
        dexterity = LPClasses.a.getConfig().getInt("Classes."+id+".properties.dexterity", 0);
        magic = LPClasses.a.getConfig().getInt("Classes."+id+".properties.magic", 0);
        cooldown = LPClasses.a.getConfig().getInt("Classes."+id+".ability_cooldown", 0)*1000;
        gui_material = Material.getMaterial(LPClasses.a.getConfig().getString("Classes."+id+".gui_icon", "stone").toUpperCase());
        description = new ArrayList<>(LPClasses.a.getConfig().getStringList("Classes."+id+".custom_description"));
        activation = new Activation(new ArrayList<>());
    }

    public void setActivation(Activation activation) {
        this.activation = activation;
    }

    public Activation getActivation() {
        return activation;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public Material getGUIMaterial(){
        return gui_material;
    }
    public List<String> getDescription(){
        return description;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getSpeed(){
        return speed;
    }
    public int getStrength(){
        return strength;
    }
    public int getHealth(){
        return health;
    }
    public int getDexterity(){
        return dexterity;
    }
    public int getMagic(){
        return magic;
    }

    public abstract boolean isAbilityItem(ItemStack item);
    public abstract void useAbility(Player player, Object... obj);
    public abstract void activateAbility(Player player);
    public abstract double getItemDamage(Material material);
}