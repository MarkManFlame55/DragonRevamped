package org.markmanflame55.dragonrevamped;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.markmanflame55.dragonrevamped.Commands.DragonCommand;
import org.markmanflame55.dragonrevamped.EventListeners.DragonBossFight;
import org.markmanflame55.dragonrevamped.EventListeners.EndWatcherEvent;

import java.util.ArrayList;
import java.util.List;

public final class DragonRevamped extends JavaPlugin implements Listener {

    public static String pluginName = "DragonRevamped";
    public static Boolean onFight = false;
    public static String pluginVersion = "1.0";
    @Override
    public void onEnable() {
        System.out.println("[" + pluginName + "] Plugin enabled succesfully ");

        getCommand("dragon").setExecutor(new DragonCommand());

        getServer().getPluginManager().registerEvents(new DragonBossFight(), this);
        getServer().getPluginManager().registerEvents(new EndWatcherEvent(), this);
        getServer().getPluginManager().registerEvents(this, this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        System.out.println("[" + pluginName + "] See ya!");
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (e.getPlayer().isOp()) {
            List<String> msg = new ArrayList<>();
            msg.add(ChatColor.DARK_PURPLE + "============================================================================");
            msg.add("");
            msg.add(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "           D R A G O N   R E V A M P  v1.0");
            msg.add("");
            msg.add(ChatColor.GRAY + "You are currently using version " + pluginVersion);
            msg.add(ChatColor.GRAY + "If something its not working do /reload or report the bug on Spigot Page");
            msg.add(ChatColor.GRAY + "https://www.spigotmc.org/members/markmanflame_55.1805917/");
            msg.add("");
            msg.add(ChatColor.DARK_PURPLE + "============================================================================");

            for (String line : msg) {
                e.getPlayer().sendMessage(line);
            }
        }
    }
}
