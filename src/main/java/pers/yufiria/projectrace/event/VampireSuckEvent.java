package pers.yufiria.projectrace.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import pers.yufiria.projectrace.PlayerRace;

public class VampireSuckEvent extends RaceEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private double raceAmount;
    private boolean cancelled = false;
    private SuckType suckType;

    public VampireSuckEvent(PlayerRace playerRace, double raceAmount, SuckType suckType) {
        super(playerRace);
        this.raceAmount = raceAmount;
        this.suckType = suckType;
    }

    public double raceAmount() {
        return raceAmount;
    }

    public VampireSuckEvent setRaceAmount(double raceAmount) {
        this.raceAmount = raceAmount;
        return this;
    }

    public SuckType suckType() {
        return suckType;
    }

    public VampireSuckEvent setSuckType(SuckType suckType) {
        this.suckType = suckType;
        return this;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public enum SuckType {
        FOOD_LEVEL, HEALTH, SATURATION
    }

}
