package net.nimbus.lokiplayerclasses.core.abilities;

import net.nimbus.lokiplayerclasses.LPClasses;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Activation {

    public static ClickType convert(Action action){
        return switch (action) {
            case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> ClickType.LEFT;
            case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> ClickType.RIGHT;
            default -> null;
        };
    }
    List<ClickType> pattern;
    public Activation(List<ClickType> pattern){
        this.pattern = pattern;
    }

    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj instanceof Activation activation) {
            if(this.getPattern().size() != activation.getPattern().size()) return false;
            for(int i = 0; i < this.getPattern().size(); i++) {
                if(this.getPattern().get(i) != activation.getPattern().get(i)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    public boolean isKey(Activation activation){
        if(this.getPattern().isEmpty()) return false;
        if(this.getPattern().size() > activation.getPattern().size()) return false;
        if(this.equals(activation)) return true;
        int delta = activation.getPattern().size() - this.getPattern().size();
        for(int i = this.getPattern().size()-1; i >= 0; i--){
            if(this.getPattern().get(i) !=
                    activation.getPattern().get(i+delta)) return false;
        }
        return true;
    }
    public List<ClickType> getPattern() {
        return pattern;
    }

    public void clearPattern() {
        this.pattern.clear();
    }

    public void addClick(ClickType click){
        pattern.add(click);
        run();
    }
    public void run(){
        new BukkitRunnable(){
            final int size = Activation.this.getPattern().size();
            @Override
            public void run() {
                if(size >= Activation.this.getPattern().size()) {
                    Activation.this.clearPattern();
                }
            }
        }.runTaskLater(LPClasses.a, 20);
    }
}
