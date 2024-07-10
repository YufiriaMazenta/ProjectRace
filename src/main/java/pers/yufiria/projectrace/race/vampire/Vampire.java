package pers.yufiria.projectrace.race.vampire;

import crypticlib.chat.BukkitTextProcessor;
import crypticlib.lifecycle.BukkitEnabler;
import crypticlib.lifecycle.BukkitReloader;
import crypticlib.lifecycle.annotation.OnEnable;
import crypticlib.lifecycle.annotation.OnReload;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.yufiria.projectrace.ProjectRaceBukkit;
import pers.yufiria.projectrace.config.Configs;
import pers.yufiria.projectrace.race.Race;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@OnEnable
@OnReload
public class Vampire implements Race, BukkitEnabler, BukkitReloader {

    public static final Vampire INSTANCE = new Vampire();
    private Map<Integer, Double> maxHealthModifierValueMap = new ConcurrentHashMap<>();

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
                new NamespacedKey(ProjectRaceBukkit.INSTANCE, id() + ".max_health"),
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
    }

}
