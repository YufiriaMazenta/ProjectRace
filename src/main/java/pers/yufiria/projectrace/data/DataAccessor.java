package pers.yufiria.projectrace.data;

import pers.yufiria.projectrace.PlayerRace;

import java.util.UUID;

public interface DataAccessor {

    void setPlayerRace(UUID uuid, PlayerRace playerRace);

    void getPlayerRace(UUID uuid);

    void changePlayerRaceLevel(UUID uuid, int level);

    void removePlayerRace(UUID uuid);

}
