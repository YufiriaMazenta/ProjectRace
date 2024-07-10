package pers.yufiria.projectrace;

import crypticlib.chat.BukkitMsgSender;
import crypticlib.scheduler.CrypticLibRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RaceTicker extends CrypticLibRunnable {

    @Override
    public void run() {
        RaceManager.INSTANCE.playerRaceCacheMap().forEach(
            (uuid, playerRace) -> {
                BukkitMsgSender.INSTANCE.debug("tick " + uuid);
                Player player = Bukkit.getPlayer(uuid);
                if (player == null)
                    return;
                BukkitMsgSender.INSTANCE.debug("tick " + player.getName());
                playerRace.race().applyAttribute2Player(player);
            }
        );
    }
}
