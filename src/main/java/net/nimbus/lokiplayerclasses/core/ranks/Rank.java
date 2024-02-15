package net.nimbus.lokiplayerclasses.core.ranks;


import net.nimbus.lokiplayerclasses.core.ranks.rewards.Reward;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Rank {
    private final String id;
    private final String name;
    private final int exp;
    private List<Reward> rewards;
    public Rank(String id, String name, int exp){
        this.id = id;
        this.name = name;
        this.exp = exp;
        this.rewards = new ArrayList<>();
    }
    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(obj instanceof Rank rank) {
            return rank.getId().equals(this.getId());
        }
        return false;
    }
    public Rank getNext(){
        int ord = Ranks.getAll().indexOf(this);
        if(ord+1 < Ranks.getAll().size()) return Ranks.getAll().get(ord+1);
        return null;
    }
    public void reward(Player player){
        rewards.forEach(r -> {
            r.reward(player);
            r.message(player);
        });
    }

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
    }

    public List<String> getRewards() {
        return rewards.stream().map(Reward::getMessage).toList();
    }

    public String getId(){
        return this.id;
    }
    public String getName() {
        return name;
    }
    public int getExp() {
        return exp;
    }
}
