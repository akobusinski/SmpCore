package wtf.gacek.smpcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wtf.gacek.smpcore.SmpCore;
import wtf.gacek.smpcore.Utils;

public class pvp implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            Utils.colorize(sender, "&c/pvp enable - Removes your PvP protection");
            return true;
        }
        if (args[0].equals("enable")) {
            if (!(sender instanceof Player)) {
                Utils.colorize(sender, "&cYou have to be a player to use this command!");
                return true;
            }
            SmpCore instance = SmpCore.getInstance();
            Player p = (Player) sender;
            if (!instance.pvpTimes.containsKey(p.getUniqueId())) {
                Utils.colorize(sender, "&cYour PvP timer already expired!");
                return true;
            }
            instance.pvpTimes.remove(p.getUniqueId());
            Utils.colorize(sender, "&aEnabled!");
            return true;
        }
        Utils.colorize(sender, "&cInvalid subcommand");
        return true;
    }
}
