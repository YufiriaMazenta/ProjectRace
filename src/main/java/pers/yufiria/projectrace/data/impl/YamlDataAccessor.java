package pers.yufiria.projectrace.data.impl;


import crypticlib.config.BukkitConfigWrapper;
import crypticlib.lifecycle.BukkitEnabler;
import crypticlib.lifecycle.annotation.OnEnable;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import pers.yufiria.projectrace.PlayerRace;
import pers.yufiria.projectrace.RaceManager;
import pers.yufiria.projectrace.data.DataAccessor;

import java.util.UUID;

@OnEnable
public enum YamlDataAccessor implements DataAccessor, BukkitEnabler {

    INSTANCE;

    private BukkitConfigWrapper playerRaceConfig;

    @Override
    public void setPlayerRace(UUID uuid, PlayerRace playerRace) {
        String uuidStr = uuid.toString();
        playerRaceConfig.set(uuidStr + ".race-id", playerRace.raceId());
        playerRaceConfig.set(uuidStr + ".race-level", playerRace.raceLevel());
        playerRaceConfig.saveConfig();
    }

    @Override
    public void loadPlayerRace(UUID uuid) {
        String uuidStr = uuid.toString();
        YamlConfiguration config = playerRaceConfig.config();
        String raceId = config.getString(uuidStr + ".race-id");
        if (raceId == null) {
            return;
        }
        int raceLevel = config.getInt(uuidStr + ".race-level");
        RaceManager.INSTANCE.setPlayerRaceCache(uuid, new PlayerRace(uuid, raceId, raceLevel));
    }

    @Override
    public void changePlayerRaceLevel(UUID uuid, int level) {
        String uuidStr = uuid.toString();
        YamlConfiguration config = playerRaceConfig.config();
        if (!config.contains(uuidStr)) {
            return;
        }
        config.set(uuidStr + ".race-level", level);
        playerRaceConfig.saveConfig();
    }

    @Override
    public void removePlayerRace(UUID uuid) {
        String uuidStr = uuid.toString();
        YamlConfiguration config = playerRaceConfig.config();
        config.set(uuidStr, null);
        playerRaceConfig.saveConfig();
    }

    @Override
    public void changePlayerRaceExp(UUID uuid, double raceExp) {
        String uuidStr = uuid.toString();
        YamlConfiguration config = playerRaceConfig.config();
        if (!config.contains(uuidStr)) {
            return;
        }
        config.set(uuidStr + ".race-exp", raceExp);
        playerRaceConfig.saveConfig();
    }

    @Override
    public void enable(Plugin plugin) {
        playerRaceConfig = new BukkitConfigWrapper(plugin, "data/player_race.yml");
        playerRaceConfig.saveDefaultConfigFile();
        playerRaceConfig.reloadConfig();
    }

}
