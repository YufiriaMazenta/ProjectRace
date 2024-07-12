package pers.yufiria.projectrace.event;

import org.bukkit.event.Event;
import pers.yufiria.projectrace.PlayerRace;

public abstract class RaceEvent extends Event {

    private final PlayerRace playerRace;

    public RaceEvent(boolean isAsync, PlayerRace playerRace) {
        super(isAsync);
        this.playerRace = playerRace;
    }

    public RaceEvent(PlayerRace playerRace) {
        this.playerRace = playerRace;
    }

    public PlayerRace playerRace() {
        return playerRace;
    }

}
