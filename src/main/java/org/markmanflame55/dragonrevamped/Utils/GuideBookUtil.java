package org.markmanflame55.dragonrevamped.Utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class GuideBookUtil {
    public static ItemStack giveGuideBook() {

        String nl = "\n";
        ItemStack guide = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta itemMeta = (BookMeta) guide.getItemMeta();
        itemMeta.setTitle(ChatColor.LIGHT_PURPLE + "Dragon Revamp Battle Guide");
        itemMeta.setAuthor("MarkManFlame_55");


        List<String> pages = new ArrayList<>();
        pages.add(
                0, ChatColor.BOLD + "================"
                + nl
                + nl
                + nl
                + ChatColor.LIGHT_PURPLE + "     DRAGON REVAMP"
                + ChatColor.DARK_PURPLE + "            Battle Guide"
                + nl
                + nl
                + nl
                + nl
                + nl
                + nl
                + ChatColor.ITALIC + " By MarkManFlame_55"
                + nl
                + nl
                + nl
                + ChatColor.BLACK + "" + ChatColor.BOLD + "================"
        );
        pages.add(1,
                ChatColor.DARK_RED + "STAGES:"
                + nl
                + nl
                + ChatColor.BLACK + "The Ender Dragon has 3 different stages depending on its health"
                + nl + "It starts 1000 health points, when it goes below 650 HP, it will become " + ChatColor.LIGHT_PURPLE + "ENRAGED ENDER DRAGON" + ChatColor.BLACK + " and below 350 HP it will become " + ChatColor.DARK_RED + "FINAL ENDER DRAGON"
        );
        pages.add(2,
                ChatColor.DARK_RED + "ATTACKS:"
                + nl
                + nl
                + ChatColor.BLACK + "The plugins introduces 7 different new attacks"
                + nl + "The Dragon has the same chance to do most of the attacks but it will also depend on the stage its on"
                + nl
                + nl + "See next pages to know more about the attacks"
        );
        pages.add(3,
                ChatColor.RED + "Minions Rise:"
                + nl
                + nl
                + ChatColor.BLACK + "Spawn 3 End Warrriors around every player on the battle"
                + nl + "They are equipped with Sharpness V Knockback II End Crystals"
        );
        pages.add(4,
                ChatColor.RED + "Lightning:"
                + nl
                + nl
                + ChatColor.BLACK + "Every player starts to see the sky blinking, and before it stops they have to be on water. If not, a lightning will strike the players applying Weakness for 5 minutes and Slowness 10 seconds"
        );
        pages.add(5,
                ChatColor.RED + "TNT Rain: "
                + nl
                + nl
                + ChatColor.BLACK + "The Ender Dragon starts dropping 5 TNTs with high explosion power. They explode at the same time"
        );
        pages.add(6,
                ChatColor.RED + "Launch: "
                + nl
                + nl
                + ChatColor.BLACK + "Every player is launched vertically into the air"
                + nl
                + nl + "(" + ChatColor.LIGHT_PURPLE + "STAGE 2" + ChatColor.BLACK + " & " + ChatColor.DARK_RED + "STAGE 3" + ChatColor.BLACK + " exclusive."
        );
        pages.add(7,
                ChatColor.RED + "Poison Cloud:"
                + nl
                + nl
                + ChatColor.BLACK + "Every player recieves Darkness, they must keep running to avoid a Poisoned Cloud that will spawn in every player´s locations applying Instant Damage"
                + nl
                + nl + "(" + ChatColor.LIGHT_PURPLE + "STAGE 2" + ChatColor.BLACK + " & " + ChatColor.DARK_RED + "STAGE 3" + ChatColor.BLACK + " exclusive."
        );
        pages.add(8,
            ChatColor.RED + "End Spikes:"
                + nl
                + nl
                + ChatColor.BLACK + "5 obsidian plates start appearing beneath the player and after a few seconds, spikes came out of the plate and hurt everything they touch"
                + nl
                + nl + "(" + ChatColor.LIGHT_PURPLE + "STAGE 2" + ChatColor.BLACK + " & " + ChatColor.DARK_RED + "STAGE 3" + ChatColor.BLACK + " exclusive."

        );
        pages.add(9,
                ChatColor.RED + "Summon Crystal Guardian:"
                + nl
                + nl
                + ChatColor.BLACK + "Spawn a Guardian in the center of the island, if players dont killed it in 1 minute, it will restore all End Crystals"
                + nl
                + nl + "(" + ChatColor.LIGHT_PURPLE + "STAGE 2" + ChatColor.BLACK + " & " + ChatColor.DARK_RED + "STAGE 3" + ChatColor.BLACK + " exclusive."
        );
        pages.add(10,
                ChatColor.DARK_RED + "More to Know:"
                + nl
                + nl
                + ChatColor.BLACK + "Destroying the end crystals has a 50% to spawn a Ghast"
        );
        pages.add(11,
                ChatColor.DARK_RED + "More to Know:"
                + nl
                + nl
                + ChatColor.BLACK + "Every few minutes the dragon has a chance to summon 4 End Watcher Towers that grants immunity to the dragon. They also shoot to players at 15 blocks"
                + nl
                + nl + "(" + ChatColor.LIGHT_PURPLE + "STAGE 2" + ChatColor.BLACK + " & " + ChatColor.DARK_RED + "STAGE 3" + ChatColor.BLACK + " exclusive."
        );
        itemMeta.setPages(pages);

        guide.setItemMeta(itemMeta);
        return guide;
    }
}
