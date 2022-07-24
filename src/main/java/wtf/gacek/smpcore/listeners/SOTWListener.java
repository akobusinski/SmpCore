package wtf.gacek.smpcore.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import wtf.gacek.smpcore.SmpCore;

public class SOTWListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onMove(PlayerMoveEvent e) { // Make players unable to move but able to look around
        if ((int) Math.floor(System.currentTimeMillis() / 1000d) > SmpCore.getInstance().getConfig().getInt("sotw-time")) return;
        assert e.getTo() != null;
        if (e.getFrom().getX() == e.getTo().getX() && e.getFrom().getY() == e.getTo().getY() && e.getFrom().getZ() == e.getTo().getZ()) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onBlockBreak(BlockBreakEvent e) {
        if ((int) Math.floor(System.currentTimeMillis() / 1000d) > SmpCore.getInstance().getConfig().getInt("sotw-time")) return;
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onBlockPlace(BlockPlaceEvent e) {
        if ((int) Math.floor(System.currentTimeMillis() / 1000d) > SmpCore.getInstance().getConfig().getInt("sotw-time")) return;
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onEntityDamage(EntityDamageByEntityEvent e) {
        if ((int) Math.floor(System.currentTimeMillis() / 1000d) > SmpCore.getInstance().getConfig().getInt("sotw-time")) return;
        if (e.getDamager() instanceof Player) {
            return;
        }
        e.setCancelled(true);
    }
}
