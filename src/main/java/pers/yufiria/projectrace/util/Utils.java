package pers.yufiria.projectrace.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Utils {

    public static @NotNull List<String> getPlayerNames() {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
    }

}
