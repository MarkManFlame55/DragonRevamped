package net.espectralgames.dragonrevamped;

import net.espectralgames.dragonrevamped.commands.dragonCommand;
import net.espectralgames.dragonrevamped.fight.DragonBossFight;
import org.bukkit.plugin.java.JavaPlugin;

public final class DragonRevamped extends JavaPlugin {

    static DragonRevamped plugin;
    private boolean revamp_active;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        saveDefaultConfig();
        saveConfig();

        setCustomFight(getConfig().getBoolean("custom-fight-by-default"));

        getCommand("dragon").setExecutor(new dragonCommand());

        getServer().getPluginManager().registerEvents(new DragonBossFight(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static DragonRevamped getPlugin() {
        return plugin;
    }
    public boolean isCustomFight() {
        return this.revamp_active;
    }
    public void setCustomFight(boolean active) {
        this.revamp_active = active;
    }
}
