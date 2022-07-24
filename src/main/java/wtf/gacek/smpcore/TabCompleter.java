package wtf.gacek.smpcore;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completion = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("smpcore.reload")) {
                completion.add("reload");
            }
        }
        return completion;
    }
}
