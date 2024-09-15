package net.espectralgames.dragonrevamped.fight.attacks;

import net.espectralgames.dragonrevamped.DragonRevamped;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class LauchPlayersAttack extends Attack {

    DragonRevamped plugin = DragonRevamped.getPlugin();

    @Override
    public void use(List<Player> players, EnderDragon enderDragon, World world) {

        this.plugin.getLogger().info(enderDragon.getName() + " ha usado Lanzar Jugadores!");

        for (Player player : players) {
            player.playSound(player, Sound.ENTITY_ENDER_DRAGON_AMBIENT, 1.0f, 1.0f);
            player.showTitle(Title.title(Component.text(""), MiniMessage.miniMessage().deserialize("<gold>Al Cielo!"), Title.Times.times(Duration.of(1, ChronoUnit.MILLIS), Duration.of(5, ChronoUnit.SECONDS), Duration.of(1, ChronoUnit.MILLIS))));
            player.setVelocity(new Vector(0, 3, 0));
        }
    }
}
