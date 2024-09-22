package net.espectralgames.dragonrevamped.entity;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

public class DragonMinionEntity {
    public static void spawn(Location pos) {
        WitherSkeleton minion = pos.getWorld().spawn(pos, WitherSkeleton.class);

        minion.getEquipment().setHelmet(new ItemStack(Material.END_STONE));
        minion.getEquipment().setHelmetDropChance(0f);
        minion.getEquipment().setChestplate(trimmedArmor(Material.NETHERITE_CHESTPLATE, TrimPattern.EYE, TrimMaterial.QUARTZ));
        minion.getEquipment().setChestplateDropChance(0f);
        minion.getEquipment().setBoots(trimmedArmor(Material.NETHERITE_BOOTS, TrimPattern.SILENCE, TrimMaterial.QUARTZ));
        minion.getEquipment().setBootsDropChance(0f);
        minion.getEquipment().setItemInMainHand(sword(Material.NETHERITE_SWORD));
        minion.getEquipment().setItemInMainHandDropChance(0f);

        minion.customName(Component.text("Dragon Minion").decoration(TextDecoration.ITALIC, false));

        AttributeInstance maxHealth = minion.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealth != null) {
            maxHealth.setBaseValue(45D);
            minion.setHealth(maxHealth.getBaseValue());
        }
    }
    private static ItemStack trimmedArmor(Material armor, TrimPattern pattern, TrimMaterial material) {
        ItemStack itemStack = new ItemStack(armor);
        ArmorMeta armorMeta = (ArmorMeta) itemStack.getItemMeta();
        ArmorTrim trim = new ArmorTrim(material, pattern);
        armorMeta.setTrim(trim);
        itemStack.setItemMeta(armorMeta);
        return itemStack;
    }
    private static ItemStack sword(Material sword) {
        ItemStack itemStack = new ItemStack(sword);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.SHARPNESS, 1, true);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
