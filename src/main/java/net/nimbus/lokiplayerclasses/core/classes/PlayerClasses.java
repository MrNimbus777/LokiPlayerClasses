package net.nimbus.lokiplayerclasses.core.classes;

import net.nimbus.lokiplayerclasses.LPClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerClasses {
    private static final Map<String, PlayerClass> map = new HashMap<>();

    public static List<PlayerClass> getAll(){
        return new ArrayList<>(map.values());
    }

    public static void register(PlayerClass playerClass){
        map.put(playerClass.getId(), playerClass);
    }
    public static void unregister(PlayerClass playerClass){
        map.remove(playerClass.getId());
    }
    public static boolean exists(String id){
        return map.containsKey(id);
    }
    public static void clearRam(){
        getAll().forEach(PlayerClasses::unregister);
    }

    public static PlayerClass get(String id){
        return map.getOrDefault(id, null);
    }
    public static PlayerClass getNonNull(String id){
        return map.getOrDefault(id, LPClasses.defaultClass);
    }
}
