package net.nimbus.lokiplayerclasses.core;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.nimbus.lokiplayerclasses.core.players.LPlayer;
import net.nimbus.lokiplayerclasses.core.players.LPlayers;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Placeholders extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "lpc";
    }

    @Override
    public @NotNull String getAuthor() {
        return "MrNimbus777";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        switch (params) {
            case "player_rank" : {
                if(player.getPlayer() != null) {
                    LPlayer lp = LPlayers.get(player.getUniqueId());
                    return lp.getRank().getName();
                }
                return "";
            }
            case "player_class" : {
                if(player.getPlayer() != null) {
                    LPlayer lp = LPlayers.get(player.getUniqueId());
                    return lp.getPlayerClassNonNull().getName();
                }
                return "";
            }
            case "player_exp" : {
                if(player.getPlayer() != null) {
                    LPlayer lp = LPlayers.get(player.getUniqueId());
                    return lp.getExperience()+"";
                }
                return "";
            }
            default: return  "";
        }
    }
}
