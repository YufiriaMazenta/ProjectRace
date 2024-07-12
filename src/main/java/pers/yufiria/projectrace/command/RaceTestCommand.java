package pers.yufiria.projectrace.command;

import crypticlib.chat.BukkitMsgSender;
import crypticlib.command.BukkitCommand;
import crypticlib.command.BukkitSubCommand;
import crypticlib.command.CommandInfo;
import crypticlib.command.annotation.Command;
import crypticlib.command.annotation.Subcommand;
import crypticlib.perm.PermInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pers.yufiria.projectrace.PlayerRace;
import pers.yufiria.projectrace.RaceManager;
import pers.yufiria.projectrace.race.vampire.Vampire;

import java.util.List;

@Command
public class RaceTestCommand extends BukkitCommand {

    public RaceTestCommand() {
        super(new CommandInfo(
            "race-test",
            new PermInfo("race.command.test")
        ));
    }

    @Subcommand
    BukkitSubCommand vampireSkill = new BukkitSubCommand("vampireSkill") {
        @Override
        public void execute(@NotNull CommandSender commandSender, @NotNull List<String> args) {
            if (!(commandSender instanceof Player player)) {
                BukkitMsgSender.INSTANCE.sendMsg(commandSender, "Only player can use this command!");
                return;
            }
            PlayerRace playerRace = RaceManager.INSTANCE.getPlayerRace(player.getUniqueId());
            if (playerRace == null || !playerRace.raceId().equals(Vampire.INSTANCE.id())) {
                BukkitMsgSender.INSTANCE.sendMsg(player, "Only vampire can release this skill!");
                return;
            }
            playerRace.race().releaseSkill(player, playerRace);
        }
    };

}
