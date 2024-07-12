package pers.yufiria.projectrace.race.vampire;

import crypticlib.CrypticLibBukkit;
import crypticlib.scheduler.CrypticLibRunnable;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pers.yufiria.projectrace.ProjectRaceBukkit;
import pers.yufiria.projectrace.config.Configs;
import top.zoyn.particlelib.pobject.Cube;
import top.zoyn.particlelib.pobject.Wing;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public enum VampireSkillManager {

    INSTANCE;

    private final Map<UUID, SuckingArea> suckingAreaMap = new HashMap<>();
    private final Map<UUID, Cube> suckingCubeMap = new HashMap<>();
    private final Map<UUID, Wing> suckingWingMap = new HashMap<>();
    private final Map<UUID, CrypticLibRunnable> suckingDarkEffectTaskMap = new HashMap<>();

    public void releaseSkill(Player vampire, long duration, double radius) {
        if (suckingAreaMap.containsKey(vampire.getUniqueId())) {
            return;
        }
        UUID uuid = vampire.getUniqueId();
        Location releaseLoc = vampire.getLocation();
        Location loc1 = releaseLoc.clone().add(-radius, -radius, -radius);
        Location loc2 = releaseLoc.clone().add(radius, radius, radius);
        Cube cube = new Cube(loc1, loc2);
        cube.setColor(Configs.vampireSkillAreaCubeColor.value());
        cube.setStep(Configs.vampireSkillAreaCubeStep.value());
        cube.alwaysShowAsync();
        suckingCubeMap.put(uuid, cube);
        Wing wing = new Wing(
            releaseLoc,
            Configs.vampireSkillWingShape.value(),
            Configs.vampireSkillWingMinRotRange.value(),
            Configs.vampireSkillWingMaxRotRange.value(),
            Configs.vampireSkillWingStep.value()
        );
        wing.setColor(Configs.vampireSkillWingColor.value());
        wing.setSwing(true);
        wing.attachEntity(vampire);
        wing.alwaysShowAsync();
        suckingWingMap.put(uuid, wing);
        suckingAreaMap.put(uuid, new SuckingArea(loc1, loc2, uuid));
        CrypticLibRunnable darkEffectTask = new CrypticLibRunnable() {
            @Override
            public void run() {
                for (Entity entity : releaseLoc.getNearbyEntities(radius, radius, radius)) {
                    if (!(entity instanceof LivingEntity livingEntity)) {
                        continue;
                    }
                    if (livingEntity.getUniqueId().equals(vampire.getUniqueId())) {
                        continue;
                    }
                    livingEntity.addPotionEffect(new PotionEffect(
                        PotionEffectType.DARKNESS,
                        100,
                        0
                    ));
                }
            }
        };
        darkEffectTask.runTaskTimer(ProjectRaceBukkit.INSTANCE, 0, 20L);
        suckingDarkEffectTaskMap.put(uuid, darkEffectTask);
        CrypticLibBukkit.scheduler().runTaskLater(
            ProjectRaceBukkit.INSTANCE,
            () -> {
                cancelSkill(vampire);
            },
            duration
        );
    }

    public SuckingArea getInSuckingArea(Location loc) {
        for (SuckingArea suckingArea : suckingAreaMap.values()) {
            if (suckingArea.inArea(loc))
                return suckingArea;
        }
        return null;
    }

    /**
     * 取消技能
     */
    public void cancelSkill(Player vampire) {
        suckingAreaMap.remove(vampire.getUniqueId());
        Cube cube = suckingCubeMap.get(vampire.getUniqueId());
        if (cube != null) {
            cube.turnOffTask();
            suckingCubeMap.remove(vampire.getUniqueId());
        }
        Wing wing = suckingWingMap.get(vampire.getUniqueId());
        if (wing != null) {
            wing.turnOffTask();
            suckingWingMap.remove(vampire.getUniqueId());
        }
        if (suckingDarkEffectTaskMap.containsKey(vampire.getUniqueId())) {
            suckingDarkEffectTaskMap.get(vampire.getUniqueId()).cancel();
            suckingDarkEffectTaskMap.remove(vampire.getUniqueId());
        }
    }

    public record SuckingArea(Location loc1, Location loc2, UUID owner) {

        public boolean inArea(Location loc) {
            if (loc.getX() > loc2.getX()) {
                return false;
            }
            if (loc.getX() < loc1.getX()) {
                return false;
            }
            if (loc.getY() > loc2.getY()) {
                return false;
            }
            if (loc.getY() < loc1.getY()) {
                return false;
            }
            if (loc.getZ() > loc2.getZ()) {
                return false;
            }
            return !(loc.getZ() < loc1.getZ());
        }

    }
    
}
