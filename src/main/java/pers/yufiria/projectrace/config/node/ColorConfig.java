package pers.yufiria.projectrace.config.node;

import crypticlib.config.node.BukkitConfigNode;
import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ColorConfig extends BukkitConfigNode<Color> {

    public ColorConfig(@NotNull String key, @NotNull Color def) {
        super(key, def);
    }

    public ColorConfig(String key, Color def, @NotNull String defComment) {
        super(key, def, defComment);
    }

    public ColorConfig(@NotNull String key, @NotNull Color def, @NotNull List<String> defComments) {
        super(key, def, defComments);
    }

    @Override
    public void load(@NotNull ConfigurationSection configurationSection) {
        this.saveDef(configurationSection);
        this.setValue(configurationSection.getColor(key, def));
        this.setComments(configurationSection.getComments(key));
    }

}
