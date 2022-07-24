package wtf.gacek.smpcore.listeners;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import wtf.gacek.smpcore.SmpCore;
import wtf.gacek.smpcore.Utils;

import java.util.Objects;

public class PortalListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onPortalEnter(PlayerPortalEvent e) {
        if (!Objects.requireNonNull(Objects.requireNonNull(e.getTo()).getWorld()).getEnvironment().equals(World.Environment.THE_END)) {
            return;
        }
        if (!((int) Math.floor(System.currentTimeMillis() / 1000d) < SmpCore.getInstance().getConfig().getInt("end-open"))) {
            return;
        }
        e.setCancelled(true);
        Utils.colorize(e.getPlayer(), "&cThe end is currently closed!");
    }
}
