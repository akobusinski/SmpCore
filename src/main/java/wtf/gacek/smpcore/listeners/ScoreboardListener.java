package wtf.gacek.smpcore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import wtf.gacek.smpcore.SmpCore;

public class ScoreboardListener implements Listener {
    @EventHandler
    public static void onLogout(PlayerQuitEvent e) {
        SmpCore.getInstance().ScoreboardCache.remove(e.getPlayer().getUniqueId());
    }
}
