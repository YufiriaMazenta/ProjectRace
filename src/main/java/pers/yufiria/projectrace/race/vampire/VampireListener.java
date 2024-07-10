package pers.yufiria.projectrace.race.vampire;

import crypticlib.listener.EventListener;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pers.yufiria.projectrace.PlayerRace;
import pers.yufiria.projectrace.RaceManager;
import pers.yufiria.projectrace.config.Configs;

@EventListener
public class VampireListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamageLivingEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) {
            return;
        }
        if (!(event.getEntity() instanceof LivingEntity))
            return;
        PlayerRace playerRace = RaceManager.INSTANCE.getPlayerRace(player.getUniqueId());
        if (playerRace == null)
            return;
        if (!playerRace.raceId().equals(Vampire.INSTANCE.id()))
            return;
        double finalDamage = event.getFinalDamage();
        double health = player.getHealth();
        double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        double suckingAmount = finalDamage * Configs.vampireSuckingRate.value();
        player.setHealth(Math.min(maxHealth, health + suckingAmount));
    }

}
