package org.markmanflame55.dragonrevamped.Utils;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.markmanflame55.dragonrevamped.DragonRevamped;

public class DragonMinions {
    public static WitherSkeleton summonEndWarrior(Entity entity, Location location) {
        WitherSkeleton endWarrior = entity.getWorld().spawn(location, WitherSkeleton.class);

        ArmorTrim trim = new ArmorTrim(TrimMaterial.QUARTZ, TrimPattern.DUNE);

        ItemStack chestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
        ArmorMeta chestMeta = (ArmorMeta) chestplate.getItemMeta();
        chestMeta.setTrim(trim);
        chestplate.setItemMeta(chestMeta);

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootMeta.setColor(Color.fromRGB(0,0,0));
        boots.setItemMeta(bootMeta);

        ItemStack weapon = new ItemStack(Material.END_CRYSTAL);
        ItemMeta meta = weapon.getItemMeta();
        meta.addEnchant(Enchantment.DAMAGE_ALL, 8, true);
        meta.addEnchant(Enchantment.KNOCKBACK, 2, false);
        weapon.setItemMeta(meta);


        endWarrior.getEquipment().clear();
        endWarrior.getEquipment().setHelmet(new ItemStack(Material.END_STONE));
        endWarrior.getEquipment().setChestplate(chestplate);
        endWarrior.getEquipment().setChestplateDropChance(0f);
        endWarrior.getEquipment().setBoots(boots);
        endWarrior.getEquipment().setBootsDropChance(0f);
        endWarrior.getEquipment().setItemInMainHand(weapon);
        endWarrior.getEquipment().setItemInMainHandDropChance(0f);
        endWarrior.setCustomName("End Warrior");
        endWarrior.addScoreboardTag("end_warrior");

        return endWarrior;
    }
    public static Enderman summonCrystalGuardian(Entity spawner, Location location) {

        Enderman guardian = spawner.getWorld().spawn(location, Enderman.class);;

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getMainScoreboard();

        if (scoreboard.getTeams().contains(scoreboard.getTeam("guardian"))) {
            scoreboard.getTeam("guardian").setColor(ChatColor.DARK_RED);
            scoreboard.getTeam("guardian").addEntry(guardian.getUniqueId().toString());
        } else {
            scoreboard.registerNewTeam("guardian");
            scoreboard.getTeam("guardian").setColor(ChatColor.DARK_RED);
            scoreboard.getTeam("guardian").addEntry(guardian.getUniqueId().toString());
        }

        AttributeInstance health = guardian.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (health != null) {
            health.setBaseValue(100);
        }
        guardian.setHealth(100);
        guardian.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,2147483647,1,false ,false));
        guardian.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2147483647, 0, false, false));
        guardian.setCustomName(ChatColor.LIGHT_PURPLE + "Crystal´s Guardian");
        guardian.addScoreboardTag("crystal_guardian");
        guardian.setCustomNameVisible(true);
        guardian.setPersistent(true);

        guardian.setGlowing(true);
        return guardian;
    }
    public static void summonEndWatcher (Entity player, Location location) {

        BlockDisplay base = player.getWorld().spawn(location.clone().add(-1,0,-1), BlockDisplay.class);
        base.setBlock(Bukkit.createBlockData(Material.OBSIDIAN));
        Transformation baseTrans = base.getTransformation();
        baseTrans.getScale().set(2,0.5,2);
        base.setTransformation(baseTrans);

        BlockDisplay pillar = player.getWorld().spawn(location.clone().add(-0.5,0,-0.5), BlockDisplay.class);
        pillar.setBlock(Bukkit.createBlockData(Material.PURPUR_PILLAR));
        Transformation pillarTrans = pillar.getTransformation();
        pillarTrans.getScale().set(pillarTrans.getScale().x, 3.5, pillarTrans.getScale().z);
        pillar.setTransformation(pillarTrans);

        BlockDisplay top = player.getWorld().spawn(location.clone().add(-1,3.5,-1), BlockDisplay.class);
        top.setBlock(Bukkit.createBlockData(Material.SMOOTH_QUARTZ));
        Transformation topTrans = top.getTransformation();
        topTrans.getScale().set(2,0.5,2);
        top.setTransformation(topTrans);

        ItemDisplay eye = player.getWorld().spawn(location.clone().add(0,6, 0), ItemDisplay.class);
        eye.setCustomName("End Watcher Tower");
        eye.setItemStack(new ItemStack(Material.ENDER_EYE));
        eye.setBillboard(Display.Billboard.CENTER);
        Transformation eyeTrans = eye.getTransformation();
        eyeTrans.getScale().set(3.5);
        eye.setTransformation(eyeTrans);


        Enderman vidaTorre = player.getWorld().spawn(location.clone(), Enderman.class);

        AttributeInstance health = vidaTorre.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (health != null) {
            health.setBaseValue(120);
        }
        vidaTorre.setSilent(true);
        vidaTorre.addScoreboardTag("end_watcher");
        vidaTorre.setAI(false);
        vidaTorre.setRemoveWhenFarAway(false);
        vidaTorre.setHealth(120);
        vidaTorre.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2147483647, 0, false, false, false));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!eye.isDead()) {
                    for (Entity players : eye.getNearbyEntities(10,10,10)) {
                        if (players instanceof Player p) {
                            shootLaserFromEntityToPlayer(eye, p, Color.fromRGB(255,0,0));
                            p.damage(1, eye);
                        }
                    }
                    for (Entity dragon : eye.getNearbyEntities(25, 25, 25)) {
                        if (dragon instanceof EnderDragon) {
                            shootLaserFromEntityToEntity(eye, dragon, Color.fromRGB(0,255,0));
                            ((EnderDragon) dragon).addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 4, false, false, false));
                        }
                    }
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(DragonRevamped.getPlugin(DragonRevamped.class), 0, 1);
    }
    private static void shootLaserFromEntityToPlayer(Entity shooter, Player targetPlayer, Color color) {

        if (targetPlayer.getGameMode().equals(GameMode.SURVIVAL) || targetPlayer.getGameMode().equals(GameMode.ADVENTURE)) {
            Location shooterLocation = shooter.getLocation();
            Location targetLocation = targetPlayer.getEyeLocation().add(0, -0.75, 0);

            // Obtener la dirección del rayo de partículas.
            Vector laserDirection = targetLocation.toVector().subtract(shooterLocation.toVector()).normalize();
            // Calcular la distancia entre la entidad y el jugador.
            double distance = shooterLocation.distance(targetLocation);
            // Calcular la cantidad de partículas en función de la distancia (cuanto más lejos, más partículas).
            int particleCount = (int) Math.ceil(distance * 10); // Puedes ajustar el factor multiplicador según la densidad de partículas que desees.
            // Calcular el paso entre cada partícula.
            double stepSize = distance / particleCount;

            // Crear todas las partículas del rayo de una vez.
            Location particleLocation = shooterLocation.clone();
            Particle.DustOptions dust = new Particle.DustOptions(color, 1);
            for (int i = 0; i < particleCount; i++) {
                particleLocation.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, 1, dust);
                particleLocation.add(laserDirection.clone().multiply(stepSize));
            }
        }
    }
    private static void shootLaserFromEntityToEntity(Entity shooter, Entity targetEntity, Color color) {
            Location shooterLocation = shooter.getLocation();
            Location targetLocation = targetEntity.getLocation().add(0, -0.75, 0);

            // Obtener la dirección del rayo de partículas.
            Vector laserDirection = targetLocation.toVector().subtract(shooterLocation.toVector()).normalize();
            // Calcular la distancia entre la entidad y el jugador.
            double distance = shooterLocation.distance(targetLocation);
            // Calcular la cantidad de partículas en función de la distancia (cuanto más lejos, más partículas).
            int particleCount = (int) Math.ceil(distance * 3); // Puedes ajustar el factor multiplicador según la densidad de partículas que desees.
            // Calcular el paso entre cada partícula.
            double stepSize = distance / particleCount;

            // Crear todas las partículas del rayo de una vez.
            Location particleLocation = shooterLocation.clone();
            Particle.DustOptions dust = new Particle.DustOptions(color, 1);
            for (int i = 0; i < particleCount; i++) {
                particleLocation.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, 1, dust);
                particleLocation.add(laserDirection.clone().multiply(stepSize));
            }
    }
}
