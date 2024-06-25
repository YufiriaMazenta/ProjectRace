package pers.yufiria.projectrace.config;

import crypticlib.config.ConfigHandler;
import crypticlib.config.entry.IntConfigEntry;
import crypticlib.config.entry.StringConfigEntry;

@ConfigHandler(path = "test.yml")
public class TestConfig {

    public static StringConfigEntry DRAGON_FIREBALL_PARTICLE = new StringConfigEntry("dragon_fireball_particle", "");
    public static IntConfigEntry DRAGON_FIREBALL_RADIUS = new IntConfigEntry("dragon_fireball_radius", 10);

}
