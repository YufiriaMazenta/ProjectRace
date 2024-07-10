package pers.yufiria.projectrace;

import crypticlib.lifecycle.BukkitEnabler;
import crypticlib.lifecycle.BukkitReloader;
import crypticlib.lifecycle.annotation.OnEnable;
import crypticlib.lifecycle.annotation.OnReload;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;
import pers.yufiria.projectrace.config.Configs;
import pers.yufiria.projectrace.data.DataAccessor;
import pers.yufiria.projectrace.data.impl.YamlDataAccessor;
import pers.yufiria.projectrace.race.Race;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@OnReload
@OnEnable
public enum RaceManager implements BukkitReloader, BukkitEnabler {

    INSTANCE;
    private DataAccessor dataAccessor = YamlDataAccessor.INSTANCE;
    private RaceTicker raceTicker;
    private final Map<String, Race> raceMap = new ConcurrentHashMap<>();
    private final Map<UUID, PlayerRace> playerRaceCacheMap = new HashMap<>();

    /**
     * 获取玩家的种族, 可能因为缓存未加载导致获取到null
     * @param uuid 玩家的uuid
     * @return 玩家的种族
     */
    @Nullable
    public PlayerRace getPlayerRace(UUID uuid) {
        return playerRaceCacheMap.get(uuid);
    }

    public void setPlayerRace(UUID uuid, PlayerRace playerRace) {
        playerRaceCacheMap.put(uuid, playerRace);
    }

    public void removePlayerRace(UUID uuid) {
        playerRaceCacheMap.remove(uuid);
    }

    @Nullable
    public Race getRace(String id) {
        return raceMap.get(id);
    }

    public RaceManager registerRace(Race race) {
        raceMap.put(race.id(), race);
        return this;
    }

    public Map<String, Race> raceMap() {
        return raceMap;
    }

    public Map<UUID, PlayerRace> playerRaceCacheMap() {
        return playerRaceCacheMap;
    }

    public DataAccessor dataAccessor() {
        return dataAccessor;
    }

    @Override
    public void reload(Plugin plugin) {
        if (raceTicker != null) {
            raceTicker.cancel();
        }
        raceTicker = new RaceTicker();
        raceTicker.runTaskTimer(plugin, 0, Configs.raceTickDuration.value());
    }

    @Override
    public void enable(Plugin plugin) {
        reload(plugin);
    }

}
