package pers.yufiria.projectrace.command;

import crypticlib.command.CommandHandler;
import crypticlib.command.CommandInfo;
import crypticlib.command.SubcommandHandler;
import crypticlib.command.annotation.Command;
import crypticlib.command.annotation.Subcommand;
import crypticlib.perm.PermInfo;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import pers.yufiria.projectrace.ProjectRaceBukkit;
import top.zoyn.particlelib.pobject.*;
import top.zoyn.particlelib.utils.matrix.Matrixs;

import java.util.ArrayList;
import java.util.List;

@Command
public class TestCommand extends CommandHandler {

    public static TestCommand INSTANCE = new TestCommand();

    private TestCommand() {
        super(new CommandInfo("race-test", new PermInfo("race.command.test")));
    }

    @Subcommand
    SubcommandHandler shootDragonBall = new SubcommandHandler("shoot-dragon-ball") {
        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull List<String> args) {
            if (!(sender instanceof Player player)) {
                return false;
            }
            player.launchProjectile(DragonFireball.class);
            return true;
        }
    };

    @Subcommand
    SubcommandHandler summonCube = new SubcommandHandler("summon-cube") {
        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull List<String> args) {
            if (!(sender instanceof Player player)) {
                return false;
            }
            Cube cube = new Cube(player.getLocation().subtract(3, 3, 3), player.getLocation().add(3, 3, 3));
            cube.setColor(Color.WHITE)
                .addMatrix(Matrixs.rotateAroundXAxis(45))
                .addMatrix(Matrixs.rotateAroundZAxis(45))
                .alwaysShowAsync();
            new BukkitRunnable() {
                @Override
                public void run() {
                    cube.addMatrix(Matrixs.rotateAroundYAxis(2));
                }
            }.runTaskTimerAsynchronously(ProjectRaceBukkit.INSTANCE, 10, 1);
            return true;
        }
    };

    @Subcommand
    SubcommandHandler wing = new SubcommandHandler("wing") {
        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull List<String> args) {
            if (!(sender instanceof Player player)) {
                return false;
            }
            List<String> points = new ArrayList<>();
            points.add("    aaaaaaaaaaa");
            points.add("  aaaaaaaaa");
            points.add("aaaaaaa");
            points.add("aaaa");
            points.add(" aaaa");
            points.add("  aaaa");
            points.add("    aaa");
            points.add(" ");

            Wing wing = new Wing(player.getLocation(), points, -10D, 90D, 0.2D);
            wing.setSwing(true);
            wing.setColor(Color.RED)
                .setPeriod(1L)
                .attachEntity(player);
            wing.alwaysShowAsync();
            return true;
        }
    };

    @Subcommand
    SubcommandHandler matrix = new SubcommandHandler("matrix") {
        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull List<String> args) {
            if (!(sender instanceof Player player)) {
                return false;
            }
            Circle circle = new Circle(player.getLocation(), 1);
            Polygon polygon = new Polygon(3, player.getLocation());
            Polygon polygon1 = new Polygon(3, player.getLocation());
            polygon1.addMatrix(Matrixs.rotateAroundYAxis(180));
            new EffectGroup(circle, polygon, polygon1)
                .setColor(Color.RED)
                .setPeriod(1)
                .scale(2)
                .show();
            return true;
        }
    };

    @Subcommand
    SubcommandHandler lotus = new SubcommandHandler("lotus") {
        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull List<String> args) {
            if (!(sender instanceof Player player)) {
                return false;
            }
            Lotus lotus = new Lotus(player.getLocation());
            lotus.show();
            return true;
        }
    };

}
