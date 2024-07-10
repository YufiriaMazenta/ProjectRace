package pers.yufiria.projectrace.command;

import crypticlib.chat.BukkitMsgSender;
import crypticlib.chat.MsgSender;
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
import org.jetbrains.annotations.Nullable;
import pers.yufiria.projectrace.PlayerRace;
import pers.yufiria.projectrace.ProjectRaceBukkit;
import pers.yufiria.projectrace.RaceManager;
import pers.yufiria.projectrace.RaceTicker;
import pers.yufiria.projectrace.config.LangConfig;
import pers.yufiria.projectrace.race.Race;
import pers.yufiria.projectrace.util.AttributeHelper;

import java.util.ArrayList;
import java.util.List;

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
    BukkitSubCommand setRace = new BukkitSubCommand("setRace", new PermInfo("race.command.setRace")) {
        @Override
        public void execute(@NotNull CommandSender commandSender, @NotNull List<String> args) {
            if (args.size() < 2) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, "Missing args.");
                return;
            }
            String playerName = args.get(0);
            String raceId = args.get(1);
            Player player = Bukkit.getPlayer(playerName);
            if (player == null) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, "Player " + playerName + " is not online!");
                return;
            }
            Race race = RaceManager.INSTANCE.getRace(raceId);
            if (race == null) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, "Unknown race " + raceId);
                return;
            }
            PlayerRace playerRace = new PlayerRace(raceId, 0);
            AttributeHelper.removePlayerAllRaceAttribute(player);
            RaceManager.INSTANCE.setPlayerRace(player.getUniqueId(), playerRace);
            BukkitMsgSender.INSTANCE.sendMsg(commandSender, "Set player " + playerName + "'s race to " + race.name());
            RaceManager.INSTANCE.dataAccessor().setPlayerRace(player.getUniqueId(), playerRace);
        }

        @Override
        public @NotNull List<String> tab(@NotNull CommandSender commandSender, @NotNull List<String> args) {
            switch (args.size()) {
                case 0, 1 -> {
                    return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
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
    BukkitSubCommand setRaceLevel = new BukkitSubCommand("setRaceLevel", new PermInfo("race.command.setRaceLevel")) {
        @Override
        public void execute(@NotNull CommandSender commandSender, @NotNull List<String> args) {
            if (args.size() < 2) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, "Missing args.");
                return;
            }
            String playerName = args.getFirst();
            Player player = Bukkit.getPlayer(playerName);
            if (player == null) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, "Player " + playerName + " is not online!");
                return;
            }
            int level;
            try {
                level = Integer.parseInt(args.get(1));
            } catch (NumberFormatException e) {
                level = 0;
            }

            PlayerRace playerRace = RaceManager.INSTANCE.getPlayerRace(player.getUniqueId());
            if (playerRace == null) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, "Player do not have race ");
                return;
            }
            playerRace.setRaceLevel(level);
            RaceManager.INSTANCE.dataAccessor().changePlayerRaceLevel(player.getUniqueId(), level);
        }

        @Override
        public @NotNull List<String> tab(@NotNull CommandSender commandSender, @NotNull List<String> args) {
            switch (args.size()) {
                case 0, 1 -> {
                    return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
                }
                default -> {
                    return List.of("1", "2", "3");
                }
            }
        }
    };

}
