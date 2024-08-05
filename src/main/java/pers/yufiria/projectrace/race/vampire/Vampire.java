package pers.yufiria.projectrace.race.vampire;

import crypticlib.chat.BukkitMsgSender;
import crypticlib.chat.BukkitTextProcessor;
import crypticlib.lifecycle.BukkitEnabler;
import crypticlib.lifecycle.BukkitReloader;
import crypticlib.lifecycle.annotation.OnEnable;
import crypticlib.lifecycle.annotation.OnReload;
import org.apache.logging.log4j.util.BiConsumer;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.yufiria.projectrace.PlayerRace;
import pers.yufiria.projectrace.config.race.VampireConfig;
import pers.yufiria.projectrace.event.VampireSuckEvent;
import pers.yufiria.projectrace.race.Race;
import pers.yufiria.projectrace.util.EntityHelper;
import pers.yufiria.projectrace.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@OnEnable
@OnReload
public class Vampire implements Race, BukkitEnabler, BukkitReloader {

    public static final Vampire INSTANCE = new Vampire();
    private final Map<Integer, Double> maxHealthModifierValueMap = new ConcurrentHashMap<>();
    private final List<PotionEffect> nightPotionEffects = new ArrayList<>();
    private final Map<Integer, Double> suckingRateMap = new ConcurrentHashMap<>();
    private final Map<Integer, Double> levelUpExpMap = new ConcurrentHashMap<>();
    private final Map<Integer, Long> skillDurationTickMap = new ConcurrentHashMap<>();
    private final Map<Integer, Double> skillRadiusMap = new ConcurrentHashMap<>();

    private Vampire() {}

    @Override
    public @NotNull String id() {
        return "vampire";
    }

    @Override
    public @NotNull String name() {
        return BukkitTextProcessor.color(VampireConfig.name.value());
    }

    @Override
    public int maxLevel() {
        return VampireConfig.maxLevel.value();
    }

    @Override
    public Double maxHealthModifier(int level) {
        if (maxHealthModifierValueMap.containsKey(level)) {
            return maxHealthModifierValueMap.get(level);
        } else {
            if (level == 0) {
                return null;
            }
            return maxHealthModifier(level - 1);
        }
    }

    @Override
    public @Nullable BiConsumer<Player, PlayerRace> raceTask() {
        return (player, playerRace) -> {
            World world = player.getWorld();
            if (!world.isBedWorks()) {
                return;
            }
            long time = world.getTime();
            if (time > 23458 || time < 12541)
                return;
            for (PotionEffect nightPotionEffect : nightPotionEffects) {
                player.addPotionEffect(nightPotionEffect);
            }
        };
    }

    @Override
    public double levelUpExp(int level) {
        if (levelUpExpMap.containsKey(level)) {
            return levelUpExpMap.get(level);
        } else {
            return -1;
        }
    }

    @Override
    public void releaseSkill(Player vampire, PlayerRace playerRace) {
        long duration = skillDuration(playerRace.raceLevel());
        double radius = skillRadius(playerRace.raceLevel());
        VampireSkillManager.INSTANCE.releaseSkill(vampire, duration, radius);
    }

    @Override
    public void cancelSkill(Player vampire) {
        VampireSkillManager.INSTANCE.cancelSkill(vampire);
    }

    public long skillDuration(int level) {
        if (skillDurationTickMap.containsKey(level)) {
            return skillDurationTickMap.get(level);
        } else {
            if (level == 0) {
                return 400;
            }
            return skillDuration(level - 1);
        }
    }

    public double skillRadius(int level) {
        if (skillRadiusMap.containsKey(level)) {
            return skillRadiusMap.get(level);
        } else {
            if (level == 0) {
                return 40d;
            }
            return skillRadius(level - 1);
        }
    }

    public double getSuckingRate(int level) {
        if (suckingRateMap.containsKey(level)) {
            return suckingRateMap.get(level);
        } else {
            if (level == 0) {
                return 0.25;
            } else {
                return getSuckingRate(level - 1);
            }
        }
    }

    /**
     * 吸血
     * @param vampire 攻击者
     * @param entity 被吸血的生物
     */
    public void suck(Player vampire, LivingEntity entity, PlayerRace playerRace, double damage) {
        double suckingAmount = damage * this.getSuckingRate(playerRace.raceLevel());
        Location start = EntityHelper.getMiddleLoc(entity);
        Location end = EntityHelper.getMiddleLoc(vampire);
        int foodLevel = vampire.getFoodLevel();
        if (foodLevel < 20) {
            VampireSuckEvent event = new VampireSuckEvent(playerRace, suckingAmount, VampireSuckEvent.SuckType.FOOD_LEVEL);
            Utils.callEvent(event);
            if (event.isCancelled()) {
                return;
            }
            vampire.setFoodLevel(Math.min(20, foodLevel + (int) Math.ceil(suckingAmount)));
            VampireParticlePainter.INSTANCE.drawSuckParticle(
                start,
                end,
                Particle.valueOf(VampireConfig.suckingParticleFoolLevelType.value().toUpperCase()),
                VampireConfig.suckingParticleFoodLevelColor.value(),
                VampireConfig.suckingParticleFoodLevelCenterLocArgsX.value(),
                VampireConfig.suckingParticleFoodLevelCenterLocArgsY.value(),
                VampireConfig.suckingParticleFoodLevelCenterLocArgsZ.value()
            );
            return;
        }

        double health = vampire.getHealth();
        double maxHealth = vampire.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if (health < maxHealth) {
            VampireSuckEvent event = new VampireSuckEvent(playerRace, suckingAmount, VampireSuckEvent.SuckType.HEALTH);
            Utils.callEvent(event);
            if (event.isCancelled()) {
                return;
            }
            vampire.setHealth(Math.min(maxHealth, health + suckingAmount));
            VampireParticlePainter.INSTANCE.drawSuckParticle(
                start,
                end,
                Particle.valueOf(VampireConfig.suckingParticleHealthType.value().toUpperCase()),
                VampireConfig.suckingParticleHealthColor.value(),
                VampireConfig.suckingParticleHealthCenterLocArgsX.value(),
                VampireConfig.suckingParticleHealthCenterLocArgsY.value(),
                VampireConfig.suckingParticleHealthCenterLocArgsZ.value()
            );
            return;
        }

        float saturation = vampire.getSaturation();
        if (saturation < foodLevel) {
            VampireSuckEvent event = new VampireSuckEvent(playerRace, suckingAmount, VampireSuckEvent.SuckType.SATURATION);
            Utils.callEvent(event);
            if (event.isCancelled()) {
                return;
            }
            vampire.setSaturation(Math.min(20, saturation + (int) Math.ceil(suckingAmount)));
            VampireParticlePainter.INSTANCE.drawSuckParticle(
                start,
                end,
                Particle.valueOf(VampireConfig.suckingParticleSaturationType.value().toUpperCase()),
                VampireConfig.suckingParticleSaturationColor.value(),
                VampireConfig.suckingParticleSaturationCenterLocArgsX.value(),
                VampireConfig.suckingParticleSaturationCenterLocArgsY.value(),
                VampireConfig.suckingParticleSaturationCenterLocArgsZ.value()
            );
        }
    }

    @Override
    public void enable(Plugin plugin) {
        register();
        reload(plugin);
    }

    @Override
    public void reload(Plugin plugin) {
        maxHealthModifierValueMap.clear();
        maxHealthModifierValueMap.putAll(configSection4LevelValueMap(VampireConfig.maxHealthModifier.value()));

        nightPotionEffects.clear();
        for (String effectStr : VampireConfig.nightEffects.value()) {
            String[] split = effectStr.split(",");
            PotionEffectType effectType = PotionEffectType.getByKey(NamespacedKey.minecraft(split[0].trim()));
            if (effectType == null) {
                BukkitMsgSender.INSTANCE.info("&cUnknown effect type " + split[0]);
                continue;
            }
            int level;
            if (split.length >= 2) {
                level = Integer.parseInt(split[1].trim());
            } else {
                level = 0;
            }
            int ticks;
            if (split.length >= 3) {
                ticks = Integer.parseInt(split[2].trim());
            } else {
                ticks = 40;
            }
            nightPotionEffects.add(new PotionEffect(effectType, ticks, level, false, false));
        }

        suckingRateMap.clear();
        suckingRateMap.putAll(configSection4LevelValueMap(VampireConfig.suckingRate.value()));

        levelUpExpMap.clear();
        levelUpExpMap.putAll(configSection4LevelValueMap(VampireConfig.levelUpExp.value()));

        skillDurationTickMap.clear();
        ConfigurationSection skillDurationTickConfig = VampireConfig.skillDurationTick.value();
        for (String key : skillDurationTickConfig.getKeys(false)) {
            int level = Integer.parseInt(key);
            long duration = skillDurationTickConfig.getLong(key);
            skillDurationTickMap.put(level, duration);
        }

        skillRadiusMap.clear();
        skillRadiusMap.putAll(configSection4LevelValueMap(VampireConfig.skillRadius.value()));
    }

}
