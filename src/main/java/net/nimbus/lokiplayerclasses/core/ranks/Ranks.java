package net.nimbus.lokiplayerclasses.core.ranks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ranks {
    private static final Map<String, Rank> map = new HashMap<>();
    private static final List<Rank> list = new ArrayList<>();
    public static List<Rank> getAll(){
        return list;
    }

    public static void register(Rank rank){
        map.put(rank.getId(), rank);
        list.add(rank);
    }
    public static void unregister(Rank rank){
        map.remove(rank.getId());
        list.remove(rank);
    }
    public static void clearRam(){
        map.clear();
        list.clear();
    }

    public static Rank get(String id){
        return map.getOrDefault(id, null);
    }
    public static Rank getNonNull(String id){
        return map.getOrDefault(id, list.get(0));
    }
}
