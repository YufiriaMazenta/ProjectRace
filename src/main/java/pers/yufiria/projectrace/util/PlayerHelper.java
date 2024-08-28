package pers.yufiria.projectrace.util;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PlayerHelper {

    public static void resetRaceEffects(Player player) {
        AttributeHelper.removePlayerAllRaceAttribute(player);
        PlayerHelper.resetFly(player);
    }

    public static void resetFly(Player player) {
        if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) {
            return;
        }
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setFlySpeed(0.1f);
    }

    public static boolean isOnline(Player player) {
        if (player == null)
            return false;
        return player.isOnline();
    }

    public static boolean isOffline(Player player) {
        return !isOnline(player);
    }

}
