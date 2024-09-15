package net.espectralgames.dragonrevamped.fight.attacks;

import net.espectralgames.dragonrevamped.DragonRevamped;
import net.espectralgames.dragonrevamped.util.DelayedTask;
import net.espectralgames.dragonrevamped.util.Time;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PoisonCloudAttack extends Attack {

    DragonRevamped plugin = DragonRevamped.getPlugin();

    @Override
    public void use(List<Player> players, EnderDragon enderDragon, World world) {

        this.plugin.getLogger().info(enderDragon.getName() + " ha usado Nube Venenosa");

        for (Player player : players) {
            if (!player.getGameMode().isInvulnerable()) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, Time.secondsToTicks(5), 0));
                player.showTitle(Title.title(Component.text(""), MiniMessage.miniMessage().deserialize("<gold>Corre!"), Title.Times.times(Duration.of(1, ChronoUnit.MILLIS), Duration.of(5, ChronoUnit.SECONDS), Duration.of(1, ChronoUnit.MILLIS))));
            }
        }
        new DelayedTask(() -> {
            for (Player player : players) {
                AreaEffectCloud cloud = world.spawn(player.getLocation(), AreaEffectCloud.class);
                cloud.setColor(Color.fromRGB(74, 8, 7));
                cloud.setParticle(Particle.DAMAGE_INDICATOR);
                cloud.addCustomEffect(new PotionEffect(PotionEffectType.INSTANT_DAMAGE, 1, 2), false);
                cloud.setRadius(3.0f);
                cloud.setRadiusOnUse(0);
                cloud.setDuration(Time.secondsToTicks(60));
            }
        }, Time.secondsToTicks(4));

    }
}
