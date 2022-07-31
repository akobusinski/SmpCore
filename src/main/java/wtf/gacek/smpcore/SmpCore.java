package wtf.gacek.smpcore;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import wtf.gacek.smpcore.commands.pvp;
import wtf.gacek.smpcore.commands.smpcore;
import wtf.gacek.smpcore.completers.PvPTabCompleter;
import wtf.gacek.smpcore.completers.SmpTabCompleter;
import wtf.gacek.smpcore.listeners.*;
import wtf.gacek.smpcore.tasks.combatTimer;
import wtf.gacek.smpcore.tasks.pvpTimer;
import wtf.gacek.smpcore.tasks.scoreboardUpdater;
import wtf.gacek.smpcore.tasks.sotwTimer;

import java.io.*;
import java.util.*;

public final class SmpCore extends JavaPlugin {
    private static SmpCore instance;
    public final HashMap<UUID, ArrayList<String>> ScoreboardCache = new HashMap<>();
    public final HashMap<UUID, Integer> combatMap = new HashMap<>();
    public HashMap<UUID, Integer> pvpTimes = new HashMap<>();
    private BukkitTask scoreboardUpdaterTask;
    private BukkitTask sotwTimerTask;
    private BukkitTask pvpTimerTask;
    private BukkitTask combatTimerTask;

    @Override
    public void onEnable() {
        this.getLogger().info("Loading SMP Core");
        instance = this;
        if (!((new File(this.getDataFolder(), "config.yml")).exists())) {
            this.saveDefaultConfig();
        }
        File f = new File(this.getDataFolder(), "pvptimers");
        if (f.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(f);
                ObjectInputStream objectInput = new ObjectInputStream(inputStream);
                @SuppressWarnings("unchecked")
                HashMap<UUID, Integer> loaded = (HashMap<UUID, Integer>) objectInput.readObject();
                pvpTimes = loaded;
                objectInput.close();
                inputStream.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        Objects.requireNonNull(this.getCommand("smpcore")).setExecutor(new smpcore());
        Objects.requireNonNull(this.getCommand("smpcore")).setTabCompleter(new SmpTabCompleter());
        Objects.requireNonNull(this.getCommand("pvp")).setExecutor(new pvp());
        Objects.requireNonNull(this.getCommand("pvp")).setTabCompleter(new PvPTabCompleter());
        getServer().getPluginManager().registerEvents(new PortalListener(), this);
        getServer().getPluginManager().registerEvents(new SOTWListener(), this);
        getServer().getPluginManager().registerEvents(new PvPTimerListener(), this);
        getServer().getPluginManager().registerEvents(new CombatListener(), this);
        getServer().getPluginManager().registerEvents(new ScoreboardListener(), this);
        scoreboardUpdaterTask = new scoreboardUpdater().runTaskTimer(this, 0, 0);
        sotwTimerTask = new sotwTimer().runTaskTimer(this, 0, 10);
        pvpTimerTask = new pvpTimer().runTaskTimer(this, 0, 20);
        combatTimerTask = new combatTimer().runTaskTimer(this, 0, 0);
        instance.getLogger().info("Loaded!");
    }

    @Override
    public void onDisable() {
        if (scoreboardUpdaterTask != null && !scoreboardUpdaterTask.isCancelled()) {
            scoreboardUpdaterTask.cancel();
        }
        if (sotwTimerTask != null && !sotwTimerTask.isCancelled()) {
            sotwTimerTask.cancel();
        }
        if (pvpTimerTask != null && !pvpTimerTask.isCancelled()) {
            pvpTimerTask.cancel();
        }
        if (combatTimerTask != null && !combatTimerTask.isCancelled()) {
            combatTimerTask.cancel();
        }
        File f = new File(this.getDataFolder(), "pvptimers");
        try {
            FileOutputStream outputStream = new FileOutputStream(f);
            ObjectOutputStream objectOutput = new ObjectOutputStream(outputStream);
            objectOutput.writeObject(pvpTimes);
            objectOutput.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SmpCore getInstance() {
        return instance;
    }
}
