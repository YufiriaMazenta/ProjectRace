package pers.yufiria.projectrace.race.vampire;

import crypticlib.listener.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pers.yufiria.projectrace.PlayerRace;
import pers.yufiria.projectrace.RaceManager;
import pers.yufiria.projectrace.event.VampireSuckEvent;

import java.util.Map;
import java.util.UUID;

@EventListener
public class VampireListener implements Listener {

    public final String VAMPIRE_SKILL_ENERGY = "vampire_skill_energy";

    @EventHandler(priority = EventPriority.HIGHEST)
    public void commonSuck(EntityDamageByEntityEvent event) {
        if (event.isCancelled())
            return;
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
        ((Vampire) playerRace.race()).suck(player, entity, playerRace, finalDamage);
    }

    @EventHandler
    public void cancelItemFoodChange(FoodLevelChangeEvent event) {
        if (event.isCancelled())
            return;
        PlayerRace playerRace = RaceManager.INSTANCE.getPlayerRace(event.getEntity().getUniqueId());
        if (playerRace == null)
            return;
        if (!playerRace.raceId().equals(Vampire.INSTANCE.id()))
            return;
        if (event.getItem() != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void skillSuck(EntityDamageEvent event) {
        if (event.isCancelled())
            return;
        if (!(event.getEntity() instanceof LivingEntity entity))
            return;
        Location location = entity.getLocation();
        VampireSkillManager.SuckingArea suckingArea = VampireSkillManager.INSTANCE.getInSuckingArea(location);
        if (suckingArea == null) {
            return;
        }
        UUID owner = suckingArea.owner();
        if (owner.equals(entity.getUniqueId()))
            return;
        Player vampire = Bukkit.getPlayer(owner);
        if (vampire == null) {
            return;
        }
        PlayerRace playerRace = RaceManager.INSTANCE.getPlayerRace(owner);
        if (playerRace == null) {
            return;
        }
        if (!playerRace.raceId().equals(Vampire.INSTANCE.id())) {
            return;
        }
        ((Vampire) playerRace.race()).suck(vampire, entity, playerRace, event.getFinalDamage());
    }

    @EventHandler
    public void cancelVampireSkill(PlayerDeathEvent event) {
        PlayerRace playerRace = RaceManager.INSTANCE.getPlayerRace(event.getPlayer().getUniqueId());
        if (playerRace == null) {
            return;
        }
        playerRace.race().cancelSkill(event.getPlayer());
    }

    @EventHandler
    public void cancelVampireSkill(PlayerQuitEvent event) {
        PlayerRace playerRace = RaceManager.INSTANCE.getPlayerRace(event.getPlayer().getUniqueId());
        if (playerRace == null) {
            return;
        }
        playerRace.race().cancelSkill(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onVampireSuckAddSkillEngine(VampireSuckEvent event) {
        if (event.isCancelled())
            return;
        if (VampireSkillManager.INSTANCE.isReleasingSkill(event.playerRace().playerId())) {
            return;
        }
        Map<String, Object> raceArgs = event.playerRace().raceArgs();
        if (raceArgs.containsKey(VAMPIRE_SKILL_ENERGY)) {
            double value = (double) raceArgs.get(VAMPIRE_SKILL_ENERGY);
            value += event.raceAmount();
            raceArgs.put(VAMPIRE_SKILL_ENERGY, value);
        } else {
            raceArgs.put(VAMPIRE_SKILL_ENERGY, event.raceAmount());
        }
        //TODO 能量充满后,如何使用技能
    }

}
