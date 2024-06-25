package pers.yufiria.projectrace.command;

import crypticlib.chat.MsgSender;
import crypticlib.command.CommandHandler;
import crypticlib.command.CommandInfo;
import crypticlib.command.SubcommandHandler;
import crypticlib.command.annotation.Command;
import crypticlib.command.annotation.Subcommand;
import crypticlib.perm.PermInfo;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import pers.yufiria.projectrace.ProjectRaceBukkit;

import java.util.List;

@Command
public class PluginCommand extends CommandHandler {

    public static final PluginCommand INSTANCE = new PluginCommand();

    public PluginCommand() {
        super(new CommandInfo("race", new PermInfo("race.command")));
    }

    @Subcommand
    SubcommandHandler reload = new SubcommandHandler("reload", new PermInfo("race.command.reload")) {
        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull List<String> args) {
            ProjectRaceBukkit.INSTANCE.reloadConfig();
            MsgSender.sendMsg(sender, "[ProjectRace] Reloaded config.");
            //todo 完善重载
            return true;
        }
    };

}
