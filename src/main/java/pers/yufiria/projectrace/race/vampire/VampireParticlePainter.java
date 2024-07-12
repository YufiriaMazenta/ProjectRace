package pers.yufiria.projectrace.race.vampire;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;
import pers.yufiria.projectrace.config.Configs;
import top.zoyn.particlelib.pobject.bezier.TwoRankBezierCurve;

import java.util.Random;

public enum VampireParticlePainter {

    INSTANCE;

    private final Random random = new Random();

    public void drawSuckParticle(Location start, Location end, Particle particle, Color color, double centerLocArgX, double centerLocArgY, double centerLocArgZ) {
        if (!start.getWorld().equals(end.getWorld())) {
            return;
        }
        Vector vector = end.clone().subtract(start).toVector();
        if (vector.length() > Configs.vampireSuckingParticleMaxDistance.value()) {
            return;
        }
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

}
