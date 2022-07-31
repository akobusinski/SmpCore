package wtf.gacek.smpcore.tasks;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import wtf.gacek.smpcore.SmpCore;
import wtf.gacek.smpcore.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class combatTimer extends BukkitRunnable {
    public void run() {
        SmpCore instance = SmpCore.getInstance();
        HashMap<UUID, Integer> combatMap = new HashMap<>(instance.combatMap);
        for (Map.Entry<UUID, Integer> entry : combatMap.entrySet()) {
            Player p = Bukkit.getPlayer(entry.getKey());
            if (p == null || !p.isOnline()) {
                continue;
            }
            // max = 15
            // value = (200 / 20) = 10
            // left = max - (value / 20) = 15 - 10 = 5
            int maxTime = instance.getConfig().getInt("combat-tag");
            int value = (int) Math.ceil(entry.getValue() / 20d);
            int timeLeft = maxTime - value;
            p.spigot().sendMessage(
                    ChatMessageType.ACTION_BAR,
                    new ComponentBuilder("[")
                            .color(ChatColor.GRAY)
                            .append(Utils.stringRepeat("|", timeLeft))
                            .color(ChatColor.GREEN)
                            .append(Utils.stringRepeat("|", value))
                            .color(ChatColor.RED)
                            .append("] " + value + "s left")
                            .color(ChatColor.GRAY)
                            .create()
            );
            instance.combatMap.put(entry.getKey(), entry.getValue() - 1);
            if (entry.getValue() == 0) {
                instance.combatMap.remove(entry.getKey());
            }
        }
    }
}
