package wtf.gacek.smpcore.tasks;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import wtf.gacek.smpcore.SmpCore;
import wtf.gacek.smpcore.Utils;

public class scoreboardUpdater extends BukkitRunnable {
    public void run() {
        SmpCore instance = SmpCore.getInstance();
        int current = (int) Math.floor(System.currentTimeMillis() / 1000d);
        int sotw_diff = instance.getConfig().getInt("sotw-time") - current;
        int end_diff = instance.getConfig().getInt("end-open") - current;
        String sotw = Utils.convertDate(sotw_diff);
        String end = Utils.convertDate(end_diff);
        ArrayList<String> globalTexts = new ArrayList<>();
        if (sotw != null) {
            globalTexts.add("&6SOTW in&r&7: &c" + sotw);
        }
        if (end != null) {
            globalTexts.add("&3End opens in&r&7: &c" + end);
        }
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        assert manager != null;
        for (Player player : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = manager.getNewScoreboard();
            String scoreboardName = instance.getConfig().getString("scoreboard-name");
            Objective objective = scoreboard.registerNewObjective(Objects.requireNonNull(ChatColor.stripColor(scoreboardName)), "dummy", Utils.colorize(scoreboardName));
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            ArrayList<String> scoreboardText = new ArrayList<>();
            ArrayList<String> playerTexts = new ArrayList<>();
            if (instance.pvpTimes.containsKey(player.getUniqueId())) {
                String playerPvPTimer = Utils.convertDate(instance.pvpTimes.get(player.getUniqueId()));
                if (playerPvPTimer != null) {
                    playerTexts.add("&aPvP timer&r&7: &c" + playerPvPTimer);
                }
            }
            if (globalTexts.size() != 0 || playerTexts.size() != 0) {
                scoreboardText.add("&b");
            }
            if (globalTexts.size() != 0) {
                scoreboardText.addAll(globalTexts);
            }
            if (playerTexts.size() != 0) {
                scoreboardText.addAll(playerTexts);
            }
            scoreboardText.add("&f");
            scoreboardText.add("&7&o" + instance.getConfig().getString("discord-link"));
            Collections.reverse(scoreboardText);
            if (instance.ScoreboardCache.containsKey(player.getUniqueId()) && instance.ScoreboardCache.get(player.getUniqueId()).equals(scoreboardText)) {
                continue;
            }
            instance.ScoreboardCache.put(player.getUniqueId(), scoreboardText);
            int i = 0;
            for (String val: scoreboardText) {
                objective.getScore(Utils.colorize(val)).setScore(i);
                i++;
            }
            player.setScoreboard(scoreboard);
        }
    }
}