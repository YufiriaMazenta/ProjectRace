package pers.yufiria.projectrace.util;

import org.bukkit.Location;

public class LocationHelper {

    public static Location getMiddleLoc(Location loc1, Location loc2) {
        return new Location(
            loc1.getWorld(),
            (loc1.getX() + loc2.getX()) / 2,
            (loc1.getY() + loc2.getY()) / 2,
            (loc1.getZ() + loc2.getZ()) / 2
        );
    }

}
