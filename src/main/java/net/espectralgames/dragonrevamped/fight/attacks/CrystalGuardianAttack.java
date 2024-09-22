package net.espectralgames.dragonrevamped.fight.attacks;

import net.espectralgames.dragonrevamped.DragonRevamped;
import net.espectralgames.dragonrevamped.util.DelayedTask;
import net.espectralgames.dragonrevamped.util.Time;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.BubbleColumn;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class CrystalGuardianAttack extends Attack {

    DragonRevamped plugin = DragonRevamped.getPlugin();

    @Override
    public void use(List<Player> players, EnderDragon enderDragon, World world) {

        this.plugin.getLogger().info(enderDragon.getName() + " ha usado Crystal Guardian!");

        for (Player player : players) {
            player.showTitle(Title.title(MiniMessage.miniMessage().deserialize("<light_purple>CRYSTAL GUARDIAN"), Component.text("at 0 0"), Title.Times.times(Duration.ofMillis(1), Duration.ofSeconds(4), Duration.ofMillis(1))));
            player.playSound(player, Sound.ENTITY_WITHER_SPAWN, 1.0f, 1.0f);
        }

        Location spawn = Objects.requireNonNull(enderDragon.getDragonBattle()).getEndPortalLocation().add(0.5,0,0.5);
        Random random = new Random();
        spawn.setX(spawn.getX() + random.nextInt(-16, 17));
        spawn.setZ(spawn.getZ() + random.nextInt(-16, 17));
        spawn.setY(world.getHighestBlockYAt((int) spawn.getX(), (int) spawn.getZ()));

        Enderman guardian = summonGuardian(world, spawn, enderDragon);

        new DelayedTask(() -> {
            if (!guardian.isDead()) {
                AttributeInstance maxHealth = enderDragon.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if (maxHealth != null) {
                    if (enderDragon.getHealth() + (double) this.plugin.getConfig().getInt("max-dragon-health") / 2 < maxHealth.getBaseValue()) {
                        enderDragon.setHealth((double) this.plugin.getConfig().getInt("max-dragon-health") / 2);
                    } else {
                        enderDragon.setHealth(maxHealth.getBaseValue());
                    }
                }

                for (Player player : players) {
                    player.playSound(player, Sound.ENTITY_WITHER_SPAWN, 1.0f, 1.0f);
                    player.showTitle(Title.title(MiniMessage.miniMessage().deserialize("<light_purple>Dragon Recuperado!"), Component.text(""), Title.Times.times(Duration.ofMillis(1), Duration.ofSeconds(3), Duration.ofMillis(1))));
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<red>El <light_purple><b>Crystal Guardian</b><red> no murio a tiempo, el Dragon se ha recuperado media vida!"));
                }
            }
        }, Time.secondsToTicks(60));
    }

    private Enderman summonGuardian(World world, Location pos, EnderDragon enderDragon) {
        Enderman enderman = world.spawn(pos, Enderman.class);

        Server server = enderman.getServer();
        ScoreboardManager manager = server.getScoreboardManager();
        Scoreboard scoreboard = manager.getMainScoreboard();

        if (scoreboard.getTeam("guardian") != null) {
            Team team = scoreboard.getTeam("guardian");
            team.addEntity(enderman);
        } else {
            Team team = scoreboard.registerNewTeam("guardian");
            team.color(NamedTextColor.DARK_RED);
            team.addEntity(enderman);
        }
        enderman.customName(MiniMessage.miniMessage().deserialize("<dark_red><b>Crystal Guardian"));
        enderman.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, PotionEffect.INFINITE_DURATION, 4, false, false, false));
        enderman.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, PotionEffect.INFINITE_DURATION, 0, false, false, false));
        enderman.setRemoveWhenFarAway(false);
        AttributeInstance maxHealth = enderman.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealth != null) {
            maxHealth.setBaseValue((20 * enderDragon.getHealth()) / 100);
            enderman.setHealth(maxHealth.getBaseValue());
        }

        enderman.setGlowing(true);
        enderman.setCarriedBlock(Bukkit.createBlockData(Material.BEDROCK));
        enderman.setScreaming(true);
        enderman.setTarget(world.getPlayers().get(new Random().nextInt(world.getPlayerCount())));
        return enderman;
    }
}
