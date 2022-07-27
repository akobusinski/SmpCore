package wtf.gacek.smpcore.listeners;

import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import wtf.gacek.smpcore.SmpCore;
import wtf.gacek.smpcore.Utils;

public class PvPTimerListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasPlayedBefore()) {
            SmpCore instance = SmpCore.getInstance();
            instance.pvpTimes.put(e.getPlayer().getUniqueId(), instance.getConfig().getInt("pvp-timer"));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onEntityDamage(EntityDamageByEntityEvent e) {
        SmpCore instance = SmpCore.getInstance();
        if (e.getDamager() instanceof org.bukkit.entity.Player && instance.pvpTimes.containsKey(e.getDamager().getUniqueId()) && e.getEntity() instanceof org.bukkit.entity.Player) {
            e.setCancelled(true);
            Utils.colorize(e.getDamager(), "&cYou cannot damage this player due to your PvP timer being on");
            Utils.colorize(e.getDamager(), "&cYou can disable your PvP timer with &6/pvp enable");
            return;
        }
        if (e.getEntity() instanceof org.bukkit.entity.Player && instance.pvpTimes.containsKey(e.getEntity().getUniqueId()) && e.getDamager() instanceof org.bukkit.entity.Player) {
            e.setCancelled(true);
            Utils.colorize(e.getDamager(), "&cThis player cannot be hurt due to their PvP timer being on");
        }
    }
}
