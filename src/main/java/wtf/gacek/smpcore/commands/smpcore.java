package wtf.gacek.smpcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import wtf.gacek.smpcore.SmpCore;
import wtf.gacek.smpcore.Utils;
import org.jetbrains.annotations.NotNull;

public class smpcore implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            Utils.colorize(sender, "&c");
            return true;
        }
        if ("reload".equals(args[0])) {
            if (!sender.hasPermission("smpcore.reload")) {
                Utils.colorize(sender, "&cYou cannot use this command!");
                return true;
            }
            SmpCore.getInstance().reloadConfig();
            Utils.colorize(sender, "&aReloaded!");
            return true;
        }
        Utils.colorize(sender, "&cInvalid subcommand");
        return true;
    }
}
