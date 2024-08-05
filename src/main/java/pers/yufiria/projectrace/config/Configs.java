package pers.yufiria.projectrace.config;

import crypticlib.config.ConfigHandler;
import crypticlib.config.node.impl.bukkit.*;

@ConfigHandler(path = "config.yml")
public class Configs {

    public static final IntConfig raceTickPeriod = new IntConfig("race-tick-period", 15, "种族任务的tick间隔");
    public static final StringConfig raceExpPapiFormat = new StringConfig("race-exp-papi-format", "%.2f", "种族经验在papi变量里的格式");
    public static final StringConfig attributeNamespace = new StringConfig("attribute-namespace", "projectrace");

}
