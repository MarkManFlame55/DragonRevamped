package net.espectralgames.dragonrevamped.fight.attacks;

import net.espectralgames.dragonrevamped.DragonRevamped;
import net.espectralgames.dragonrevamped.util.DelayedTask;
import net.espectralgames.dragonrevamped.util.Time;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class SpikeAttack extends Attack {

    DragonRevamped plugin = DragonRevamped.getPlugin();

    @Override
    public void use(List<Player> players, EnderDragon enderDragon, World world) {

        this.plugin.getLogger().info(enderDragon.getName() + " ha usado Pinchos!");
        final CompletableFuture<List<Location>> plateTask = new CompletableFuture<>();

        new BukkitRunnable() {

            final List<Location> plateLocations = new ArrayList<>();
            int plates = 0;
            @Override
            public void run() {
                if (plates < 5) {
                    for (Player player : players) {
                        Location pos = player.getLocation();
                        pos.setYaw(0f);
                        pos.setPitch(0f);
                        ItemDisplay itemDisplay = player.getWorld().spawn(pos.add(0, 0.25, 0), ItemDisplay.class);
                        itemDisplay.setItemStack(new ItemStack(Material.OBSIDIAN));
                        itemDisplay.getWorld().playSound(itemDisplay.getLocation(), Sound.BLOCK_STONE_PLACE, 2.0f, 1.0f);
                        Transformation item = itemDisplay.getTransformation();
                        item.getScale().set(item.getScale().x, 0.5, item.getScale().z);
                        itemDisplay.setTransformation(item);
                        plateLocations.add(itemDisplay.getLocation());
                    }
                    plates++;
                } else {
                    plateTask.complete(plateLocations);
                }

            }
        }.runTaskTimer(this.plugin, 0, Time.secondsToTicks(1));
        
        plateTask.whenComplete((plateLocations, throwable) -> {

            if (throwable != null) {
                System.out.println(":P");
            }

            for (Location pos1 : plateLocations) {
                BlockDisplay blockDisplay = world.spawn(pos1.clone().add(-0.25, 0, -0.25), BlockDisplay.class);
                blockDisplay.setBlock(Bukkit.createBlockData(Material.CHORUS_PLANT));
                Transformation transformation = blockDisplay.getTransformation();
                transformation.getScale().set(0.5, 3, 0.5);
                blockDisplay.setTransformation(transformation);
                world.playSound(blockDisplay.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.2f, 1.0f);
                for (Entity entity : blockDisplay.getNearbyEntities(0.5, 3, 0.5)) {
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.damage(30 , enderDragon);
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, Time.secondsToTicks(10), 1, false, false, false));
                        Vector launchDirection = livingEntity.getLocation().toVector().add(livingEntity.getLocation().toVector().multiply(-1));
                        launchDirection.setY(0.8);
                        livingEntity.setVelocity(launchDirection);
                    }
                }
            }
        });

    }
}