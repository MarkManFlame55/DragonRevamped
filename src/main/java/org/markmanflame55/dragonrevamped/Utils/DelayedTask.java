package org.markmanflame55.dragonrevamped.Utils;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.markmanflame55.dragonrevamped.DragonRevamped;

public class DelayedTask implements Listener {
    private static Plugin plugin = DragonRevamped.getPlugin(DragonRevamped.class);
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
