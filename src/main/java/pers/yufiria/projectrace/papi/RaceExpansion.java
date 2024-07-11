package pers.yufiria.projectrace.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.yufiria.projectrace.PlayerRace;
import pers.yufiria.projectrace.ProjectRaceBukkit;
import pers.yufiria.projectrace.RaceManager;
import pers.yufiria.projectrace.config.Configs;

public class RaceExpansion extends PlaceholderExpansion {

    public static final RaceExpansion INSTANCE = new RaceExpansion();

    private RaceExpansion() {}

    @Override
    public @NotNull String getIdentifier() {
        return "race";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Yufiria";
    }

    @Override
    public @NotNull String getVersion() {
        return ProjectRaceBukkit.INSTANCE.getDescription().getVersion();
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return null;
        }
        PlayerRace playerRace = RaceManager.INSTANCE.getPlayerRace(player.getUniqueId());
        switch (params) {
            case "race_id" -> {
                return playerRace != null ? playerRace.raceId() : "";
            }
            case "race_name" -> {
                return playerRace != null ? playerRace.race().name() : "";
            }
            case "race_level" -> {
                return playerRace != null ? playerRace.raceLevel() + "" : "0";
            }
            case "race_exp" -> {
                return playerRace != null ? String.format(Configs.raceExpPapiFormat.value(), playerRace.raceExp()) : "0";
            }
            default -> {
                return null;
            }
        }
    }
}
