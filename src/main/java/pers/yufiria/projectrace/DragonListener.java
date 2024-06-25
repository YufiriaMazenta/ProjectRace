package pers.yufiria.projectrace;

import com.destroystokyo.paper.event.entity.EnderDragonFireballHitEvent;
import crypticlib.listener.BukkitListener;
import org.bukkit.Particle;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pers.yufiria.projectrace.config.TestConfig;

@BukkitListener
public enum DragonListener implements Listener {

    INSTANCE;

    @EventHandler
    public void onDragonBallHit(EnderDragonFireballHitEvent event) {
        String particleName = TestConfig.DRAGON_FIREBALL_PARTICLE.value().toUpperCase().replace("-", "_");
        if (particleName.isEmpty()) {
            return;
        }
        Particle particle = Particle.valueOf(particleName);
        AreaEffectCloud areaEffectCloud = event.getAreaEffectCloud();
        areaEffectCloud.setParticle(particle);
        areaEffectCloud.setRadius(TestConfig.DRAGON_FIREBALL_RADIUS.value());
    }

}
