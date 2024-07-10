package pers.yufiria.projectrace;

import org.jetbrains.annotations.NotNull;
import pers.yufiria.projectrace.exception.RaceException;
import pers.yufiria.projectrace.race.Race;

import java.util.Objects;

public class PlayerRace {

    private String raceId;
    private int raceLevel;

    public PlayerRace(String raceId) {
        this(raceId, 0);
    }

    public PlayerRace(String raceId, int raceLevel) {
        if (RaceManager.INSTANCE.getRace(raceId) == null) {
            throw new RaceException("Unknown race " + raceId);
        }
        this.raceId = raceId;
        this.raceLevel = raceLevel;
    }

    public String raceId() {
        return raceId;
    }

    public PlayerRace setRaceId(String raceId) {
        this.raceId = raceId;
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
        return this.raceLevel;
    }

    public @NotNull Race race() {
        Race race = RaceManager.INSTANCE.getRace(raceId);
        if (race == null) {
            throw new IllegalArgumentException("Can not find player's race");
        }
        return race;
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
