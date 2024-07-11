package pers.yufiria.projectrace.config;

import crypticlib.config.ConfigHandler;
import crypticlib.config.node.impl.bukkit.StringConfig;

@ConfigHandler(path = "lang.yml")
public class LangConfig {

    public static final StringConfig commandReload = new StringConfig(
        "command.reload",
        "&8[&3Race&8] &7&7插件已重载",
        "重载插件的消息提示"
    );
    public static final StringConfig commandMissingPlayerName = new StringConfig(
        "command.missing-player-name",
        "&8[&3Race&8] &7请输入有效的玩家名字",
        "插件命令缺少玩家参数的提示"
    );
    public static final StringConfig commandPlayerOffline = new StringConfig(
        "command.player-offline",
        "&8[&3Race&8] &7玩家不在线",
        "插件命令中玩家不在线的提示"
    );
    public static final StringConfig commandSetRaceMissingRace = new StringConfig(
        "command.set-race.missing-race",
        "&8[&3Race&8] &7请输入有效的种族ID",
        "插件命令缺少种族类型的提示"
    );
    public static final StringConfig commandSetRaceNotExistRace = new StringConfig(
        "command.set-race.not-exist-race",
        "&8[&3Race&8] &7种族%race%不存在",
        "设置种族命令中不存在对应种族类型的提示"
    );
    public static final StringConfig commandSetRaceSuccess = new StringConfig(
        "command.set-race.success",
        "&8[&3Race&8] &7玩家%player%的种族已经修改为%race%",
        "设置种族命令成功的提示"
    );
    public static final StringConfig commandSetLevelMissingLevel = new StringConfig(
        "command.set-level.missing-level",
        "&8[&3Race&8] &7请输入要设置的等级",
        "设置种族等级命令缺少等级参数的提示"
    );
    public static final StringConfig commandSetLevelNumberFormatFailed = new StringConfig(
        "command.set-level.number-format-failed",
        "&8[&3Race&8] &7你输入的等级不是一个有效的数字！",
        "设置种族等级命令中输入等级无效的提示"
    );
    public static final StringConfig commandSetLevelPlayerNoRace = new StringConfig(
        "command.set-level.player-no-race",
        "&8[&3Race&8] &7玩家目前暂时没有种族！无法设置等级",
        "设置种族等级命令中玩家没有种族的提示"
    );
    public static final StringConfig commandSetLevelSuccess = new StringConfig(
        "command.set-level.success",
        "&8[&3Race&8] &7玩家%player%的种族等级已被设置为%level%",
        "设置种族等级命令成功执行的提示"
    );
    public static final StringConfig commandExpExpMissingExp = new StringConfig(
        "command.exp.missing-exp",
        "&8[&3Race&8] &7请输入经验值",
        "设置种族经验命令输入经验值的提示"
    );
    public static final StringConfig commandExpNumberFormatFailed = new StringConfig(
        "command.exp.number-format-failed",
        "&8[&3Race&8] &7你输入的经验值不是一个有效的数字！",
        "设置种族经验命令经验无效的提示"
    );
    public static final StringConfig commandExpPlayerNoRace = new StringConfig(
        "command.exp.player-no-race",
        "&8[&3Race&8] &7玩家目前暂时没有种族！无法设置经验值",
        "设置种族经验命令中玩家没有种族的提示"
    );
    public static final StringConfig commandSetExpSuccess = new StringConfig(
        "command.set-exp.success",
        "&8[&3Race&8] &7玩家%player%的种族经验已被修改为%exp%, 修正后等级为%level%",
        "设置种族等级命令成功执行的提示"
    );

}
