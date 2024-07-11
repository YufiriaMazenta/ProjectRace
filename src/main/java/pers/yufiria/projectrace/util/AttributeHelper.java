package pers.yufiria.projectrace.util;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import pers.yufiria.projectrace.PlayerRace;
import pers.yufiria.projectrace.ProjectRaceBukkit;

public class AttributeHelper {

    public static void removePlayerAllRaceAttribute(Player player) {
        for (Attribute attribute : Attribute.values()) {
            AttributeInstance attributeInstance = player.getAttribute(attribute);
            if (attributeInstance == null) {
                continue;
            }
            for (AttributeModifier modifier : attributeInstance.getModifiers()) {
                if (modifier.getKey().getNamespace().equalsIgnoreCase(ProjectRaceBukkit.INSTANCE.getDescription().getName())) {
                    attributeInstance.removeModifier(modifier);
                }
            }
        }
    }

    public static void addAttributeModifier(Player player, Attribute attribute, AttributeModifier modifier) {
        AttributeInstance attributeIns = player.getAttribute(attribute);
        if (attributeIns == null) {
            return;
        }
        for (AttributeModifier originModifier : attributeIns.getModifiers()) {
            if (originModifier.getKey().equals(modifier.getKey())) {
                if (originModifier.getAmount() == modifier.getAmount()) {
                    //相同就不设置了
                    return;
                }
                attributeIns.removeModifier(originModifier);
            }
        }
        attributeIns.addModifier(modifier);
    }

}
