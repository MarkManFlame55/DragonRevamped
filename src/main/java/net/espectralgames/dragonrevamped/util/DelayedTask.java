package net.espectralgames.dragonrevamped.util;

import net.espectralgames.dragonrevamped.DragonRevamped;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class DelayedTask {
    private static Plugin plugin = DragonRevamped.getPlugin();
    private int id = -1;

    public DelayedTask(Plugin instance) {
        plugin = instance;
    }
    public DelayedTask(Runnable runnable) {
        this(runnable, 0);
    }
    public DelayedTask(Runnable runnable, long delay) {
        if (plugin.isEnabled()) {
            id = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, runnable, delay);
        } else {
            runnable.run();
        }
    }
}
