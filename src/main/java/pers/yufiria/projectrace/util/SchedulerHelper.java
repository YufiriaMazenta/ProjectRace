package pers.yufiria.projectrace.util;

import crypticlib.CrypticLibBukkit;
import pers.yufiria.projectrace.ProjectRaceBukkit;

public class SchedulerHelper {

    public static void async(Runnable task) {
        CrypticLibBukkit.scheduler().runTaskAsync(
            ProjectRaceBukkit.INSTANCE,
            task
        );
    }

    public static void sync(Runnable task) {
        CrypticLibBukkit.scheduler().runTask(
            ProjectRaceBukkit.INSTANCE,
            task
        );
    }

}
