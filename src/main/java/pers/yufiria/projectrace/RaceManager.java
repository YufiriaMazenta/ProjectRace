package pers.yufiria.projectrace;

import crypticlib.CrypticLibBukkit;
import crypticlib.lifecycle.BukkitEnabler;
import crypticlib.lifecycle.BukkitReloader;
import crypticlib.lifecycle.annotation.OnEnable;
import crypticlib.lifecycle.annotation.OnReload;
import crypticlib.listener.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;
import pers.yufiria.projectrace.config.Configs;
import pers.yufiria.projectrace.data.DataAccessor;
import pers.yufiria.projectrace.data.impl.YamlDataAccessor;
import pers.yufiria.projectrace.race.Race;
import pers.yufiria.projectrace.util.PlayerHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@OnReload
@OnEnable
@EventListener
public enum RaceManager implements BukkitReloader, BukkitEnabler, Listener {

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

    public void setPlayerRaceCache(UUID uuid, PlayerRace playerRace) {
        playerRaceCacheMap.put(uuid, playerRace);
    }

    public void removePlayerRaceCache(UUID uuid) {
        playerRaceCacheMap.remove(uuid);
    }

    public void removePlayerRace(UUID uuid) {
        removePlayerRaceCache(uuid);
        dataAccessor.removePlayerRace(uuid);
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
        raceTicker.runTaskTimer(plugin, 0, Configs.raceTickPeriod.value());
    }

    @Override
    public void enable(Plugin plugin) {
        reload(plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        dataAccessor.loadPlayerRace(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        //一分钟后清除缓存
        CrypticLibBukkit.scheduler().runTaskLater(
            ProjectRaceBukkit.INSTANCE,
            () -> {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null || !player.isOnline()) {
                    removePlayerRaceCache(uuid);
                }
            },
            1200
        );
    }

}
