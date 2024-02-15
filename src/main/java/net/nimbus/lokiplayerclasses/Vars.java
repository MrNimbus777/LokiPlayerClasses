package net.nimbus.lokiplayerclasses;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Vars {

    public static String PREFIX;

    public static class Multipliers {
        public static double SPEED;
        public static double STRENGTH;
        public static double HEALTH;
        public static double DEXTERITY;
        public static double MAGIC;
    }
    public static class GUI{
        public static class A{
            public static String NAME;
            public static String ITEM_1_NAME;
            public static List<String> ITEM_1_LORE;
            public static String ITEM_2_NAME;
            public static Material ITEM_2_MATERIAL;
            public static List<String> ITEM_2_LORE;
        }
        public static class B{
            public static String NAME;
            public static String ITEM_1_NAME;
            public static Material ITEM_1_MATERIAL;
            public static List<String> ITEM_1_LORE;
            public static String ITEM_2_NAME;
            public static Material ITEM_2_MATERIAL;
            public static List<String> ITEM_2_LORE;
            public static String ITEM_3_NAME;
            public static Material ITEM_3_MATERIAL;
            public static List<String> ITEM_3_LORE;
            public static String ITEM_4_NAME;
            public static Material ITEM_4_MATERIAL;
            public static List<String> ITEM_4_LORE;
            public static String ITEM_5_NAME;
            public static Material ITEM_5_MATERIAL;
            public static List<String> ITEM_5_LORE;
            public static String ITEM_6_NAME;
            public static Material ITEM_6_MATERIAL;
            public static List<String> ITEM_6_LORE;

        }
    }

    public static void init(){
        PREFIX = Utils.toColor(LPClasses.a.getConfig().getString("Settings.prefix"));

        Multipliers.SPEED = LPClasses.a.getConfig().getDouble("Settings.multipliers.speed")/100.0;
        Multipliers.STRENGTH = LPClasses.a.getConfig().getDouble("Settings.multipliers.strength")/100.0;
        Multipliers.HEALTH = LPClasses.a.getConfig().getDouble("Settings.multipliers.health");
        Multipliers.DEXTERITY = LPClasses.a.getConfig().getDouble("Settings.multipliers.dexterity")/100.0;
        Multipliers.MAGIC = LPClasses.a.getConfig().getDouble("Settings.multipliers.magic")/100.0;

        GUI.A.NAME = Utils.toColor(LPClasses.a.getConfig().getString("GUI.class_choose.name"));
        GUI.A.ITEM_1_NAME = Utils.toColor(LPClasses.a.getConfig().getString("GUI.class_choose.class_item.name"));
        GUI.A.ITEM_1_LORE = LPClasses.a.getConfig().getStringList("GUI.class_choose.class_item.lore").stream().
                map(Utils::toColor).collect(Collectors.toList());
        GUI.A.ITEM_2_NAME = Utils.toColor(LPClasses.a.getConfig().getString("GUI.class_choose.border_item.name"));
        GUI.A.ITEM_2_MATERIAL = Material.getMaterial(
                LPClasses.a.getConfig().getString("GUI.class_choose.border_item.material", "stone").toUpperCase());
        GUI.A.ITEM_2_LORE = LPClasses.a.getConfig().getStringList("GUI.class_choose.border_item.lore").stream().
                map(Utils::toColor).collect(Collectors.toList());

        GUI.B.NAME = Utils.toColor(LPClasses.a.getConfig().getString("GUI.rank.name"));
        GUI.B.ITEM_1_NAME = Utils.toColor(LPClasses.a.getConfig().getString("GUI.rank.speed_upgrade.name"));
        GUI.B.ITEM_1_MATERIAL = Material.getMaterial(
                LPClasses.a.getConfig().getString("GUI.rank.speed_upgrade.material", "stone").toUpperCase());
        GUI.B.ITEM_1_LORE = LPClasses.a.getConfig().getStringList("GUI.rank.speed_upgrade.lore").stream().
                map(Utils::toColor).collect(Collectors.toList());
        GUI.B.ITEM_2_NAME = Utils.toColor(LPClasses.a.getConfig().getString("GUI.rank.strength_upgrade.name"));
        GUI.B.ITEM_2_MATERIAL = Material.getMaterial(
                LPClasses.a.getConfig().getString("GUI.rank.strength_upgrade.material", "stone").toUpperCase());
        GUI.B.ITEM_2_LORE = LPClasses.a.getConfig().getStringList("GUI.rank.strength_upgrade.lore").stream().
                map(Utils::toColor).collect(Collectors.toList());
        GUI.B.ITEM_3_NAME = Utils.toColor(LPClasses.a.getConfig().getString("GUI.rank.dexterity_upgrade.name"));
        GUI.B.ITEM_3_MATERIAL = Material.getMaterial(
                LPClasses.a.getConfig().getString("GUI.rank.dexterity_upgrade.material", "stone").toUpperCase());
        GUI.B.ITEM_3_LORE = LPClasses.a.getConfig().getStringList("GUI.rank.dexterity_upgrade.lore").stream().
                map(Utils::toColor).collect(Collectors.toList());
        GUI.B.ITEM_4_NAME = Utils.toColor(LPClasses.a.getConfig().getString("GUI.rank.health_upgrade.name"));
        GUI.B.ITEM_4_MATERIAL = Material.getMaterial(
                LPClasses.a.getConfig().getString("GUI.rank.health_upgrade.material", "stone").toUpperCase());
        GUI.B.ITEM_4_LORE = LPClasses.a.getConfig().getStringList("GUI.rank.health_upgrade.lore").stream().
                map(Utils::toColor).collect(Collectors.toList());
        GUI.B.ITEM_5_NAME = Utils.toColor(LPClasses.a.getConfig().getString("GUI.rank.magic_upgrade.name"));
        GUI.B.ITEM_5_MATERIAL = Material.getMaterial(
                LPClasses.a.getConfig().getString("GUI.rank.magic_upgrade.material", "stone").toUpperCase());
        GUI.B.ITEM_5_LORE = LPClasses.a.getConfig().getStringList("GUI.rank.magic_upgrade.lore").stream().
                map(Utils::toColor).collect(Collectors.toList());
        GUI.B.ITEM_6_NAME = Utils.toColor(LPClasses.a.getConfig().getString("GUI.rank.border_item.name"));
        GUI.B.ITEM_6_MATERIAL = Material.getMaterial(
                LPClasses.a.getConfig().getString("GUI.rank.border_item.material", "stone").toUpperCase());
        GUI.B.ITEM_6_LORE = LPClasses.a.getConfig().getStringList("GUI.rank.border_item.lore").stream().
                map(Utils::toColor).collect(Collectors.toList());
    }
}
