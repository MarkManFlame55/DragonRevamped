package net.espectralgames.dragonrevamped.fight.attacks;

import net.espectralgames.dragonrevamped.DragonRevamped;
import net.espectralgames.dragonrevamped.entity.DragonMinionEntity;
import net.espectralgames.dragonrevamped.util.DelayedTask;
import net.espectralgames.dragonrevamped.util.Time;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class
SpawnMinionsAttack extends Attack {

    DragonRevamped plugin = DragonRevamped.getPlugin();

    int MAX_MINION_SPAWN = 2;
    int MIN_MINION_SPAWN = 1;

    @Override
    public void use(List<Player> players, EnderDragon enderDragon, World world) {

        this.plugin.getLogger().info(enderDragon.getName() + " ha usado Minions!");

        for (Player player : players) {
            player.showTitle(Title.title(Component.text("MINOINS EN CAMINO"), MiniMessage.miniMessage().deserialize("<gray>en 5 segundos"), Title.Times.times(Duration.ofMillis(500), Duration.ofSeconds(2), Duration.ofMillis(500))));
        }
        new DelayedTask(() -> {
            for (Player player : players) {
                if (!player.getGameMode().isInvulnerable()) {
                    Location pos = player.getLocation();
                    Random random = new Random();
                    int high = 6;
                    int low = -6;
                    int resultX = random.nextInt(high - low) + low;
                    int resultZ = random.nextInt(high - low) + low;
                    Location minionsPos = new Location(world, pos.getX() + resultX, world.getHighestBlockYAt((int) (pos.getX() + resultX), (int) (pos.getZ() + resultZ))+1, pos.getZ() + resultZ);
                    for (int i = 0; i < random.nextInt(this.MIN_MINION_SPAWN,this.MAX_MINION_SPAWN+1); i++) {
                        DragonMinionEntity.spawn(minionsPos);
                    }
                }
            }
        }, Time.secondsToTicks(5));
    }



}
