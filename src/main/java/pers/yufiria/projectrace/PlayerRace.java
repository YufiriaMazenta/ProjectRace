package pers.yufiria.projectrace;

import org.jetbrains.annotations.NotNull;
import pers.yufiria.projectrace.exception.RaceException;
import pers.yufiria.projectrace.race.Race;

import java.util.Objects;
import java.util.UUID;

public class PlayerRace {

    private final UUID playerId;
    private String raceId;
    private int raceLevel;
    private double raceExp;

    public PlayerRace(UUID playerId, String raceId) {
        this(playerId, raceId, 0);
    }

    public PlayerRace(UUID playerId, String raceId, int raceLevel) {
        this(playerId, raceId, raceLevel, 0);
    }

    public PlayerRace(UUID playerId, String raceId, int raceLevel, double raceExp) {
        if (RaceManager.INSTANCE.getRace(raceId) == null) {
            throw new RaceException("Unknown race " + raceId);
        }
        this.playerId = playerId;
        this.raceExp = raceExp;
        this.raceLevel = raceLevel;
        this.raceId = raceId;
    }

    public UUID playerId() {
        return playerId;
    }

    public String raceId() {
        return raceId;
    }

    public PlayerRace setRaceId(String raceId) {
        this.raceId = raceId;
        setRaceExp(0);
        setRaceLevel(0);
        return this;
    }

    public int raceLevel() {
        return raceLevel;
    }

    /**
     * 设置玩家的种族等级
     * @param raceLevel 新等级
     * @return 设置成功的等级
     */
    public int setRaceLevel(int raceLevel) {
        int maxLevel = RaceManager.INSTANCE.getRace(raceId).maxLevel();
        this.raceLevel = Math.min(raceLevel, maxLevel);
        RaceManager.INSTANCE.dataAccessor().changePlayerRaceLevel(playerId, raceLevel);
        return this.raceLevel;
    }

    public @NotNull Race race() {
        Race race = RaceManager.INSTANCE.getRace(raceId);
        if (race == null) {
            throw new IllegalArgumentException("Can not find player's race");
        }
        return race;
    }

    public double raceExp() {
        return raceExp;
    }

    public double setRaceExp(double raceExp) {
        Race race = race();
        int level = raceLevel;
        int maxLevel = race.maxLevel();
        while (raceExp >= race.levelUpExp(level) && level < maxLevel) {
            raceExp -= race.levelUpExp(level);
            level ++;
        }
        setRaceLevel(level);
        this.raceExp = raceExp;
        RaceManager.INSTANCE.dataAccessor().changePlayerRaceExp(playerId, raceExp);
        return this.raceExp;
    }

    public double addRaceExp(double add) {
        return setRaceExp(raceExp + add);
    }

    public double subtractRaceExp(double subtract) {
        return setRaceExp(Math.max(0, raceExp - subtract));
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerRace that)) return false;

        return raceLevel == that.raceLevel && Objects.equals(raceId, that.raceId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(raceId);
        result = 31 * result + raceLevel;
        return result;
    }

}
