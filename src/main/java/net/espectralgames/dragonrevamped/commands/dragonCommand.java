package net.espectralgames.dragonrevamped.commands;

import net.espectralgames.dragonrevamped.DragonRevamped;
import net.espectralgames.dragonrevamped.items.PluginItems;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class dragonCommand implements CommandExecutor, TabCompleter {

    DragonRevamped plugin = DragonRevamped.getPlugin();

    List<String> options = new ArrayList<>();

    public dragonCommand() {
        this.options.add("enable");
        this.options.add("disable");
        this.options.add("trophy");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("enable")) {
                if (!this.plugin.isCustomFight()) {
                    this.plugin.setCustomFight(true);
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<green>Se ha activado el dragon de Dragon Revamped!"));
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<green>La proxima batalla será con el nuevo dragon!"));
                    if (sender instanceof Player player) {
                        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BIT, 1.0f, 2.0f);
                    }
                } else {
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>La batalla custom ya esta activada!"));
                    if (sender instanceof Player player) {
                        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BIT, 1.0f, 0.1f);
                    }
                }
            }
            if (args[0].equalsIgnoreCase("disable")) {
                if (this.plugin.isCustomFight()) {
                    this.plugin.setCustomFight(false);
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<green>Se ha desactivado la batalla custom de Dragon Revamped!"));
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<green>La proxima batalla será contra el dragón Vanilla"));
                    if (sender instanceof Player player) {
                        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BIT, 1.0f, 2.0f);
                    }
                } else {
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>La batalla custom ya esta desactivada!"));
                    if (sender instanceof Player player) {
                        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BIT, 1.0f, 0.1f);
                    }
                }
            }
            if (args[0].equalsIgnoreCase("trophy")) {
                if (sender instanceof Player player) {
                    player.getInventory().addItem(PluginItems.DRAGON_PRIZE);
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<green>Has recibido" + PluginItems.DRAGON_PRIZE.getItemMeta().getItemName()));
                    player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BIT, 1.0f, 2.0f);
                }
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1 && sender instanceof Player) {
            List<String> completions = new ArrayList<>();
            StringUtil.copyPartialMatches(args[0], options, completions);
            Collections.sort(completions);
            return completions;
        }
        return null;
    }
}
