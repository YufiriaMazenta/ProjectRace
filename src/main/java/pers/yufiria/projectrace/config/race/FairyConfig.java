package pers.yufiria.projectrace.config.race;

import crypticlib.config.ConfigHandler;
import crypticlib.config.node.impl.bukkit.ConfigSectionConfig;
import crypticlib.config.node.impl.bukkit.DoubleConfig;
import crypticlib.config.node.impl.bukkit.IntConfig;
import crypticlib.config.node.impl.bukkit.StringConfig;
import org.bukkit.Color;
import pers.yufiria.projectrace.config.node.ColorConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@ConfigHandler(path = "race/fairy.yml")
public class FairyConfig {

    public static final StringConfig name = new StringConfig("name", "&b妖精", "妖精的种族名字");
    public static final IntConfig maxLevel = new IntConfig("max-level", 10, "妖精的种族最大等级");
    public static final ConfigSectionConfig maxHealthModifier = new ConfigSectionConfig(
        "attribute-modifiers.max-health",
        ((Supplier<Map<String, Object>>) () -> {
            Map<String, Object> map = new ConcurrentHashMap<>();
            for (int i = 0; i < 11; i++) {
                map.put(i + "", -10 + 2 * i);
            }
            return map;
        }).get(),
        "妖精的生命提升配置"
    );
    public static final DoubleConfig blockInteractRangeModifier = new DoubleConfig("attribute-modifiers.block-interact-range", -1.5d);
    public static final DoubleConfig entityInteractRangeModifier = new DoubleConfig("attribute-modifiers.entity-interact-range", -1d);
    public static final DoubleConfig scaleModifier = new DoubleConfig("attribute-modifiers.scale", -0.55);
    public static final ConfigSectionConfig levelUpExp = new ConfigSectionConfig(
        "level-up-exp",
        ((Supplier<Map<String, Object>>) () -> {
            Map<String, Object> map = new ConcurrentHashMap<>();
            for (int i = 0; i < 10; i++) {
                map.put(i + "", 100 + 10 * (i + 1));
            }
            return map;
        }).get(),
        "妖精的升级经验配置"
    );

    public static final DoubleConfig flySpeed = new DoubleConfig("fly.speed", 0.05);
    public static final StringConfig flyParticleType = new StringConfig("fly.particle.type", "SCRAPE");
    public static final ColorConfig flyParticleColor = new ColorConfig("fly.particle.color", Color.AQUA);
    public static final DoubleConfig flyParticleRadius = new DoubleConfig("fly.particle.radius", 0.2);

}
