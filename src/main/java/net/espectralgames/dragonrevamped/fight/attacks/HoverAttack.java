package net.espectralgames.dragonrevamped.fight.attacks;

import net.espectralgames.dragonrevamped.util.Time;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class HoverAttack extends Attack {

    @Override
    public void use(List<Player> player, EnderDragon enderDragon, World world) {
        Location pos = enderDragon.getDragonBattle().getEndPortalLocation().add(0,1,0);
        AreaEffectCloud cloud = world.spawn(pos, AreaEffectCloud.class);
        cloud.setDuration(Time.secondsToTicks(60));
        cloud.setColor(Color.WHITE);
        cloud.setOwnerUniqueId(enderDragon.getUniqueId());
        cloud.setRadius(16);
        cloud.setRadiusOnUse(0);
        cloud.setRadiusPerTick(0);
        cloud.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, Time.secondsToTicks(10), 0, false, true, true), true);
        cloud.addCustomEffect(new PotionEffect(PotionEffectType.HUNGER, Time.secondsToTicks(10), 1, false, true, true), true);
        cloud.addCustomEffect(new PotionEffect(PotionEffectType.POISON, Time.secondsToTicks(10), 2, false, true, true), true);
    }
}
