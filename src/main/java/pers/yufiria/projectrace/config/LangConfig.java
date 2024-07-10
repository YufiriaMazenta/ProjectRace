package pers.yufiria.projectrace.config;

import crypticlib.config.ConfigHandler;
import crypticlib.config.node.impl.bukkit.StringConfig;

import java.util.List;

@ConfigHandler(path = "lang.yml")
public class LangConfig {

    public static final StringConfig commandReload = new StringConfig(
        "command.reload",
        "&8[&3Race&8] &7插件已重载",
        List.of("重载插件的消息提示")
    );


}
