package pers.yufiria.projectrace.race;

import org.apache.logging.log4j.util.BiConsumer;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.yufiria.projectrace.PlayerRace;
import pers.yufiria.projectrace.RaceManager;
import pers.yufiria.projectrace.util.AttributeHelper;

import java.util.HashMap;
import java.util.Map;

public interface Race {

    NamespacedKey MAX_HEALTH_KEY = new NamespacedKey("projectrace", "race.max_health");
    NamespacedKey INTERACT_RANGE_KEY = new NamespacedKey("projectrace", "race.interact_range");
    NamespacedKey SCALE_KEY = new NamespacedKey("projectrace", "race.scale");
    NamespacedKey ATTACK_DAMAGE_KEY = new NamespacedKey("projectrace", "race.attack_damage");
    NamespacedKey MOVE_SPEED_KEY = new NamespacedKey("projectrace", "race.move_speed");

    /**
     * 种族的识别ID,需要保证唯一性
     */
    @NotNull String id();

    /**
     * 种族的显示名字
     */
    @NotNull String name();

    int maxLevel();

    /**
     * 此种族的最大生命
     */
    default Double maxHealthModifier(int level) {return null;}

    default Double blockInteractRangeModifier(int level) {return null;}

    default Double entityInteractRangeModifier(int level) {return null;}

    default Double scaleModifier(int level) {return null;}

    default Double attackDamageModifier(int level) {return null;}

    default Double moveSpeedModifier(int level) {return null;}

    @Nullable
    BiConsumer<Player, PlayerRace> raceTask();

    /**
     * 升级所需的经验,返回负数代表此等级无法升级
     * @param level 升级前的等级
     */
    double levelUpExp(int level);

    void releaseSkill(Player race, PlayerRace playerRace);

    void cancelSkill(Player race);

    /**
     * 将此种族的属性应用到玩家身上
     */
    default void applyAttribute2Player(Player player) {
        PlayerRace playerRace = RaceManager.INSTANCE.getPlayerRace(player.getUniqueId());
        if (playerRace == null) {
            return;
        }

        int raceLevel = playerRace.raceLevel();

        //修改最大生命
        AttributeInstance maxHealthAttr = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        Double maxHealthModifier = maxHealthModifier(raceLevel);
        if (maxHealthModifier != null) {
            double originMaxHealth = maxHealthAttr.getValue();
            double originHealth = player.getHealth();
            double healthPercent = originHealth / originMaxHealth;
            AttributeHelper.addAttributeModifier(
                player,
                Attribute.GENERIC_MAX_HEALTH,
                new AttributeModifier(
                    MAX_HEALTH_KEY,
                    maxHealthModifier,
                    AttributeModifier.Operation.ADD_NUMBER,
                    EquipmentSlotGroup.ANY
                )
            );
            double changedMaxHealth = maxHealthAttr.getValue();
            if (originMaxHealth != changedMaxHealth)
                player.setHealth(changedMaxHealth * healthPercent);
        }

        //触及半径
        Double blockInteractRangeModifier = blockInteractRangeModifier(raceLevel);
        if (blockInteractRangeModifier != null) {
            AttributeModifier blockModifier = new AttributeModifier(
                INTERACT_RANGE_KEY,
                blockInteractRangeModifier,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.ANY
            );
            AttributeHelper.addAttributeModifier(player, Attribute.PLAYER_BLOCK_INTERACTION_RANGE, blockModifier);
        }
        Double entityInteractRangeModifier = entityInteractRangeModifier(raceLevel);
        if (entityInteractRangeModifier != null) {
            AttributeModifier entityModifier = new AttributeModifier(
                INTERACT_RANGE_KEY,
                entityInteractRangeModifier,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.ANY
            );
            AttributeHelper.addAttributeModifier(player, Attribute.PLAYER_ENTITY_INTERACTION_RANGE, entityModifier);
        }

        //体型
        Double scaleModifier = scaleModifier(raceLevel);
        if (scaleModifier != null) {
            AttributeHelper.addAttributeModifier(
                player,
                Attribute.GENERIC_SCALE,
                new AttributeModifier(
                    SCALE_KEY,
                    scaleModifier,
                    AttributeModifier.Operation.ADD_NUMBER,
                    EquipmentSlotGroup.ANY
                )
            );
        }

        //攻击伤害
        Double attackDamageModifier = attackDamageModifier(raceLevel);
        if (attackDamageModifier != null) {
            AttributeHelper.addAttributeModifier(
                player,
                Attribute.GENERIC_ATTACK_DAMAGE,
                new AttributeModifier(
                    ATTACK_DAMAGE_KEY,
                    attackDamageModifier,
                    AttributeModifier.Operation.ADD_NUMBER,
                    EquipmentSlotGroup.ANY
                )
            );
        }

        //移速
        Double moveSpeedModifier = moveSpeedModifier(raceLevel);
        if (moveSpeedModifier != null) {
            AttributeHelper.addAttributeModifier(
                player,
                Attribute.GENERIC_MOVEMENT_SPEED,
                new AttributeModifier(
                    MOVE_SPEED_KEY,
                    moveSpeedModifier,
                    AttributeModifier.Operation.ADD_NUMBER,
                    EquipmentSlotGroup.ANY
                )
            );
        }
    }

    default void register() {
        RaceManager.INSTANCE.registerRace(this);
    }

    default Map<Integer, Double> configSection4LevelValueMap(ConfigurationSection config) {
        Map<Integer, Double> map = new HashMap<>();
        for (String key : config.getKeys(false)) {
            int level = Integer.parseInt(key);
            double value = config.getDouble(key);
            map.put(level, value);
        }
        return map;
    }

}
