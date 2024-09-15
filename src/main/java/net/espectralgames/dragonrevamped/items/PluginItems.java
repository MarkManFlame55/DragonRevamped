package net.espectralgames.dragonrevamped.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PluginItems {


    public static final ItemStack DRAGON_PRIZE = newPluginItem(Material.BEDROCK, "<dark_red><b>Bloque del Guardian", 1, true, true, createLore("", "<dark_gray>Otorgado a aquel que consiguo asestarle", "<dark_gray>el golpe definitivo al dragon mas poderoso", "<dark_gray>de este mundo...", "", "<gold><b>ITEM ÚNICO"));



    private static ItemStack newPluginItem(Material material, String name, int maxStackSize , boolean glint, boolean fireproof, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.itemName(MiniMessage.miniMessage().deserialize(name));
        itemMeta.setEnchantmentGlintOverride(glint);
        itemMeta.setMaxStackSize(maxStackSize);
        itemMeta.setFireResistant(fireproof);
        List<Component> itemLore = new ArrayList<>();
        for (String string : lore) {
            itemLore.add(MiniMessage.miniMessage().deserialize(string));
        }
        itemMeta.lore(itemLore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private static List<String> createLore(String... lines) {
        return new ArrayList<>(Arrays.asList(lines));
    }
}

