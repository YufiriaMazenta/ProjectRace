package pers.yufiria.projectrace.command;

import crypticlib.chat.BukkitMsgSender;
import crypticlib.command.BukkitCommand;
import crypticlib.command.BukkitSubCommand;
import crypticlib.command.CommandInfo;
import crypticlib.command.annotation.Command;
import crypticlib.command.annotation.Subcommand;
import crypticlib.perm.PermInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pers.yufiria.projectrace.PlayerRace;
import pers.yufiria.projectrace.ProjectRaceBukkit;
import pers.yufiria.projectrace.RaceManager;
import pers.yufiria.projectrace.command.sub.RaceExpCommand;
import pers.yufiria.projectrace.config.LangConfig;
import pers.yufiria.projectrace.race.Race;
import pers.yufiria.projectrace.util.AttributeHelper;
import pers.yufiria.projectrace.util.Utils;

import java.util.List;
import java.util.Map;

@Command
public class PluginCommand extends BukkitCommand {

    public static final PluginCommand INSTANCE = new PluginCommand();

    public PluginCommand() {
        super(new CommandInfo("race", new PermInfo("race.command")));
    }

    @Subcommand
    BukkitSubCommand reload = new BukkitSubCommand("reload", new PermInfo("race.command.reload")) {
        @Override
        public void execute(@NotNull CommandSender sender, @NotNull List<String> args) {
            ProjectRaceBukkit.INSTANCE.reloadPlugin();
            BukkitMsgSender.INSTANCE.sendMsg(sender, LangConfig.commandReload.value());
        }
    };

    @Subcommand
    BukkitSubCommand setRace = new BukkitSubCommand("setRace", new PermInfo("race.command.set-race")) {
        @Override
        public void execute(@NotNull CommandSender commandSender, @NotNull List<String> args) {
            if (args.isEmpty()) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, LangConfig.commandMissingPlayerName.value());
                return;
            }
            if (args.size() < 2) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, LangConfig.commandSetRaceMissingRace.value());
                return;
            }
            String playerName = args.get(0);
            String raceId = args.get(1);
            Player player = Bukkit.getPlayer(playerName);
            if (player == null) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, LangConfig.commandPlayerOffline.value());
                return;
            }
            Race race = RaceManager.INSTANCE.getRace(raceId);
            if (race == null) {
                BukkitMsgSender.INSTANCE.sendMsg(
                    commandSender,
                    LangConfig.commandSetRaceNotExistRace.value(),
                    Map.of("%race%", raceId)
                );
                return;
            }
            PlayerRace playerRace = new PlayerRace(player.getUniqueId(), raceId, 0);
            AttributeHelper.removePlayerAllRaceAttribute(player);
            RaceManager.INSTANCE.setPlayerRaceCache(player.getUniqueId(), playerRace);
            RaceManager.INSTANCE.dataAccessor().setPlayerRace(player.getUniqueId(), playerRace);
            BukkitMsgSender.INSTANCE.sendMsg(
                commandSender,
                LangConfig.commandSetRaceSuccess.value(),
                Map.of("%player%", playerName, "%race%", race.name())
            );
        }

        @Override
        public @NotNull List<String> tab(@NotNull CommandSender commandSender, @NotNull List<String> args) {
            switch (args.size()) {
                case 0, 1 -> {
                    return Utils.getPlayerNames();
                }
                case 2 -> {
                    return RaceManager.INSTANCE.raceMap().keySet().stream().toList();
                }
                default -> {
                    return List.of("");
                }
            }
        }
    };

    @Subcommand
    BukkitSubCommand setLevel = new BukkitSubCommand("setLevel", new PermInfo("race.command.set-level")) {
        @Override
        public void execute(@NotNull CommandSender commandSender, @NotNull List<String> args) {
            if (args.isEmpty()) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, LangConfig.commandMissingPlayerName.value());
                return;
            }
            if (args.size() < 2) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, LangConfig.commandSetLevelMissingLevel.value());
                return;
            }
            String playerName = args.getFirst();
            Player player = Bukkit.getPlayer(playerName);
            if (player == null) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, LangConfig.commandPlayerOffline.value());
                return;
            }
            int level;
            try {
                level = Integer.parseInt(args.get(1));
            } catch (NumberFormatException e) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, LangConfig.commandSetLevelNumberFormatFailed.value());
                return;
            }

            PlayerRace playerRace = RaceManager.INSTANCE.getPlayerRace(player.getUniqueId());
            if (playerRace == null) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, LangConfig.commandSetLevelPlayerNoRace.value());
                return;
            }
            int result = playerRace.setRaceLevel(level);
            BukkitMsgSender.INSTANCE.sendMsg(
                commandSender,
                LangConfig.commandSetLevelSuccess.value(),
                Map.of("%player%", playerName, "%level%", result + "")
            );
        }

        @Override
        public @NotNull List<String> tab(@NotNull CommandSender commandSender, @NotNull List<String> args) {
            switch (args.size()) {
                case 0, 1 -> {
                    return Utils.getPlayerNames();
                }
                default -> {
                    return List.of("1", "2", "3");
                }
            }
        }
    };

    @Subcommand
    BukkitSubCommand exp = RaceExpCommand.INSTANCE;

}
