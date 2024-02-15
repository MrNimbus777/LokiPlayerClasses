package net.nimbus.lokiplayerclasses;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.nimbus.lokiplayerclasses.commands.completers.LPCCompleter;
import net.nimbus.lokiplayerclasses.commands.completers.RankCompleter;
import net.nimbus.lokiplayerclasses.commands.executors.LPCExe;
import net.nimbus.lokiplayerclasses.commands.executors.RankExe;
import net.nimbus.lokiplayerclasses.core.Placeholders;
import net.nimbus.lokiplayerclasses.core.classes.PlayerClass;
import net.nimbus.lokiplayerclasses.core.classes.classes.*;
import net.nimbus.lokiplayerclasses.core.players.LPlayer;
import net.nimbus.lokiplayerclasses.core.players.LPlayers;
import net.nimbus.lokiplayerclasses.core.ranks.Rank;
import net.nimbus.lokiplayerclasses.core.ranks.Ranks;
import net.nimbus.lokiplayerclasses.core.ranks.rewards.CommandReward;
import net.nimbus.lokiplayerclasses.core.ranks.rewards.PermissionReward;
import net.nimbus.lokiplayerclasses.core.ranks.rewards.Reward;
import net.nimbus.lokiplayerclasses.events.entity.*;
import net.nimbus.lokiplayerclasses.events.player.*;
import net.nimbus.lokiplayerclasses.core.classes.PlayerClasses;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LPClasses extends JavaPlugin {

    public static LPClasses a;
    public LuckPerms lp;
    public String version;

    private static YamlConfiguration messages;

    public static PlayerClass defaultClass;

    public void loadConfig(boolean reload){
        File config = new File(getDataFolder(), "config.yml");
        if (!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
            try {
                getConfig().load(config);
                getLogger().info("Created new config.yml file at " + config.getPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (reload) {
            try {
                getConfig().load(config);
                getLogger().info("Config reloaded.");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        Vars.init();
    }

    void loadCommands(){
        loadCommand("lokiplayerclass", new LPCExe(), new LPCCompleter());
        loadCommand("rank", new RankExe(), new RankCompleter());
    }

    void loadEvents(){
        loadEvent(new PlayerInteractEvents());
        loadEvent(new InventoryClickEvents());
        loadEvent(new EntityDamageByEntityEvents());
        loadEvent(new PlayerJoinEvents());
        loadEvent(new PlayerQuitEvents());
        loadEvent(new EntityDeathEvents());
        loadEvent(new EntityShootBowEvents());
        loadEvent(new ProjectileHitEvents());
    }

    public void onEnable() {
        LPClasses.a = this;
        lp = LuckPermsProvider.get();
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        version = packageName.substring(packageName.lastIndexOf(".") + 1);

        loadConfig(false);

        loadEvents();
        loadCommands();

        defaultClass = new DefaultClass();
        PlayerClasses.register(new ArcherClass());
        PlayerClasses.register(new AssassinClass());
        PlayerClasses.register(new BerserkClass());
        PlayerClasses.register(new KnightClass());
        PlayerClasses.register(new MageClass());

        loadMessages();

        ConfigurationSection section = getConfig().getConfigurationSection("Ranks");
        if(section != null) {
            for(String id : section.getKeys(false)) {
                String name = section.getString(id+".name");
                int exp = section.getInt(id+".exp", 0);
                List<Reward> rewards = new ArrayList<>();
                for(String s : section.getStringList(id+".rewards")){
                    if(s.startsWith("cmd:")){
                        String[] split = s.replaceFirst("cmd:", "").split("[|]");
                        rewards.add(new CommandReward(split[0], split[1]));
                    } else if(s.startsWith("perm:")) {
                        String[] split = s.replaceFirst("perm:", "").split("[|]");
                        rewards.add(new PermissionReward(split[0], split[1]));
                    } else getLogger().severe("Unknown reward type in rank "+id+": "+s);
                }
                Rank rank = new Rank(id, Utils.toColor(name), exp);
                rank.setRewards(rewards);
                Ranks.register(rank);
                getLogger().info("Loaded rank " + id + ".");
            }
        }

        Bukkit.getOnlinePlayers().forEach(p -> {
            LPlayer lp = LPlayers.load(p.getUniqueId());
            if(lp != null) {
                LPlayers.register(lp);
                lp.updatePassiveProperties();
            }
        });

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new Placeholders().register();
        }
    }

    public void onDisable() {
        PlayerClasses.clearRam();
        LPlayers.getAll().forEach(LPlayer::save);
        LPlayers.clearRam();
        Ranks.clearRam();
    }
    void loadEvent(Listener listener){
        try {
            getServer().getPluginManager().registerEvents(listener, this);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void loadCommand(String cmd, CommandExecutor executor){
        try {
            getCommand(cmd).setExecutor(executor);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    void loadCommand(String cmd, CommandExecutor executor, TabCompleter completer){
        try {
            loadCommand(cmd, executor);
            getCommand(cmd).setTabCompleter(completer);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadMessages(){
        File messagesFile = new File(getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            if(!messagesFile.getParentFile().exists()) messagesFile.getParentFile().mkdirs();
            try {
                messages = YamlConfiguration.loadConfiguration(new InputStreamReader(getResource("messages.yml")));
                messages.save(messagesFile);
                getLogger().info("Created new messages.yml file at " + messagesFile.getPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                messages = YamlConfiguration.loadConfiguration(messagesFile);
                getLogger().info("Messages file loaded.");
            } catch (Exception exception) {
            }
        }
    }

    public YamlConfiguration getMessages() {
        return messages;
    }

    public String getMessage(String key) {
        return getMessages().getString(key);
    }
}
