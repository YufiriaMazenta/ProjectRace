package pers.yufiria.projectrace.config;

import crypticlib.config.ConfigHandler;
import crypticlib.config.node.impl.bukkit.*;
import org.bukkit.Color;
import pers.yufiria.projectrace.config.node.ColorConfig;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@ConfigHandler(path = "config.yml")
public class Configs {

    public static final IntConfig raceTickDuration = new IntConfig("race-tick-duration", 1, "种族任务的tick间隔");
    public static final StringConfig raceExpPapiFormat = new StringConfig("race-exp-papi-format", "%.2f", "种族经验在papi变量里的格式");

    public static final StringConfig vampireName = new StringConfig("vampire.name", "&d吸血鬼", "吸血鬼的种族名字");
    public static final IntConfig vampireMaxLevel = new IntConfig("vampire.max-level", 10, "吸血鬼的种族最大等级");
    public static final ConfigSectionConfig vampireMaxHealthModifier = new ConfigSectionConfig(
        "vampire.attribute-modifiers.max-health",
        ((Supplier<Map<String, Object>>) () -> {
            Map<String, Object> map = new ConcurrentHashMap<>();
            for (int i = 0; i < 11; i++) {
                map.put(i + "", 2 * i);
            }
            return map;
        }).get(),
        "吸血鬼的生命提升配置"
    );
    public static final ConfigSectionConfig vampireSuckingRate = new ConfigSectionConfig(
        "vampire.sucking-rate",
        ((Supplier<Map<String, Object>>) () -> {
            Map<String, Object> map = new ConcurrentHashMap<>();
            for (int i = 0; i < 11; i++) {
                map.put(i + "", 0.25 + 0.01 * i);
            }
            return map;
        }).get(),
        "吸血鬼的吸取倍率配置"
    );
    public static final ConfigSectionConfig vampireLevelUpExp = new ConfigSectionConfig(
        "vampire.level-up-exp",
        ((Supplier<Map<String, Object>>) () -> {
            Map<String, Object> map = new ConcurrentHashMap<>();
            for (int i = 0; i < 10; i++) {
                map.put(i + "", 100 + 10 * (i + 1));
            }
            return map;
        }).get(),
        "吸血鬼的升级经验配置"
    );

    public static final StringConfig vampireSuckingParticleFoolLevelType = new StringConfig(
        "vampire.sucking-particle.fool-level.type",
        "GLOW_SQUID_INK",
        "吸血鬼吸取饱食度时的特效配置"
    );
    public static final ColorConfig vampireSuckingParticleFoodLevelColor = new ColorConfig("vampire.sucking-particle.fool-level.color", Color.ORANGE);
    public static final DoubleConfig vampireSuckingParticleFoodLevelCenterLocArgsX = new DoubleConfig("vampire.sucking-particle.fool-level.center-loc-args.x", 0.85);
    public static final DoubleConfig vampireSuckingParticleFoodLevelCenterLocArgsY = new DoubleConfig("vampire.sucking-particle.fool-level.center-loc-args.y", 0.85);
    public static final DoubleConfig vampireSuckingParticleFoodLevelCenterLocArgsZ = new DoubleConfig("vampire.sucking-particle.fool-level.center-loc-args.z", 0.85);

    public static final StringConfig vampireSuckingParticleHealthType = new StringConfig(
        "vampire.sucking-particle.health.type",
        "GLOW_SQUID_INK",
        "吸血鬼吸取生命时的特效配置"
    );
    public static final ColorConfig vampireSuckingParticleHealthColor = new ColorConfig("vampire.sucking-particle.health.color", Color.RED);
    public static final DoubleConfig vampireSuckingParticleHealthCenterLocArgsX = new DoubleConfig("vampire.sucking-particle.health.center-loc-args.x", 0.85);
    public static final DoubleConfig vampireSuckingParticleHealthCenterLocArgsY = new DoubleConfig("vampire.sucking-particle.health.center-loc-args.y", 0.85);
    public static final DoubleConfig vampireSuckingParticleHealthCenterLocArgsZ = new DoubleConfig("vampire.sucking-particle.health.center-loc-args.z", 0.85);

    public static final StringConfig vampireSuckingParticleSaturationType = new StringConfig(
        "vampire.sucking-particle.saturation.type",
        "GLOW_SQUID_INK",
        "吸血鬼吸取饱和度时的特效配置"
    );
    public static final ColorConfig vampireSuckingParticleSaturationColor = new ColorConfig("vampire.sucking-particle.saturation.color", Color.YELLOW);
    public static final DoubleConfig vampireSuckingParticleSaturationCenterLocArgsX = new DoubleConfig("vampire.sucking-particle.saturation.center-loc-args.x", 0.85);
    public static final DoubleConfig vampireSuckingParticleSaturationCenterLocArgsY = new DoubleConfig("vampire.sucking-particle.saturation.center-loc-args.y", 0.85);
    public static final DoubleConfig vampireSuckingParticleSaturationCenterLocArgsZ = new DoubleConfig("vampire.sucking-particle.saturation.center-loc-args.z", 0.85);

    public static final StringListConfig vampireNightEffects = new StringListConfig(
        "vampire.night-effects",
        List.of("night_vision,0,220", "strength,0,40","speed,0,40"),
        "吸血鬼的夜晚buff配置"
    );

}
