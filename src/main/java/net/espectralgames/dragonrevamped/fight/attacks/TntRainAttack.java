package net.espectralgames.dragonrevamped.fight.attacks;

import net.espectralgames.dragonrevamped.DragonRevamped;
import net.espectralgames.dragonrevamped.util.Time;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class TntRainAttack extends Attack {

    DragonRevamped plugin = DragonRevamped.getPlugin();

    int startFuseTicks = Time.secondsToTicks(5);
    int TICKS_BETWEEN_TNT = 10;

    @Override
    public void use(List<Player> players, EnderDragon enderDragon, World world) {

        this.plugin.getLogger().info(enderDragon.getName() + " ha usado Lluvia de TNT!");

        new BukkitRunnable() {
            int fuseTicks = startFuseTicks;
            int tntCount = 1;
            @Override
            public void run() {
                if (tntCount < 6) {
                    TNTPrimed tnt = world.spawn(enderDragon.getLocation(), TNTPrimed.class);
                    tnt.setFuseTicks(fuseTicks);
                    tnt.setSource(enderDragon);
                    tnt.setIsIncendiary(true);
                    tnt.setYield(10f);
                    world.playSound(enderDragon.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 3.0f, 1.0f);
                    fuseTicks = fuseTicks - TICKS_BETWEEN_TNT;
                    tntCount++;
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(this.plugin, 0, this.TICKS_BETWEEN_TNT);
    }
}
