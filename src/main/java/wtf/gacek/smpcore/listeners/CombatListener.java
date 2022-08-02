package wtf.gacek.smpcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import wtf.gacek.smpcore.SmpCore;
import wtf.gacek.smpcore.Utils;

import java.util.List;

public class CombatListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onAttack(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player && e.getEntity() instanceof Player)) {
            return;
        }

        Player damager = (Player) e.getDamager();
        Player player = (Player) e.getEntity();
        SmpCore instance = SmpCore.getInstance();
        int combatTime = instance.getConfig().getInt("combat-tag") * 20;

        instance.combatMap.put(damager.getUniqueId(), combatTime);
        instance.combatMap.put(player.getUniqueId(), combatTime);
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent e) {
        SmpCore instance = SmpCore.getInstance();
        Player p = e.getPlayer();
        if (instance.combatMap.containsKey(p.getUniqueId())) {
            p.setHealth(0d);
        }
        instance.combatMap.remove(p.getUniqueId());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        SmpCore instance = SmpCore.getInstance();
        instance.combatMap.remove(p.getUniqueId());
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        SmpCore instance = SmpCore.getInstance();
        if (!instance.combatMap.containsKey(p.getUniqueId())) {
            return;
        }
        List<?> blocked = instance.getConfig().getList("blocked-commands");
        if (blocked == null || blocked.isEmpty()) {
            return;
        }
        instance.getLogger().info(e.getMessage());
        if (blocked.contains(e.getMessage().split(" ")[0])) {
            e.setCancelled(true);
            Utils.colorize(p, "&cYou cannot use " + e.getMessage().split(" ")[0] +" in PvP!");
        }
    }
}
