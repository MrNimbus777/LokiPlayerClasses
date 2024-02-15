package net.nimbus.lokiplayerclasses.core.players;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.nimbus.lokiplayerclasses.LPClasses;
import net.nimbus.lokiplayerclasses.Utils;
import net.nimbus.lokiplayerclasses.Vars;
import net.nimbus.lokiplayerclasses.core.classes.PlayerClasses;
import net.nimbus.lokiplayerclasses.core.classes.PlayerClass;
import net.nimbus.lokiplayerclasses.core.ranks.ExpAction;
import net.nimbus.lokiplayerclasses.core.ranks.Rank;
import net.nimbus.lokiplayerclasses.core.ranks.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LPlayer {
    public static Map<UUID, Long> cooldownMap = new HashMap<>();
    public static Map<UUID, Boolean> abilityMap = new HashMap<>();
    private final UUID uuid;
    String class_id = null;
    String rank_id;
    int experience;
    int points = 0;

    int speed_lvl = 0;
    int strength_lvl = 0;
    int dexterity_lvl = 0;
    int health_lvl = 0;
    int magic_lvl = 0;

    public LPlayer(UUID uuid){
        this.uuid = uuid;
        rank_id = Ranks.getAll().get(0).getId();
    }

    public void setPlayerClass(PlayerClass playerClass) {
        if(playerClass == null) {
            this.class_id = null;
            return;
        }
        this.class_id = playerClass.getId();
        updatePassiveProperties();
    }

    public boolean isAbility() {
        return abilityMap.getOrDefault(getUUID(), false);
    }

    public void setAbility(boolean ability) {
        abilityMap.put(getUUID(), ability);
    }
    public boolean isCooldown(){
        return cooldownMap.getOrDefault(getUUID(), 0L) >= System.currentTimeMillis();
    }
    public void updateCooldown(){
        cooldownMap.put(getUUID(), System.currentTimeMillis() + getPlayerClassNonNull().getCooldown());
    }

    public int getCooldown() {
        return (int) (cooldownMap.getOrDefault(getUUID(), 0L)-System.currentTimeMillis())/1000;
    }

    public boolean hasClass(){
        return PlayerClasses.exists(this.class_id);
    }
    public Player getPlayer(){
        return Bukkit.getPlayer(getUUID());
    }

    public PlayerClass getPlayerClass() {
        return PlayerClasses.get(this.class_id);
    }
    public PlayerClass getPlayerClassNonNull() {
        return PlayerClasses.getNonNull(this.class_id);
    }

    public Rank getRank(){
        return Ranks.get(this.rank_id);
    }

    public void setRank(Rank rank){
        Utils.removePermission(getUUID(), "lpc.rank."+this.rank_id);
        if(rank == null) {
            this.rank_id = null;
            return;
        }
        this.rank_id = rank.getId();
        Utils.addPermission(getUUID(), "lpc.rank."+this.rank_id);
    }

    public void save(){
        File file = new File(LPClasses.a.getDataFolder(), "players/"+uuid.toString()+".json");
        JSONObject obj = new JSONObject();
        obj.put("exp", getExperience());
        obj.put("points", getPoints());
        obj.put("class", class_id);
        obj.put("rank", rank_id);

        obj.put("speed", getSpeedLvl());
        obj.put("strength", getStrengthLvl());
        obj.put("health", getHealthLvl());
        obj.put("dexterity", getDexterityLvl());
        obj.put("magic", getMagicLvl());

        try {
            if(!file.exists()) {
                if(!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            writer.write(obj.toJSONString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePassiveProperties(){
        getPlayer().
                getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).
                setBaseValue((1+(getPlayerClassNonNull().getSpeed() + getSpeedLvl()) * Vars.Multipliers.SPEED) * 0.1);
        getPlayer().
                getAttribute(Attribute.GENERIC_MAX_HEALTH).
                setBaseValue(20+getPlayerClassNonNull().getHealth() + getHealthLvl() * Vars.Multipliers.HEALTH);
        getPlayer().
                getAttribute(Attribute.GENERIC_ATTACK_SPEED).
                setBaseValue((2+(getPlayerClassNonNull().getDexterity() + getDexterityLvl()) * Vars.Multipliers.DEXTERITY) * 2);
    }


    public UUID getUUID() {
        return uuid;
    }
    public int getExperience() {
        return experience;
    }

    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }

    public int getHealthLvl() {
        return health_lvl;
    }
    public int getSpeedLvl() {
        return speed_lvl;
    }
    public int getStrengthLvl() {
        return strength_lvl;
    }
    public int getDexterityLvl() {
        return dexterity_lvl;
    }
    public int getMagicLvl() {
        return magic_lvl;
    }

    public void setExperience(int experience) {
        Rank next = getRank().getNext();
        if(next != null) if(experience >= next.getExp()) {
            experience -= next.getExp();
            setRank(next);
            getPlayer().sendTitle(Utils.toColor("&a&lRank up&6&l!"), Utils.toColor("&fYou have got rank " + getRank().getName() + "&f!"));
            getPlayer().playSound(getPlayer(), Sound.ENTITY_PLAYER_LEVELUP, 30, 1);
            Firework firework = (Firework) getPlayer().getWorld().spawnEntity(getPlayer().getLocation(), EntityType.FIREWORK);
            FireworkMeta meta = firework.getFireworkMeta();
            meta.addEffect(FireworkEffect.builder().withColor(Color.GREEN).withFade(Color.GREEN).build());
            firework.setFireworkMeta(meta);
            firework.detonate();
            next.reward(getPlayer());
        }
        this.experience = experience;
    }
    public void addExperience(ExpAction expAction) {
        addExperience(expAction.getExp());
    }
    public void addExperience(int add){
        int exp = getExperience()+add;
        getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,
                TextComponent.fromLegacyText(Utils.toColor(LPClasses.a.getMessage("Actions.got_exp").
                        replace("%exp%", ""+add))));
        setExperience(exp);
    }
    public void setHealthLvl(int health_lvl) {
        this.health_lvl = health_lvl;
    }
    public void setSpeedLvl(int speed_lvl) {
        this.speed_lvl = speed_lvl;
    }
    public void setStrengthLvl(int strength_lvl) {
        this.strength_lvl = strength_lvl;
    }
    public void setDexterityLvl(int dexterity_lvl) {
        this.dexterity_lvl = dexterity_lvl;
    }
    public void setMagicLvl(int magic_lvl) {
        this.magic_lvl = magic_lvl;
    }
}
