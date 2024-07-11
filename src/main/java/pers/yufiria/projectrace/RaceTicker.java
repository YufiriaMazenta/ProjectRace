package pers.yufiria.projectrace;

import crypticlib.scheduler.CrypticLibRunnable;
import org.apache.logging.log4j.util.BiConsumer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pers.yufiria.projectrace.race.Race;

public class RaceTicker extends CrypticLibRunnable {

    @Override
    public void run() {
        RaceManager.INSTANCE.playerRaceCacheMap().forEach(
            (uuid, playerRace) -> {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null)
                    return;
                if (!player.isOnline())
                    return;
                if (player.isDead())
                    return;
                Race race = playerRace.race();
                race.applyAttribute2Player(player);
                BiConsumer<Player, PlayerRace> raceTask = race.raceTask();
                if (raceTask != null) {
                    raceTask.accept(player, playerRace);
                }
            }
        );
    }



}
