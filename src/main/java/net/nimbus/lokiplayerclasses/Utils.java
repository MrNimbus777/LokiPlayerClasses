package net.nimbus.lokiplayerclasses;

import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    static Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
    public static String toColor(String str){
        Matcher match = pattern.matcher(str);
        while (match.find()) {
            String color = str.substring(match.start() + 1, match.end());
            str = str.replace("&" + color, ChatColor.of(color) + "");
            match = pattern.matcher(str);
        }
        return str.replace("&", "\u00a7");
    }
    public static String toPrefix(String s){
        return Vars.PREFIX+toColor(s);
    }
    public static ItemStack setTag(ItemStack item, String key, String value){
        try {
            Class<?> clazz = Class.forName("org.bukkit.craftbukkit." + LPClasses.a.version + ".inventory.CraftItemStack");
            net.minecraft.world.item.ItemStack nmsItem = (net.minecraft.world.item.ItemStack) clazz.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
            CompoundTag tag = nmsItem.hasTag() ? nmsItem.getTag() : new CompoundTag();
            tag.putString(key, value);
            nmsItem.setTag(tag);
            return (ItemStack) clazz.getMethod("asBukkitCopy", net.minecraft.world.item.ItemStack.class).invoke(null, nmsItem);
        } catch (Exception e) {
            e.printStackTrace();
            return item;
        }
    }
    public static String readTag(ItemStack item, String key){
        try {
            Class<?> clazz = Class.forName("org.bukkit.craftbukkit." + LPClasses.a.version + ".inventory.CraftItemStack");
            net.minecraft.world.item.ItemStack nmsItem = (net.minecraft.world.item.ItemStack) clazz.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
            CompoundTag tag = nmsItem.hasTag() ? nmsItem.getTag() : new CompoundTag();
            return tag.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public static void addPermission(UUID player, String permission){
        try {
            User user = LPClasses.a.lp.getUserManager().loadUser(player).get();
            if(user.getNodes().stream().map(Node::getKey).toList().contains(permission)) return;
            user.data().add(Node.builder(permission).build());
            LPClasses.a.lp.getUserManager().saveUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void removePermission(UUID player, String permission) {
        try {
            User user = LPClasses.a.lp.getUserManager().loadUser(player).get();
            user.data().remove(Node.builder(permission).build());
            LPClasses.a.lp.getUserManager().saveUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ChatColor[] createGradient(ChatColor from, ChatColor to, int size){
        ChatColor[] gradient = new ChatColor[size];
        gradient[0] = from;
        gradient[size-1] = to;
        int r1 = from.getColor().getRed();
        int g1 = from.getColor().getGreen();
        int b1 = from.getColor().getBlue();
        double rD = (to.getColor().getRed() - r1) / (double) (size - 2);
        double gD = (to.getColor().getGreen() - g1) / (double) (size - 2);
        double bD = (to.getColor().getBlue() - b1) / (double) (size - 2);
        for(int i = 1; i < size-1; i++) {
            gradient[i] = ChatColor.of(new Color((int) (r1 + i * rD), (int) (g1 + i * gD), (int) (b1 + i * bD)));
        }
        return gradient;
    }
    public static String getGradientText(String s, ChatColor from, ChatColor to) {
        ChatColor[] gradient = createGradient(from, to, s.length());
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < s.length(); i++){
            result.append(gradient[i]).append(s.toCharArray()[i]);
        }
        return result.toString();
    }
}
