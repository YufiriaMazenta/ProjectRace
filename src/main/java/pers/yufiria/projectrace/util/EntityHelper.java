package pers.yufiria.projectrace.util;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public class EntityHelper {

    public static Location getMiddleLoc(LivingEntity entity) {
        Location entityEyeLoc = entity.getEyeLocation().clone();
        Location entityLoc = entity.getLocation().clone();
        return entityLoc.set(entityLoc.getX(), (entityLoc.getY() + entityEyeLoc.getY()) /2, entityLoc.getZ());
    }

}
