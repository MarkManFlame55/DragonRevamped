package net.espectralgames.dragonrevamped.entity;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Blaze;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EnderBlazeEntity {
    public static void spawn(Location pos) {
        Blaze blaze = pos.getWorld().spawn(pos, Blaze.class);
        blaze.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, PotionEffect.INFINITE_DURATION, 0, false, false, false));
        AttributeInstance maxHealth = blaze.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        blaze.customName(Component.text("Ender Blaze"));
        if (maxHealth != null) {
            maxHealth.setBaseValue(10D);
            blaze.setHealth(10D);
        }
    }
}
