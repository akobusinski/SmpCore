package wtf.gacek.smpcore;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import wtf.gacek.smpcore.commands.smpcore;
import wtf.gacek.smpcore.listeners.SOTWListener;
import wtf.gacek.smpcore.listeners.PortalListener;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class SmpCore extends JavaPlugin {
    private static SmpCore instance;
    private static final HashMap<UUID, ArrayList<String>> ScoreboardCache = new HashMap<>();
    private BukkitTask scoreboardUpdater;
    private BukkitTask sotwTimer;

    @Override
    public void onEnable() {
        this.getLogger().info("Loading SMP Core");
        instance = this;
        if (!((new File(this.getDataFolder(), "config.yml")).exists())) {
            this.saveDefaultConfig();
        }
        Objects.requireNonNull(this.getCommand("smpcore")).setExecutor(new smpcore());
        Objects.requireNonNull(this.getCommand("smpcore")).setTabCompleter(new TabCompleter());
        getServer().getPluginManager().registerEvents(new PortalListener(), this);
        getServer().getPluginManager().registerEvents(new SOTWListener(), this);
        scoreboardUpdater = new BukkitRunnable() {
            @Override
            public void run() {
                int current = (int) Math.floor(System.currentTimeMillis() / 1000d);
                int sotw_diff = instance.getConfig().getInt("sotw-time") - current;
                int end_diff = instance.getConfig().getInt("end-open") - current;
                String sotw = Utils.convertDate(sotw_diff);
                String end = Utils.convertDate(end_diff);
                ScoreboardManager manager = Bukkit.getScoreboardManager();
                assert manager != null;
                for (Player player: Bukkit.getOnlinePlayers()) {
                    Scoreboard scoreboard = manager.getNewScoreboard();
                    String scoreboardName = instance.getConfig().getString("scoreboard-name");
                    Objective objective = scoreboard.registerNewObjective(Objects.requireNonNull(ChatColor.stripColor(scoreboardName)), "dummy", Utils.colorize(scoreboardName));
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                    ArrayList<String> scoreboardText = new ArrayList<>();
                    scoreboardText.add("&a");
                    if (sotw != null) scoreboardText.add("&6SOTW in: " + sotw);
                    if (end != null) scoreboardText.add("&3End opens in: " + end);
                    scoreboardText.add("&b");
                    scoreboardText.add("&7&o" + instance.getConfig().getString("discord-link"));
                    Collections.reverse(scoreboardText);
                    if (ScoreboardCache.containsKey(player.getUniqueId()) && ScoreboardCache.get(player.getUniqueId()).equals(scoreboardText)) {
                        continue;
                    }
                    ScoreboardCache.put(player.getUniqueId(), scoreboardText);
                    AtomicInteger i = new AtomicInteger();
                    scoreboardText.forEach((val) -> {
                        objective.getScore(Utils.colorize(val)).setScore(i.get());
                        i.getAndIncrement();
                    });
                    player.setScoreboard(scoreboard);
                }
            }
        }.runTaskTimer(this, 0, 0);
        sotwTimer = new BukkitRunnable() {
            @Override
            public void run() {
                int current = (int) Math.floor(System.currentTimeMillis() / 1000d);
                int time = instance.getConfig().getInt("sotw-time");
                if (current > time) {
                    return;
                }
                int diff = time - current;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("The server is opening " + Utils.dateToText(diff)).color(net.md_5.bungee.api.ChatColor.RED).create());
                }
            }
        }.runTaskTimer(this, 0, 20);
        instance.getLogger().info("Loaded!");
    }

    @Override
    public void onDisable() {
        if (scoreboardUpdater != null && !scoreboardUpdater.isCancelled()) {
            scoreboardUpdater.cancel();
        }
        if (sotwTimer != null && !sotwTimer.isCancelled()) {
            sotwTimer.cancel();
        }
    }

    public static SmpCore getInstance() {
        return instance;
    }
}
