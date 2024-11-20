package wtf.gacek.smpcore.listeners;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import wtf.gacek.smpcore.SmpCore;
import wtf.gacek.smpcore.Utils;

import java.util.Objects;

public class PortalListener implements Listener {
    private static final String END_CLOSED_MESSAGE = "&cThe end is currently closed!";

    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onPlayerTeleport(PlayerTeleportEvent e) {
        if (shouldClose(Objects.requireNonNull(Objects.requireNonNull(e.getTo()).getWorld()).getEnvironment())) {
            e.setCancelled(true);
            Utils.colorize(e.getPlayer(), END_CLOSED_MESSAGE);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onPortalEnter(PlayerPortalEvent e) {
        if (shouldClose(Objects.requireNonNull(Objects.requireNonNull(e.getTo()).getWorld()).getEnvironment())) {
            e.setCancelled(true);
            Utils.colorize(e.getPlayer(), END_CLOSED_MESSAGE);
        }
    }

    private static boolean shouldClose(World.Environment environment) {
        if (!environment.equals(World.Environment.THE_END)) {
            return false;
        }

        return (int) Math.floor(System.currentTimeMillis() / 1000d) < SmpCore.getInstance().getConfig().getInt("end-open");
    }
}
