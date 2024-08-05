package pers.yufiria.projectrace.race.fairy;

import crypticlib.lifecycle.BukkitEnabler;
import crypticlib.lifecycle.BukkitReloader;
import crypticlib.lifecycle.annotation.OnEnable;
import crypticlib.lifecycle.annotation.OnReload;
import org.apache.logging.log4j.util.BiConsumer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.yufiria.projectrace.PlayerRace;
import pers.yufiria.projectrace.config.race.FairyConfig;
import pers.yufiria.projectrace.race.Race;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@OnEnable
@OnReload
public class Fairy implements Race, BukkitEnabler, BukkitReloader {

    public static final Fairy INSTANCE = new Fairy();
    private final Map<Integer, Double> maxHealthModifierValueMap = new ConcurrentHashMap<>();
    private final Map<Integer, Double> levelUpExpMap = new ConcurrentHashMap<>();

    private Fairy() {}

    @Override
    public @NotNull String id() {
        return "fairy";
    }

    @Override
    public @NotNull String name() {
        return FairyConfig.name.value();
    }

    @Override
    public int maxLevel() {
        return FairyConfig.maxLevel.value();
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
    public Double blockInteractRangeModifier(int level) {
        return FairyConfig.blockInteractRangeModifier.value();
    }

    @Override
    public Double entityInteractRangeModifier(int level) {
        return FairyConfig.entityInteractRangeModifier.value();
    }

    @Override
    public Double scaleModifier(int level) {
        return FairyConfig.scaleModifier.value();
    }

    @Override
    public @Nullable BiConsumer<Player, PlayerRace> raceTask() {
        return (player, playerRace) -> {
            if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) {
                if (player.getFlySpeed() < 0.1f)
                    player.setFlySpeed(0.1f);
                return;
            }
            if (!player.getAllowFlight()) {
                player.setAllowFlight(true);
            }
            if (player.getFlySpeed() != FairyConfig.flySpeed.value().floatValue()) {
                player.setFlySpeed(FairyConfig.flySpeed.value().floatValue());
            }
//            if (player.isFlying()) {
//                Circle circle = new Circle(player.getLocation());
//                circle.attachEntity(player);
//                circle.setParticle(Particle.valueOf(FairyConfig.flyParticleType.value().toUpperCase()));
//                circle.setColor(FairyConfig.flyParticleColor.value());
//                circle.setRadius(FairyConfig.flyParticleRadius.value());
//                circle.play();
//            }
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
    public void releaseSkill(Player race, PlayerRace playerRace) {

    }

    @Override
    public void cancelSkill(Player race) {

    }

    @Override
    public void enable(Plugin plugin) {
        register();
        reload(plugin);
    }

    @Override
    public void reload(Plugin plugin) {
        maxHealthModifierValueMap.clear();
        maxHealthModifierValueMap.putAll(configSection4LevelValueMap(FairyConfig.maxHealthModifier.value()));

        levelUpExpMap.clear();
        levelUpExpMap.putAll(configSection4LevelValueMap(FairyConfig.levelUpExp.value()));
    }

}
