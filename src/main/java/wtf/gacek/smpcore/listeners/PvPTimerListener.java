package wtf.gacek.smpcore.listeners;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
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

    @EventHandler(priority = EventPriority.LOWEST)
    public static void onEntityDamage(EntityDamageByEntityEvent e) {
        SmpCore instance = SmpCore.getInstance();
        Entity damager = e.getDamager();
        Entity entity = e.getEntity();
        if (damager instanceof Player && entity instanceof Player && instance.pvpTimes.containsKey(damager.getUniqueId())) {
            e.setCancelled(true);
            Utils.colorize(damager, "&cYou cannot damage this player due to your PvP timer being on");
            Utils.colorize(damager, "&cYou can disable your PvP timer with &6/pvp enable");
            return;
        }
        if (entity instanceof Player && damager instanceof Player && instance.pvpTimes.containsKey(entity.getUniqueId())) {
            e.setCancelled(true);
            Utils.colorize(damager, "&cThis player cannot be hurt due to their PvP timer being on");
        }
    }
}
