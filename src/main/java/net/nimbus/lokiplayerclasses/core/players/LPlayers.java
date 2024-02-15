package net.nimbus.lokiplayerclasses.core.players;

import net.nimbus.lokiplayerclasses.LPClasses;
import net.nimbus.lokiplayerclasses.Utils;
import net.nimbus.lokiplayerclasses.Vars;
import net.nimbus.lokiplayerclasses.core.classes.PlayerClass;
import net.nimbus.lokiplayerclasses.core.classes.PlayerClasses;
import net.nimbus.lokiplayerclasses.core.ranks.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.*;

public class LPlayers {
    private static final Map<UUID, LPlayer> map = new HashMap<>();

    public static void register(LPlayer lPlayer){
        map.put(lPlayer.getUUID(), lPlayer);
    }
    public static void unregister(LPlayer lPlayer){
        map.remove(lPlayer.getUUID());
    }
    public static void clearRam(){
        getAll().forEach(LPlayers::unregister);
    }
    public static LPlayer get(UUID uuid){
        return map.getOrDefault(uuid, null);
    }
    public static LPlayer get(Player player){
        return get(player.getUniqueId());
    }
    public static List<LPlayer> getAll(){
        return new ArrayList<>(map.values());
    }
    public static LPlayer load(UUID uuid) {
        File file = new File(LPClasses.a.getDataFolder(), "players/"+uuid.toString()+".json");
        try {
            if(!file.exists()) return new LPlayer(uuid);
            FileReader fileReader = new FileReader(file);
            JSONObject obj = (JSONObject) new JSONParser().parse(fileReader);
            fileReader.close();

            String class_id = (String) obj.getOrDefault("class", "");
            int exp = Integer.parseInt(obj.getOrDefault("exp", 0).toString());
            int points = Integer.parseInt(obj.getOrDefault("points", 0).toString());
            String rank_id = (String) obj.getOrDefault("rank", "");

            LPlayer lPlayer = new LPlayer(uuid);
            lPlayer.setPlayerClass(PlayerClasses.get(class_id));
            lPlayer.setRank(Ranks.getNonNull(rank_id));
            lPlayer.setExperience(exp);
            lPlayer.setPoints(points);

            lPlayer.setSpeedLvl(Integer.parseInt(obj.getOrDefault("speed", 0).toString()));
            lPlayer.setStrengthLvl(Integer.parseInt(obj.getOrDefault("strength", 0).toString()));
            lPlayer.setHealthLvl(Integer.parseInt(obj.getOrDefault("health", 0).toString()));
            lPlayer.setDexterityLvl(Integer.parseInt(obj.getOrDefault("dexterity", 0).toString()));
            lPlayer.setMagicLvl(Integer.parseInt(obj.getOrDefault("magic", 0).toString()));

            return lPlayer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void chooseClass(Player player, PlayerClass playerClass){
        LPlayer lPlayer = get(player.getUniqueId());
        if(lPlayer == null) return;
        lPlayer.setPlayerClass(playerClass);
        player.sendMessage(Utils.toPrefix(LPClasses.a.getMessage("Actions.class_choose").replace("%class%", playerClass.getName())));
    }

    public static void openAGUI(Player player){
        Inventory gui = Bukkit.createInventory(null, 54, Vars.GUI.A.NAME);

        ItemStack border_item = Utils.setTag(new ItemStack(Vars.GUI.A.ITEM_2_MATERIAL), "lpcGUI", "lpc1");
        ItemMeta meta = border_item.getItemMeta();
        meta.setDisplayName(Vars.GUI.A.ITEM_2_NAME);
        meta.setLore(Vars.GUI.A.ITEM_2_LORE);
        border_item.setItemMeta(meta.clone());

        List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53).
                forEach(i -> gui.setItem(i, border_item));

        int counter = 0;
        for(PlayerClass pc : PlayerClasses.getAll()){
            ItemStack class_item = new ItemStack(pc.getGUIMaterial());
            meta = class_item.getItemMeta();
            meta.setDisplayName(Vars.GUI.A.ITEM_1_NAME.replace("%name%", pc.getName()));

            List<String> lore = new ArrayList<>();
            for(String s1 : Vars.GUI.A.ITEM_1_LORE) {
                if(s1.contains("%custom_description%")) {
                    for(String s2 : pc.getDescription()){
                        lore.add(Utils.toColor(s2));
                    }
                    continue;
                }
                s1 = s1.replace("%hp%", ""+(20+pc.getHealth())).
                        replace("%damage%", getMinusedValue((int) (pc.getStrength()*Vars.Multipliers.STRENGTH*100))).
                        replace("%speed%", getMinusedValue((int) (pc.getSpeed()*Vars.Multipliers.SPEED*100))).
                        replace("%dexterity%", getMinusedValue((int) (pc.getDexterity()*Vars.Multipliers.DEXTERITY*100))).
                        replace("%magic%", getMinusedValue((int) (pc.getMagic()*Vars.Multipliers.MAGIC*100)));
                lore.add(Utils.toColor(s1));
            }
            meta.setLore(lore);

            class_item.setItemMeta(meta);

            gui.setItem(10+counter+counter/7*2, Utils.setTag(Utils.setTag(class_item, "lpcGUI", "lpc1"), "lokiclass", pc.getId()));
            counter++;
        }
        player.openInventory(gui);
    }
    public static void openBGUI(Player player, Inventory gui){
        LPlayer lp = LPlayers.get(player);

        ItemStack item = new ItemStack(Vars.GUI.B.ITEM_1_MATERIAL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.toColor(Vars.GUI.B.ITEM_1_NAME.
                replace("%lvl%", ""+lp.getSpeedLvl()).
                replace("%lvl1%", ""+(lp.getSpeedLvl()+1))));
        List<String> lore = new ArrayList<>();
        for(String s : Vars.GUI.B.ITEM_1_LORE){
            lore.add(Utils.toColor(s.
                    replace("%lvl%", ""+lp.getSpeedLvl()).
                    replace("%lvl1%", ""+(lp.getSpeedLvl()+1))));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        gui.setItem(18, Utils.setTag(Utils.setTag(item.clone(), "lpcGUI", "lpc2"), "upgrade", "speed"));

        item = new ItemStack(Vars.GUI.B.ITEM_2_MATERIAL);
        meta = item.getItemMeta();
        meta.setDisplayName(Utils.toColor(Vars.GUI.B.ITEM_2_NAME.
                replace("%lvl%", ""+lp.getStrengthLvl()).
                replace("%lvl1%", ""+(lp.getStrengthLvl()+1))));
        lore = new ArrayList<>();
        for(String s : Vars.GUI.B.ITEM_2_LORE){
            lore.add(Utils.toColor(s.
                    replace("%lvl%", ""+lp.getStrengthLvl()).
                    replace("%lvl1%", ""+(lp.getStrengthLvl()+1))));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        gui.setItem(20, Utils.setTag(Utils.setTag(item.clone(), "lpcGUI", "lpc2"), "upgrade", "strength"));

        item = new ItemStack(Vars.GUI.B.ITEM_3_MATERIAL);
        meta = item.getItemMeta();
        meta.setDisplayName(Utils.toColor(Vars.GUI.B.ITEM_3_NAME.
                replace("%lvl%", ""+lp.getDexterityLvl()).
                replace("%lvl1%", ""+(lp.getDexterityLvl()+1))));
        lore = new ArrayList<>();
        for(String s : Vars.GUI.B.ITEM_3_LORE){
            lore.add(Utils.toColor(s.
                    replace("%lvl%", ""+lp.getDexterityLvl()).
                    replace("%lvl1%", ""+(lp.getDexterityLvl()+1))));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        gui.setItem(22, Utils.setTag(Utils.setTag(item.clone(), "lpcGUI", "lpc2"), "upgrade", "dexterity"));

        item = new ItemStack(Vars.GUI.B.ITEM_4_MATERIAL);
        meta = item.getItemMeta();
        meta.setDisplayName(Utils.toColor(Vars.GUI.B.ITEM_4_NAME.
                replace("%lvl%", ""+lp.getHealthLvl()).
                replace("%lvl1%", ""+(lp.getHealthLvl()+1))));
        lore = new ArrayList<>();
        for(String s : Vars.GUI.B.ITEM_4_LORE){
            lore.add(Utils.toColor(s.
                    replace("%lvl%", ""+lp.getHealthLvl()).
                    replace("%lvl1%", ""+(lp.getHealthLvl()+1))));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        gui.setItem(24, Utils.setTag(Utils.setTag(item.clone(), "lpcGUI", "lpc2"), "upgrade", "health"));

        item = new ItemStack(Vars.GUI.B.ITEM_5_MATERIAL);
        meta = item.getItemMeta();
        meta.setDisplayName(Utils.toColor(Vars.GUI.B.ITEM_5_NAME.
                replace("%lvl%", ""+lp.getMagicLvl()).
                replace("%lvl1%", ""+(lp.getMagicLvl()+1))));
        lore = new ArrayList<>();
        for(String s : Vars.GUI.B.ITEM_5_LORE){
            lore.add(Utils.toColor(s.
                    replace("%lvl%", ""+lp.getMagicLvl()).
                    replace("%lvl1%", ""+(lp.getMagicLvl
                            ()+1))));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        gui.setItem(26, Utils.setTag(Utils.setTag(item.clone(), "lpcGUI", "lpc2"), "upgrade", "magic"));



        ItemStack border = Utils.setTag(new ItemStack(Vars.GUI.B.ITEM_6_MATERIAL), "lpcGUI", "lpc2");
        meta = border.getItemMeta();
        meta.setDisplayName(Utils.toColor(Vars.GUI.B.ITEM_6_NAME));
        lore = new ArrayList<>();
        for(String s : Vars.GUI.B.ITEM_6_LORE){
            lore.add(Utils.toColor(s));
        }
        meta.setLore(lore);
        border.setItemMeta(meta);
        List.of(0, 1, 2, 3 , 4, 5, 6, 7, 8, 36, 37, 38, 39, 40, 41, 42, 43, 44).forEach(i -> gui.setItem(i, border));
    }
    private static String getMinusedValue(int val){
        if(val < 0) {
            return Utils.toColor("&c-"+val);
        } else if (val > 0) {
            return Utils.toColor("&a+"+val);
        }
        return Utils.toColor("&7+"+val);
    }
}
