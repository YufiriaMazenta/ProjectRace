package pers.yufiria.projectrace;

import crypticlib.BukkitPlugin;
import crypticlib.annotation.AnnotationProcessor;

public class ProjectRaceBukkit extends BukkitPlugin {

    public static ProjectRaceBukkit INSTANCE;

    @Override
    public void enable() {
        INSTANCE = this;

    }

    @Override
    public void disable() {
        //TODO
    }

}