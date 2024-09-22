package net.espectralgames.dragonrevamped.fight.attacks;

import net.espectralgames.dragonrevamped.DragonRevamped;
import net.espectralgames.dragonrevamped.util.DelayedTask;
import net.espectralgames.dragonrevamped.util.Time;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class ThunderAttack extends Attack {

    DragonRevamped plugin = DragonRevamped.getPlugin();

    @Override
    public void use(List<Player> players, EnderDragon enderDragon, World world) {

        this.plugin.getLogger().info(enderDragon.getName() + " ha usado Trueno");

        for (Player player : players) {
            if (!player.getGameMode().isInvulnerable()) {
                player.playSound(player, Sound.ENTITY_ENDER_DRAGON_AMBIENT, 1.0f, 1.0f);
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Time.secondsToTicks(5), 0, false, false, false));
            }
        }
        new DelayedTask(() -> {
            for (Player player : players) {
                if (!player.getGameMode().isInvulnerable()) {
                    Location pos = player.getLocation();
                    if (!pos.getBlock().isLiquid()) {
                        world.strikeLightning(pos);
                        player.damage(24, enderDragon);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, Time.secondsToTicks(20), 1));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Time.secondsToTicks(300), 1));
                    }
                }
            }
        }, Time.secondsToTicks(5));
    }
}
