package pers.yufiria.projectrace.race;

import org.apache.logging.log4j.util.BiConsumer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.yufiria.projectrace.PlayerRace;
import pers.yufiria.projectrace.RaceManager;
import pers.yufiria.projectrace.util.AttributeHelper;

import java.util.function.Consumer;

public interface Race {

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
    @Nullable AttributeModifier maxHealthModifier(int level);

    @Nullable AttributeModifier interactRangeModifier(int level);

    @Nullable AttributeModifier scaleModifier(int level);

    @Nullable AttributeModifier attackDamageModifier(int level);

    @Nullable
    default BiConsumer<Player, PlayerRace> raceTask() {
        return null;
    }

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
        AttributeModifier maxHealthModifier = maxHealthModifier(raceLevel);
        if (maxHealthModifier != null) {
            double originMaxHealth = maxHealthAttr.getValue();
            double originHealth = player.getHealth();
            double healthPercent = originHealth / originMaxHealth;
            AttributeHelper.addAttributeModifier(player, Attribute.GENERIC_MAX_HEALTH, maxHealthModifier);
            double changedMaxHealth = maxHealthAttr.getValue();
            if (originMaxHealth != changedMaxHealth)
                player.setHealth(changedMaxHealth * healthPercent);
        }

        //触及半径
        AttributeModifier interactRangeModifier = interactRangeModifier(raceLevel);
        if (interactRangeModifier != null) {
            AttributeHelper.addAttributeModifier(player, Attribute.PLAYER_ENTITY_INTERACTION_RANGE, interactRangeModifier);
            AttributeHelper.addAttributeModifier(player, Attribute.PLAYER_BLOCK_INTERACTION_RANGE, interactRangeModifier);
        }

        //体型
        AttributeModifier scaleModifier = scaleModifier(raceLevel);
        if (scaleModifier != null) {
            AttributeHelper.addAttributeModifier(player, Attribute.GENERIC_SCALE, scaleModifier);
        }

        //攻击伤害
        AttributeModifier attackDamageModifier = attackDamageModifier(raceLevel);
        if (attackDamageModifier != null) {
            AttributeHelper.addAttributeModifier(player, Attribute.GENERIC_ATTACK_DAMAGE, attackDamageModifier);
        }
    }

    default void register() {
        RaceManager.INSTANCE.registerRace(this);
    }

}
