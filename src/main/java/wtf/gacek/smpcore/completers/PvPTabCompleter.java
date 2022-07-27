package wtf.gacek.smpcore.completers;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PvPTabCompleter implements TabCompleter {
    @Nullable
    public ArrayList<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> completion = new ArrayList<>();
        if (args.length == 1)
            completion.add("enable");
        return completion;
    }
}
