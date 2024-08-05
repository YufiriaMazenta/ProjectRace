package pers.yufiria.projectrace.config.race;

import crypticlib.config.ConfigHandler;
import crypticlib.config.node.impl.bukkit.*;
import org.bukkit.Color;
import pers.yufiria.projectrace.config.node.ColorConfig;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@ConfigHandler(path = "race/vampire.yml")
public class VampireConfig {

    public static final StringConfig name = new StringConfig("name", "&d吸血鬼", "吸血鬼的种族名字");
    public static final IntConfig maxLevel = new IntConfig("max-level", 10, "吸血鬼的种族最大等级");
    public static final ConfigSectionConfig maxHealthModifier = new ConfigSectionConfig(
        "attribute-modifiers.max-health",
        ((Supplier<Map<String, Object>>) () -> {
            Map<String, Object> map = new ConcurrentHashMap<>();
            for (int i = 0; i < 11; i++) {
                map.put(i + "", 2 * i);
            }
            return map;
        }).get(),
        "吸血鬼的生命提升配置"
    );
    public static final ConfigSectionConfig suckingRate = new ConfigSectionConfig(
        "sucking-rate",
        ((Supplier<Map<String, Object>>) () -> {
            Map<String, Object> map = new ConcurrentHashMap<>();
            for (int i = 0; i < 11; i++) {
                map.put(i + "", (0.25 + 0.01 * i));
            }
            return map;
        }).get(),
        "吸血鬼的吸取倍率配置"
    );
    public static final ConfigSectionConfig levelUpExp = new ConfigSectionConfig(
        "level-up-exp",
        ((Supplier<Map<String, Object>>) () -> {
            Map<String, Object> map = new ConcurrentHashMap<>();
            for (int i = 0; i < 10; i++) {
                map.put(i + "", 100 + 10 * (i + 1));
            }
            return map;
        }).get(),
        "吸血鬼的升级经验配置"
    );

    public static final DoubleConfig suckingParticleMaxDistance = new DoubleConfig(
        "sucking-particle.max-distance",
        32d,
        "吸血鬼特效的最大渲染距离"
    );
    public static final StringConfig suckingParticleFoolLevelType = new StringConfig(
        "sucking-particle.fool-level.type",
        "GLOW_SQUID_INK",
        "吸血鬼吸取饱食度时的特效配置"
    );
    public static final ColorConfig suckingParticleFoodLevelColor = new ColorConfig("sucking-particle.fool-level.color", Color.ORANGE);
    public static final DoubleConfig suckingParticleFoodLevelCenterLocArgsX = new DoubleConfig("sucking-particle.fool-level.center-loc-args.x", 0.85);
    public static final DoubleConfig suckingParticleFoodLevelCenterLocArgsY = new DoubleConfig("sucking-particle.fool-level.center-loc-args.y", 0.85);
    public static final DoubleConfig suckingParticleFoodLevelCenterLocArgsZ = new DoubleConfig("sucking-particle.fool-level.center-loc-args.z", 0.85);
    public static final StringConfig suckingParticleHealthType = new StringConfig(
        "sucking-particle.health.type",
        "GLOW_SQUID_INK",
        "吸血鬼吸取生命时的特效配置"
    );
    public static final ColorConfig suckingParticleHealthColor = new ColorConfig("sucking-particle.health.color", Color.RED);
    public static final DoubleConfig suckingParticleHealthCenterLocArgsX = new DoubleConfig("sucking-particle.health.center-loc-args.x", 0.85);
    public static final DoubleConfig suckingParticleHealthCenterLocArgsY = new DoubleConfig("sucking-particle.health.center-loc-args.y", 0.85);
    public static final DoubleConfig suckingParticleHealthCenterLocArgsZ = new DoubleConfig("sucking-particle.health.center-loc-args.z", 0.85);
    public static final StringConfig suckingParticleSaturationType = new StringConfig(
        "sucking-particle.saturation.type",
        "GLOW_SQUID_INK",
        "吸血鬼吸取饱和度时的特效配置"
    );
    public static final ColorConfig suckingParticleSaturationColor = new ColorConfig("sucking-particle.saturation.color", Color.YELLOW);
    public static final DoubleConfig suckingParticleSaturationCenterLocArgsX = new DoubleConfig("sucking-particle.saturation.center-loc-args.x", 0.85);
    public static final DoubleConfig suckingParticleSaturationCenterLocArgsY = new DoubleConfig("sucking-particle.saturation.center-loc-args.y", 0.85);
    public static final DoubleConfig suckingParticleSaturationCenterLocArgsZ = new DoubleConfig("sucking-particle.saturation.center-loc-args.z", 0.85);

    public static final StringListConfig nightEffects = new StringListConfig(
        "night-effects",
        List.of("night_vision,0,220", "strength,0,40","speed,0,40"),
        "吸血鬼的夜晚buff配置"
    );
    public static final ConfigSectionConfig skillDurationTick = new ConfigSectionConfig(
        "skill.duration-tick",
        ((Supplier<Map<String, Object>>) () -> {
            Map<String, Object> map = new ConcurrentHashMap<>();
            for (int i = 0; i < 11; i++) {
                map.put(i + "", 400 + 20 * i);
            }
            return map;
        }).get(),
        "吸血鬼的技能持续时间配置"
    );
    public static final ConfigSectionConfig skillRadius = new ConfigSectionConfig(
        "skill.radius",
        ((Supplier<Map<String, Object>>) () -> {
            Map<String, Object> map = new ConcurrentHashMap<>();
            for (int i = 0; i < 11; i++) {
                map.put(i + "", 20 + i);
            }
            return map;
        }).get(),
        "吸血鬼的技能效果半径配置"
    );
    public static final DoubleConfig skillAreaCubeStep = new DoubleConfig(
        "skill.area-cube.step",
        1.0,
        "提示吸血效果生效的特效粒子间距"
    );
    public static final ColorConfig skillAreaCubeColor = new ColorConfig(
        "skill.area-cube.color",
        Color.RED,
        "提示吸血效果生效的特效颜色"
    );
    public static final DoubleConfig skillWingStep = new DoubleConfig(
        "skill.wing.step",
        0.2,
        "吸血鬼释放技能时的翅膀粒子间距"
    );

    public static final StringListConfig skillWingShape = new StringListConfig(
        "skill.wing.shape",
        List.of(
            "    ########",
            "  #######",
            "######",
            "###",
            " ##",
            "  ##",
            "   ##"
        ),
        "吸血鬼释放技能时的翅膀形状"
    );
    public static final ColorConfig skillWingColor = new ColorConfig(
        "skill.wing.color",
        Color.RED,
        "吸血鬼释放技能时的翅膀颜色"
    );
    public static final DoubleConfig skillWingMinRotRange = new DoubleConfig(
        "skill.wing.min-rot-range", 0d, "吸血鬼释放技能时的翅膀最小角度"
    );
    public static final DoubleConfig skillWingMaxRotRange = new DoubleConfig(
        "skill.wing.max-rot-range", 72d, "吸血鬼释放技能时的翅膀最大角度"
    );
    
}
