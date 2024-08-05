package pers.yufiria.projectrace.command.sub;

import crypticlib.chat.BukkitMsgSender;
import crypticlib.command.BukkitSubcommand;
import crypticlib.command.annotation.Subcommand;
import crypticlib.perm.PermInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.yufiria.projectrace.PlayerRace;
import pers.yufiria.projectrace.RaceManager;
import pers.yufiria.projectrace.config.LangConfig;
import pers.yufiria.projectrace.util.Utils;

import java.util.List;
import java.util.Map;

public class RaceExpCommand extends BukkitSubcommand {

    public static final RaceExpCommand INSTANCE = new RaceExpCommand();

    private RaceExpCommand() {
        super("exp", new PermInfo("race.command.exp"));
    }

    @Subcommand
    BukkitSubcommand set = new BukkitSubcommand("set") {
        @Override
        public void execute(@NotNull CommandSender commandSender, @NotNull List<String> args) {
            if (args.isEmpty()) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, LangConfig.commandMissingPlayerName.value());
                return;
            }
            if (args.size() < 2) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, LangConfig.commandExpExpMissingExp.value());
                return;
            }

            changePlayerRaceExp(commandSender, args.get(0), args.get(1), RaceExpChangeType.SET);
        }

        @Override
        public @Nullable List<String> tab(@NotNull CommandSender commandSender, @NotNull List<String> args) {
            return tabExpArgs(args);
        }
    };

    @Subcommand
    BukkitSubcommand give = new BukkitSubcommand("give") {
        @Override
        public void execute(@NotNull CommandSender commandSender, @NotNull List<String> args) {
            if (args.isEmpty()) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, LangConfig.commandMissingPlayerName.value());
                return;
            }
            if (args.size() < 2) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, LangConfig.commandExpExpMissingExp.value());
                return;
            }
            changePlayerRaceExp(commandSender, args.get(0), args.get(1), RaceExpChangeType.ADD);
        }

        @Override
        public @Nullable List<String> tab(@NotNull CommandSender commandSender, @NotNull List<String> args) {
            return tabExpArgs(args);
        }
    };

    @Subcommand
    BukkitSubcommand take = new BukkitSubcommand("take") {
        @Override
        public void execute(@NotNull CommandSender commandSender, @NotNull List<String> args) {
            if (args.isEmpty()) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, LangConfig.commandMissingPlayerName.value());
                return;
            }
            if (args.size() < 2) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, LangConfig.commandExpExpMissingExp.value());
                return;
            }
            changePlayerRaceExp(commandSender, args.get(0), args.get(1), RaceExpChangeType.SUBTRACT);
        }

        @Override
        public @Nullable List<String> tab(@NotNull CommandSender commandSender, @NotNull List<String> args) {
            return tabExpArgs(args);
        }
    };

    public void changePlayerRaceExp(CommandSender operator, String playerName, String raceExp, RaceExpChangeType raceExpChangeType) {
        Player player = Bukkit.getPlayer(playerName);
        if (player == null) {
            BukkitMsgSender.INSTANCE.sendMsg(operator, LangConfig.commandPlayerOffline.value());
            return;
        }
        double exp;
        try {
            exp = Double.parseDouble(raceExp);
        } catch (NumberFormatException e) {
            BukkitMsgSender.INSTANCE.sendMsg(operator, LangConfig.commandExpNumberFormatFailed.value());
            return;
        }

        PlayerRace playerRace = RaceManager.INSTANCE.getPlayerRace(player.getUniqueId());
        if (playerRace == null) {
            BukkitMsgSender.INSTANCE.sendMsg(operator, LangConfig.commandExpPlayerNoRace.value());
            return;
        }
        double resultExp;
        switch (raceExpChangeType) {
            case ADD -> resultExp = playerRace.addRaceExp(exp);
            case SUBTRACT ->  resultExp = playerRace.subtractRaceExp(exp);
            default -> resultExp = playerRace.setRaceExp(exp);
        }
        double level = playerRace.raceLevel();
        BukkitMsgSender.INSTANCE.sendMsg(
            operator,
            LangConfig.commandSetExpSuccess.value(),
            Map.of("%player%", player.getName(), "%exp%", resultExp + "", "%level%", level + "")
        );
    }

    public @Nullable List<String> tabExpArgs(@NotNull List<String> args) {
        if (args.size() <= 1) {
            return Utils.getPlayerNames();
        } else {
            return List.of("");
        }
    }

    enum RaceExpChangeType {
        ADD, SET, SUBTRACT
    }

}
