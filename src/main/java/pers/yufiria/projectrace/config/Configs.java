package pers.yufiria.projectrace.config;

import crypticlib.config.ConfigHandler;
import crypticlib.config.node.impl.bukkit.ConfigSectionConfig;
import crypticlib.config.node.impl.bukkit.DoubleConfig;
import crypticlib.config.node.impl.bukkit.IntConfig;
import crypticlib.config.node.impl.bukkit.StringConfig;

@ConfigHandler(path = "config.yml")
public class Configs {

    public static final IntConfig raceTickDuration = new IntConfig("race-tick-duration", 1);

    public static final StringConfig vampireName = new StringConfig("vampire.name", "&d吸血鬼");
    public static final IntConfig vampireMaxLevel = new IntConfig("vampire.max-level", 10);
    public static final ConfigSectionConfig vampireMaxHealthModifier = new ConfigSectionConfig("vampire.attribute-modifiers.max-health");
    public static final DoubleConfig vampireSuckingRate = new DoubleConfig("vampire.sucking-rate", 0.25);
}
