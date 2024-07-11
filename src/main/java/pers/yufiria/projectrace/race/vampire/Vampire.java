package pers.yufiria.projectrace.race.vampire;

import crypticlib.chat.BukkitMsgSender;
import crypticlib.chat.BukkitTextProcessor;
import crypticlib.lifecycle.BukkitEnabler;
import crypticlib.lifecycle.BukkitReloader;
import crypticlib.lifecycle.annotation.OnEnable;
import crypticlib.lifecycle.annotation.OnReload;
import org.apache.logging.log4j.util.BiConsumer;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.yufiria.projectrace.PlayerRace;
import pers.yufiria.projectrace.ProjectRaceBukkit;
import pers.yufiria.projectrace.config.Configs;
import pers.yufiria.projectrace.race.Race;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@OnEnable
@OnReload
public class Vampire implements Race, BukkitEnabler, BukkitReloader {

    public static final Vampire INSTANCE = new Vampire();
    private Map<Integer, Double> maxHealthModifierValueMap = new ConcurrentHashMap<>();
    private List<PotionEffect> nightPotionEffects = new ArrayList<>();
    private Map<Integer, Double> suckingRateMap = new ConcurrentHashMap<>();
    private Map<Integer, Double> levelUpExpMap = new ConcurrentHashMap<>();
    private NamespacedKey vampireMaxHealthModifierKey = new NamespacedKey("projectrace", id() + ".max_health");

    @Override
    public @NotNull String id() {
        return "vampire";
    }

    @Override
    public @NotNull String name() {
        return BukkitTextProcessor.color(Configs.vampireName.value());
    }

    @Override
    public int maxLevel() {
        return Configs.vampireMaxLevel.value();
    }

    @Override
    public @Nullable AttributeModifier maxHealthModifier(int level) {
        if (maxHealthModifierValueMap.containsKey(level)) {
            double value = maxHealthModifierValueMap.get(level);
            return new AttributeModifier(
                vampireMaxHealthModifierKey,
                value,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.ANY
            );
        } else {
            if (level == 0) {
                return null;
            }
            return maxHealthModifier(level - 1);
        }
    }

    @Override
    public @Nullable AttributeModifier interactRangeModifier(int level) {
        return null;
    }

    @Override
    public @Nullable AttributeModifier scaleModifier(int level) {
        return null;
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

    @Override
    public void enable(Plugin plugin) {
        register();
        reload(plugin);
    }

    @Override
    public void reload(Plugin plugin) {
        maxHealthModifierValueMap.clear();
        ConfigurationSection maxHealthConfig = Configs.vampireMaxHealthModifier.value();
        for (String key : maxHealthConfig.getKeys(false)) {
            int level = Integer.parseInt(key);
            double value = maxHealthConfig.getDouble(key);
            maxHealthModifierValueMap.put(level, value);
        }
        nightPotionEffects.clear();
        for (String effectStr : Configs.vampireNightEffects.value()) {
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
        ConfigurationSection suckingRates = Configs.vampireSuckingRate.value();
        for (String key : suckingRates.getKeys(false)) {
            int level = Integer.parseInt(key);
            double rate = suckingRates.getDouble(key);
            suckingRateMap.put(level, rate);
        }

        levelUpExpMap.clear();
        ConfigurationSection levelUpExpConfig = Configs.vampireLevelUpExp.value();
        for (String key : levelUpExpConfig.getKeys(false)) {
            int level = Integer.parseInt(key);
            double exp = levelUpExpConfig.getDouble(key);
            levelUpExpMap.put(level, exp);
        }
    }

}
