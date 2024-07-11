package pers.yufiria.projectrace.race.vampire;

import crypticlib.listener.EventListener;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.util.Vector;
import pers.yufiria.projectrace.PlayerRace;
import pers.yufiria.projectrace.RaceManager;
import pers.yufiria.projectrace.config.Configs;
import pers.yufiria.projectrace.util.EntityHelper;
import top.zoyn.particlelib.pobject.bezier.TwoRankBezierCurve;

import java.util.Random;

@EventListener
public class VampireListener implements Listener {

    private Random random = new Random();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamageLivingEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) {
            return;
        }
        if (!(event.getEntity() instanceof LivingEntity entity))
            return;
        PlayerRace playerRace = RaceManager.INSTANCE.getPlayerRace(player.getUniqueId());
        if (playerRace == null)
            return;
        if (!playerRace.raceId().equals(Vampire.INSTANCE.id()))
            return;
        double finalDamage = event.getFinalDamage();
        double suckingAmount = finalDamage * ((Vampire) playerRace.race()).getSuckingRate(playerRace.raceLevel());
        Location start = EntityHelper.getMiddleLoc(entity);
        Location end = EntityHelper.getMiddleLoc(player);
        int foodLevel = player.getFoodLevel();
        if (foodLevel < 20) {
            player.setFoodLevel(Math.min(20, foodLevel + (int) Math.ceil(suckingAmount)));
            drawSuckParticle(
                start,
                end,
                Particle.valueOf(Configs.vampireSuckingParticleFoolLevelType.value().toUpperCase()),
                Configs.vampireSuckingParticleFoodLevelColor.value(),
                Configs.vampireSuckingParticleFoodLevelCenterLocArgsX.value(),
                Configs.vampireSuckingParticleFoodLevelCenterLocArgsY.value(),
                Configs.vampireSuckingParticleFoodLevelCenterLocArgsZ.value()
            );
            return;
        }

        double health = player.getHealth();
        double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if (health < maxHealth) {
            player.setHealth(Math.min(maxHealth, health + suckingAmount));
            drawSuckParticle(
                start,
                end,
                Particle.valueOf(Configs.vampireSuckingParticleHealthType.value().toUpperCase()),
                Configs.vampireSuckingParticleHealthColor.value(),
                Configs.vampireSuckingParticleHealthCenterLocArgsX.value(),
                Configs.vampireSuckingParticleHealthCenterLocArgsY.value(),
                Configs.vampireSuckingParticleHealthCenterLocArgsZ.value()
            );
            return;
        }

        float saturation = player.getSaturation();
        if (saturation < foodLevel) {
            if (suckingAmount < 1) {
                suckingAmount = 1;
            }
            player.setSaturation(Math.min(20, saturation + (int) Math.ceil(suckingAmount)));
            drawSuckParticle(
                start,
                end,
                Particle.valueOf(Configs.vampireSuckingParticleSaturationType.value().toUpperCase()),
                Configs.vampireSuckingParticleSaturationColor.value(),
                Configs.vampireSuckingParticleSaturationCenterLocArgsX.value(),
                Configs.vampireSuckingParticleSaturationCenterLocArgsY.value(),
                Configs.vampireSuckingParticleSaturationCenterLocArgsZ.value()
            );
        }
    }

    private void drawSuckParticle(Location start, Location end, Particle particle, Color color, double centerLocArgX, double centerLocArgY, double centerLocArgZ) {
        Vector vector = end.clone().subtract(start).toVector();
        Vector multiply = vector.multiply(
            new Vector(
                random.nextDouble(-centerLocArgX, centerLocArgX),
                random.nextDouble(-centerLocArgY, centerLocArgY),
                random.nextDouble(-centerLocArgZ, centerLocArgZ)
            )
        );
        Location center = start.clone().add(multiply);
        TwoRankBezierCurve twoRankBezierCurve = new TwoRankBezierCurve(start, center, end);
        twoRankBezierCurve.setParticle(particle).setColor(color);
        twoRankBezierCurve.show();
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        PlayerRace playerRace = RaceManager.INSTANCE.getPlayerRace(event.getEntity().getUniqueId());
        if (playerRace == null)
            return;
        if (!playerRace.raceId().equals(Vampire.INSTANCE.id()))
            return;
        if (event.getItem() != null) {
            event.setCancelled(true);
        }
    }

}
