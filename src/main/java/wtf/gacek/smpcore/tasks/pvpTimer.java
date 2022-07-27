package wtf.gacek.smpcore.tasks;

import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import wtf.gacek.smpcore.SmpCore;

public class pvpTimer extends BukkitRunnable {
    public void run() {
        int current = (int)Math.floor(System.currentTimeMillis() / 1000d);
        SmpCore instance = SmpCore.getInstance();
        int time = instance.getConfig().getInt("sotw-time");
        if (current <= time) {
            return;
        }
        for (Map.Entry<UUID, Integer> entry : instance.pvpTimes.entrySet()) {
            if (!Bukkit.getOfflinePlayer(entry.getKey()).isOnline()) {
                continue;
            }
            if (entry.getValue() - 1 == 0) {
                instance.pvpTimes.remove(entry.getKey());
                continue;
            }
            instance.pvpTimes.put(entry.getKey(), entry.getValue() - 1);
        }
    }
}
