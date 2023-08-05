package org.markmanflame55.dragonrevamped.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.markmanflame55.dragonrevamped.DragonRevamped;
import org.markmanflame55.dragonrevamped.Utils.GuideBookUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DragonCommand implements CommandExecutor, TabCompleter {

    List<String> options = new ArrayList<>();
    List<String> options2 = new ArrayList<>();
    public DragonCommand() {
        options.add("stage");
        options.add("kill");
        options.add("credits");
        options.add("guide");
        options2.add("1");
        options2.add("2");
        options2.add("3");
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
                if (strings.length == 0) {
                    player.sendMessage(ChatColor.RED + "Uncompleted Command: /dragon (stage/kill/credits) [1/2/3]");
                } else if (strings.length == 2) {
                    if (commandSender.isOp()) {
                        if (strings[0].equalsIgnoreCase("stage")) {
                            if (strings[1].equalsIgnoreCase("1")) {
                                for (LivingEntity enderDragon : player.getWorld().getLivingEntities()) {
                                    if (enderDragon instanceof EnderDragon) {
                                        enderDragon.setHealth(1000);
                                    }
                                }
                            } else if (strings[1].equalsIgnoreCase("2")) {
                                for (LivingEntity enderDragon : player.getWorld().getLivingEntities()) {
                                    if (enderDragon instanceof EnderDragon) {
                                        enderDragon.setHealth(600);
                                    }
                                }
                            } else if (strings[1].equalsIgnoreCase("3")) {
                                for (LivingEntity enderDragon : player.getWorld().getLivingEntities()) {
                                    if (enderDragon instanceof EnderDragon) {
                                        enderDragon.setHealth(250);
                                    }
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Ender Dragon doesnt have that stage");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Doesn´t expect that many entries");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You don´t have permissions to do that");
                    }
                } else if (strings.length == 1) {
                    if (strings[0].equalsIgnoreCase("credits")) {
                        List<String> credits = new ArrayList<>();

                        credits.add(ChatColor.DARK_PURPLE + "============================================================================");
                        credits.add("");
                        credits.add(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "           D R A G O N   R E V A M P  v" + DragonRevamped.pluginVersion);
                        credits.add("");
                        credits.add(ChatColor.WHITE + "Plugin Developer: " + ChatColor.AQUA + "MarkManFlame_55");
                        credits.add("");
                        credits.add(ChatColor.RED + "" + ChatColor.BOLD + "YT: " + ChatColor.WHITE + "https://youtube.com/@markmanflame_5542");
                        credits.add(ChatColor.AQUA + "" +ChatColor.BOLD + "Twitter: " + ChatColor.WHITE + "https://twitter.com/MarkManFlame_55");
                        credits.add(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Twitch: " + ChatColor.WHITE + "https://www.twitch.tv/markmanflame_55");
                        credits.add("");
                        credits.add("");
                        credits.add(ChatColor.WHITE + "Check for new updates or more plugins on: " + ChatColor.YELLOW + "https://www.spigotmc.org/members/markmanflame_55.1805917/");
                        credits.add("");
                        credits.add(ChatColor.DARK_PURPLE + "============================================================================");

                        for (String i : credits) {
                            player.sendMessage(i);
                        }
                    } else if (strings[0].equalsIgnoreCase("kill")) {
                        if (player.isOp()) {
                            for (LivingEntity enderDragon : player.getWorld().getLivingEntities()) {
                                if (enderDragon instanceof EnderDragon) {
                                    enderDragon.setHealth(0);
                                }
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You don´t have permissions to do that");
                        }
                    } else if (strings[0].equalsIgnoreCase("guide")) {

                        player.getInventory().addItem(GuideBookUtil.giveGuideBook());

                    } else {
                            player.sendMessage(ChatColor.RED + "Invalid option: /dragon (stage/kill/credits/guide) [1/2/3]");
                    }
                }

        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 2 && commandSender instanceof Player && strings[0].equalsIgnoreCase("stage")) {
            List<String> completions = new ArrayList<>();
            StringUtil.copyPartialMatches(strings[0], options, completions);
            StringUtil.copyPartialMatches(strings[1], options2, completions);
            Collections.sort(completions);
            return completions;
        } else if (strings.length == 1 && commandSender instanceof Player) {
            List<String> completions2 = new ArrayList<>();
            StringUtil.copyPartialMatches(strings[0], options, completions2);
            Collections.sort(completions2);
            return completions2;
        }
        return null;
    }
}
