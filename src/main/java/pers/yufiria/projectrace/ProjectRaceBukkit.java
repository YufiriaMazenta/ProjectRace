package pers.yufiria.projectrace;

import crypticlib.BukkitPlugin;
import org.bukkit.Bukkit;
import pers.yufiria.projectrace.papi.RaceExpansion;

public class ProjectRaceBukkit extends BukkitPlugin {

    public static ProjectRaceBukkit INSTANCE;

    @Override
    public void enable() {
        INSTANCE = this;
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            RaceExpansion.INSTANCE.register();
        }
    }

    @Override
    public void disable() {
        //TODO
    }

}