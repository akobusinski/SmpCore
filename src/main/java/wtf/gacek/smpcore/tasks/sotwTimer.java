package wtf.gacek.smpcore.tasks;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import wtf.gacek.smpcore.SmpCore;
import wtf.gacek.smpcore.Utils;

public class sotwTimer extends BukkitRunnable {
    public void run() {
        int current = (int) Math.floor(System.currentTimeMillis() / 1000d);
        int time = SmpCore.getInstance().getConfig().getInt("sotw-time");
        if (current > time) {
            return;
        }
        int diff = time - current;
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (new ComponentBuilder("The server is opening " + Utils.dateToText(diff))).color(ChatColor.RED).create());
        }
    }
}
