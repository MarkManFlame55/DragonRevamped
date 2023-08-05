package org.markmanflame55.dragonrevamped.EventListeners;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Biome;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.markmanflame55.dragonrevamped.DragonRevamped;
import org.markmanflame55.dragonrevamped.Utils.DelayedTask;
import org.markmanflame55.dragonrevamped.Utils.DragonMinions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DragonBossFight implements Listener {

    public static Boolean areTowersAlive;

    private static Boolean STAGE1;
    private static Boolean STAGE2;
    private static Boolean STAGE3;


    private List<Location> crystalPositions = new ArrayList<>();

    @EventHandler
    public void onDragonSpawn(EntitySpawnEvent e) {
        if (e.getEntity() instanceof EnderDragon dragon) {
            if (e.getEntity().getWorld().getBiome(e.getEntity().getLocation()).equals(Biome.THE_END)) {
                dragon.getDragonBattle().getBossBar().setTitle(ChatColor.MAGIC + "" + ChatColor.DARK_PURPLE + "AAA" +
                        ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Ender Dragon" +
                        ChatColor.MAGIC + "" + ChatColor.DARK_PURPLE + "AAA");
                AttributeInstance health = dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if (health != null) {
                    health.setBaseValue(1000);
                }
                startBossFight(dragon);
            }
        }
    }


    private boolean isDragonAlive(Entity player) {
        List<LivingEntity> livingEntities = player.getWorld().getLivingEntities();
        for (LivingEntity entity : livingEntities) {
            if (entity instanceof EnderDragon) {
                return true;
            }
        }
        return false;
    }

    private void getStages(EnderDragon enderDragon) {
        if (enderDragon.getHealth() > 650) {
            STAGE1 = true;
            STAGE2 = false;
            STAGE3 = false;
        } else if (enderDragon.getHealth() <= 650) {
            if (enderDragon.getHealth() <= 350) {
                STAGE1 = false;
                STAGE2 = false;
                STAGE3 = true;
            } else {
                STAGE1 = false;
                STAGE2 = true;
                STAGE3 = false;
            }
        }
    }

    private void changeFightVisuals(EnderDragon enderDragon) {
        new BukkitRunnable() {
            @Override
            public void run() {
                getStages(enderDragon);
                if (STAGE1) {
                    enderDragon.getBossBar().setTitle(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "AAA" +
                            ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + " Ender Dragon " +
                            ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "AAA");
                    enderDragon.setCustomName(enderDragon.getBossBar().getTitle());
                    enderDragon.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5*20, 0, false, false));
                }
                if (STAGE2) {
                    enderDragon.getBossBar().setTitle(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "AAA" +
                            ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + " ENRAGED ENDER DRAGON " +
                            ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "AAA");
                    AttributeInstance flySpeed = enderDragon.getAttribute(Attribute.GENERIC_FLYING_SPEED);
                    if (flySpeed != null) {
                        flySpeed.setBaseValue(flySpeed.getBaseValue() * 2);
                    }
                    AttributeInstance damage = enderDragon.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
                    if (damage != null) {
                        damage.setBaseValue(damage.getBaseValue() * 1.5);
                    }
                    enderDragon.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5 * 20, 2, false, false));
                    enderDragon.setCustomName(enderDragon.getBossBar().getTitle());
                }
                if (STAGE3) {
                    enderDragon.getBossBar().setTitle(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "AAA" +
                            ChatColor.DARK_RED + "" + ChatColor.BOLD + " FINAL ENDER DRAGON " +
                            ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "AAA");
                    AttributeInstance flySpeed = enderDragon.getAttribute(Attribute.GENERIC_FLYING_SPEED);
                    if (flySpeed != null) {
                        flySpeed.setBaseValue(flySpeed.getBaseValue() * 3);
                    }
                    AttributeInstance damage = enderDragon.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
                    if (damage != null) {
                        damage.setBaseValue(damage.getBaseValue() * 3);
                    }
                    enderDragon.setGlowing(true);
                    enderDragon.setCustomName(enderDragon.getBossBar().getTitle());
                }
            }
        }.runTaskTimer(DragonRevamped.getPlugin(DragonRevamped.class), 0, 1);
    }

    private void spawnMinionsAttack(Player player) {
        System.out.println("[" + DragonRevamped.pluginName + "]: Dragon casted Minions Rise");
        if (player.getGameMode().equals(GameMode.SURVIVAL)) {
            player.playSound(player, Sound.ENTITY_ENDER_DRAGON_AMBIENT, 1.0f, 1.0f);
            player.sendTitle(ChatColor.WHITE + "MINIONS INCOMING", ChatColor.GRAY + "in 10 seconds", 1, 100, 1);

            new DelayedTask(() -> {
                Location location = player.getLocation();
                Random random = new Random();
                int high = 6;
                int low = -6;
                int result1 = random.nextInt(high - low) + low;
                int result2 = random.nextInt(high - low) + low;
                Location minionSpawn = new Location(player.getWorld(), location.getX() + result1, location.getY(), location.getZ() + result2);
                for (int i = 0; i <= 2; i++) {
                    DragonMinions.summonEndWarrior(player, minionSpawn);
                }
            }, 200);
        }
    }

    private void lightningAttack(Player player) {
        System.out.println("[" + DragonRevamped.pluginName + "]: Dragon casted Lightning");
        if (player.getGameMode().equals(GameMode.SURVIVAL)) {
            player.playSound(player, Sound.ENTITY_ENDER_DRAGON_AMBIENT, 1.0f, 1.0f);
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 5 * 20, 0, false, false));
            new DelayedTask(() -> {
                Location location = player.getLocation();
                if (!location.getBlock().isLiquid()) {
                    player.getWorld().strikeLightning(location);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 20, 1));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 300 * 20, 0));
                }
            }, 5 * 20);
        }
    }

    private void launchAttack(Player player) {
        System.out.println("[" + DragonRevamped.pluginName + "]: Dragon casted Launch Players");
        if (player.getGameMode().equals(GameMode.SURVIVAL)) {
            Location location = player.getLocation();
            player.playSound(player, Sound.ENTITY_ENDER_DRAGON_AMBIENT, 1.0f, 1.0f);
            player.sendTitle("", ChatColor.GOLD + "To the Sky!", 1, 5 * 4, 1);
            Vector launchDirection = location.toVector().add(location.toVector().multiply(-1));
            launchDirection.setY(3.0);
            player.setVelocity(launchDirection);
        }
    }

    private void poisonCloudAttack(Player player) {
        System.out.println("[" + DragonRevamped.pluginName + "]: Dragon casted Damage cloud");
        if (player.getGameMode().equals(GameMode.SURVIVAL)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 20 * 8, 1));
            player.sendTitle("", ChatColor.GOLD + "RUN", 1, 60, 1);
            new DelayedTask(() -> {
                Location location = player.getLocation();
                new DelayedTask(() -> {
                    AreaEffectCloud cloud = player.getWorld().spawn(location.add(0, -0.2, 0), AreaEffectCloud.class);
                    cloud.setColor(Color.fromRGB(74, 8, 7));
                    cloud.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 1, 2), false);
                    cloud.setParticle(Particle.DAMAGE_INDICATOR);
                    cloud.setRadius(3.0f);
                    cloud.setRadiusOnUse(-3.0f);
                }, 10);
            }, 6 * 20);
        }
    }
    private static void summonSpike(Player player, EnderDragon enderDragon) {
        BlockDisplay blockDisplay = player.getWorld().spawn(player.getLocation().add(-0.25,0,-0.25), BlockDisplay.class);
        ItemDisplay itemDisplay = player.getWorld().spawn(player.getLocation().add(0,0.25,0), ItemDisplay.class);

        itemDisplay.setItemStack(new ItemStack(Material.OBSIDIAN));

        itemDisplay.getWorld().playSound(itemDisplay.getLocation(), Sound.BLOCK_STONE_PLACE, 2.0f, 1.0f);

        Transformation item = itemDisplay.getTransformation();
        item.getScale().set(item.getScale().x, 0.5, item.getScale().z);
        itemDisplay.setTransformation(item);

        new DelayedTask(() -> {
            blockDisplay.setBlock(Bukkit.createBlockData(Material.CHORUS_PLANT));
            Transformation transformation = blockDisplay.getTransformation();
            transformation.getScale().set(0.5,0.1, 0.5);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (transformation.getScale().y < 3) {
                        transformation.getScale().set(0.5, transformation.getScale().y+1, 0.5);
                        blockDisplay.setTransformation(transformation);
                    } else {
                        player.getWorld().playSound(blockDisplay.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1.0f, 1.0f);
                        this.cancel();
                    }
                }
            }.runTaskTimer(DragonRevamped.getPlugin(DragonRevamped.class), 0, 1);

            for (Entity entity : blockDisplay.getNearbyEntities(0.5, 3, 0.5)) {
                if (entity instanceof LivingEntity) {
                    ((LivingEntity) entity).damage(9, enderDragon);
                    Vector launchDirection = entity.getLocation().toVector().add(entity.getLocation().toVector().multiply(-1));
                    launchDirection.setY(0.8);
                    entity.setVelocity(launchDirection);
                }
            }
            new DelayedTask(() -> {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (transformation.getScale().y > 0) {
                            transformation.getScale().set(0.5, transformation.getScale().y-1, 0.5);
                            blockDisplay.setTransformation(transformation);
                        } else {
                            blockDisplay.remove();
                            itemDisplay.remove();
                            this.cancel();
                        }
                    }
                }.runTaskTimer(DragonRevamped.getPlugin(DragonRevamped.class), 0, 1);
            }, 85);
        }, 3*20);
    }
    public static void spikeAttack(Player player, EnderDragon enderDragon) {
        System.out.println("[" + DragonRevamped.pluginName + "]: Dragon casted Spiked Floor");
        if (player.getGameMode().equals(GameMode.SURVIVAL)) {
           summonSpike(player, enderDragon);
           new DelayedTask(() -> {
               summonSpike(player, enderDragon);
               new DelayedTask(() -> {
                   summonSpike(player, enderDragon);
                   new DelayedTask(() -> {
                       summonSpike(player, enderDragon);
                       new DelayedTask(() -> {
                           summonSpike(player, enderDragon);
                       },10);
                   },10);
               },10);
           },10);
        }
    }
    private void summonCrystalGuardian(EnderDragon enderDragon) {
        System.out.println("[" + DragonRevamped.pluginName + "]: Dragon casted Summon Crystal Guardian");

        for (Player player : enderDragon.getWorld().getPlayers()) {
            player.sendTitle(ChatColor.LIGHT_PURPLE + "CRYSTAL GUARDIAN", ChatColor.WHITE + "at 0,0", 1, 20*4, 1);
            player.playSound(player, Sound.ENTITY_WITHER_SPAWN, 1.0f, 1.0f);
        }

        Location spawn = enderDragon.getDragonBattle().getEndPortalLocation();
        spawn.setX(spawn.getX()+0.5);
        spawn.setY(spawn.getY()+5);
        spawn.setZ(spawn.getZ()+0.5);
        Enderman guardian = DragonMinions.summonCrystalGuardian(enderDragon, spawn);
        new DelayedTask(() -> {
            if (!guardian.isDead()) {
                for (Player player : enderDragon.getWorld().getPlayers()) {
                    player.sendTitle(ChatColor.LIGHT_PURPLE + "End Crystals Restored", ChatColor.WHITE + "Crystal Guardian wasn´t killed in time");
                }
                for (Location crystalspawn : crystalPositions) {
                    enderDragon.getWorld().spawnEntity(crystalspawn, EntityType.ENDER_CRYSTAL, false);
                }
            }
        }, 60*20);

    }
    private void tntRain(EnderDragon enderDragon) {
        System.out.println("[" + DragonRevamped.pluginName + "]: Dragon casted TNT Rain");
        new DelayedTask(() -> {
            Location location = enderDragon.getLocation();
            TNTPrimed tnt1 = enderDragon.getWorld().spawn(location, TNTPrimed.class);
            tnt1.setFuseTicks(200);
            tnt1.setYield(5.0f);
            tnt1.setSource(enderDragon);
            for (Player player : enderDragon.getWorld().getPlayers()) {
                player.playSound(enderDragon, Sound.ENTITY_ENDER_DRAGON_SHOOT, 8.0f, 1.0f);
            }
            new DelayedTask(() -> {
                Location location1 = enderDragon.getLocation();
                TNTPrimed tnt2 = enderDragon.getWorld().spawn(location1, TNTPrimed.class);
                tnt2.setFuseTicks(190);
                tnt2.setYield(5.0f);
                tnt2.setSource(enderDragon);
                for (Player player : enderDragon.getWorld().getPlayers()) {
                    player.playSound(enderDragon, Sound.ENTITY_ENDER_DRAGON_SHOOT, 8.0f, 1.0f);
                }
                new DelayedTask(() -> {
                    Location location2 = enderDragon.getLocation();
                    TNTPrimed tnt3 = enderDragon.getWorld().spawn(location2, TNTPrimed.class);
                    tnt3.setFuseTicks(180);
                    tnt3.setYield(5.0f);
                    tnt3.setSource(enderDragon);
                    for (Player player : enderDragon.getWorld().getPlayers()) {
                        player.playSound(enderDragon, Sound.ENTITY_ENDER_DRAGON_SHOOT, 8.0f, 1.0f);
                    }
                    new DelayedTask(() -> {
                        Location location3 = enderDragon.getLocation();
                        TNTPrimed tnt4 = enderDragon.getWorld().spawn(location3, TNTPrimed.class);
                        tnt4.setFuseTicks(170);
                        tnt4.setYield(5.0f);
                        tnt4.setSource(enderDragon);
                        for (Player player : enderDragon.getWorld().getPlayers()) {
                            player.playSound(enderDragon, Sound.ENTITY_ENDER_DRAGON_SHOOT, 8.0f, 1.0f);
                        }
                        new DelayedTask(() -> {
                            Location location4 = enderDragon.getLocation();
                            TNTPrimed tnt5 = enderDragon.getWorld().spawn(location4, TNTPrimed.class);
                            tnt5.setFuseTicks(160);
                            tnt5.setYield(5.0f);
                            tnt5.setSource(enderDragon);
                            for (Player player : enderDragon.getWorld().getPlayers()) {
                                player.playSound(enderDragon, Sound.ENTITY_ENDER_DRAGON_SHOOT, 8.0f, 1.0f);
                            }
                        }, 10);
                    }, 10);
                }, 10);
            }, 10);
        }, 10);
    }
    private void customAttacks(EnderDragon enderDragon) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!enderDragon.isDead()) {
                    getStages(enderDragon);
                    if (STAGE1) {
                        Random random = new Random();
                        float chance = random.nextFloat();
                        float chanceAttack = random.nextFloat();
                        if (chance <= 0.5f) {
                            if (chanceAttack <= 0.25f) {
                                for (Player player : enderDragon.getWorld().getPlayers()) {
                                    spawnMinionsAttack(player);
                                }
                            } else if (chanceAttack <= 0.50f) {
                                for (Player player : enderDragon.getWorld().getPlayers()) {
                                    lightningAttack(player);
                                }
                            } else if (chanceAttack <= 0.75f) {
                                for (Player player : enderDragon.getWorld().getPlayers()) {
                                    spikeAttack(player, enderDragon);
                                }
                            } else {
                                tntRain(enderDragon);
                            }
                        } else {
                            System.out.println("[" + DragonRevamped.pluginName + "]: Dragon missed attack");
                        }
                    }
                    if (STAGE2) {
                        Random random = new Random();
                        float chance = random.nextFloat();
                        float chanceAttack = random.nextFloat();
                        if (chance <= 0.85f) {
                            if (chanceAttack <= 0.165f) {
                                for (Player player : enderDragon.getWorld().getPlayers()) {
                                    spawnMinionsAttack(player);
                                }
                            } else if (chanceAttack <= 0.33f) {
                                for (Player player : enderDragon.getWorld().getPlayers()) {
                                    launchAttack(player);
                                }
                            } else if (chanceAttack <= 0.495f) {
                                for (Player player : enderDragon.getWorld().getPlayers()) {
                                    lightningAttack(player);
                                }
                            } else if (chanceAttack <= 0.66f) {
                                for (Player player : enderDragon.getWorld().getPlayers()) {
                                    poisonCloudAttack(player);
                                }
                            } else if (chanceAttack <= 0.825f){
                                tntRain(enderDragon);
                            } else if (chanceAttack <= 0.99f) {
                                for (Player player : enderDragon.getWorld().getPlayers()) {
                                    spikeAttack(player, enderDragon);
                                }
                            }
                            else {
                                summonCrystalGuardian(enderDragon);
                            }
                        } else {
                            System.out.println("[" + DragonRevamped.pluginName + "]: Dragon missed attack");
                        }
                    }
                    if (STAGE3) {
                        Random random = new Random();
                        float chance = random.nextFloat();
                        if (chance <= 0.1583f) {
                            for (Player player : enderDragon.getWorld().getPlayers()) {
                                spawnMinionsAttack(player);
                            }
                        } else if (chance <= 0.3166f) {
                            for (Player player : enderDragon.getWorld().getPlayers()) {
                                launchAttack(player);
                            }
                        } else if (chance <= 0.475f) {
                            for (Player player : enderDragon.getWorld().getPlayers()) {
                                lightningAttack(player);
                            }
                        } else if (chance <= 0.633f) {
                            for (Player player : enderDragon.getWorld().getPlayers()) {
                                poisonCloudAttack(player);
                            }
                        } else if (chance <= 0.792f) {
                            tntRain(enderDragon);
                        } else if (chance <= 0.95f) {
                            for (Player player : enderDragon.getWorld().getPlayers()) {
                                spikeAttack(player, enderDragon);
                            }
                        }
                        else {
                            summonCrystalGuardian(enderDragon);
                        }
                    }
                }
            }
        }.runTaskTimer(DragonRevamped.getPlugin(DragonRevamped.class), 200, 30 * 20);
    }
    private void summonTowers(EnderDragon enderDragon) {
        if (enderDragon.getDragonBattle().getEndPortalLocation() != null) {
            //PRIMERA TORRE
            double Y0 = getHighestY(enderDragon.getWorld(), (int) enderDragon.getDragonBattle().getEndPortalLocation().getX()+10, (int) enderDragon.getDragonBattle().getEndPortalLocation().getZ());
            Location spawn0 = enderDragon.getDragonBattle().getEndPortalLocation();
            spawn0.setY(Y0);
            DragonMinions.summonEndWatcher(enderDragon, spawn0.add(10.5,0,0.5));
            //SEGUNDA TORRE

            double Y1 = getHighestY(enderDragon.getWorld(), (int) enderDragon.getDragonBattle().getEndPortalLocation().getX(), (int) enderDragon.getDragonBattle().getEndPortalLocation().getZ()-10);
            Location spawn1 = enderDragon.getDragonBattle().getEndPortalLocation();
            spawn1.setY(Y1);
            DragonMinions.summonEndWatcher(enderDragon, spawn1.add(0.5,0,-9.5));
            //TERCERA TORRE

            double Y2 = getHighestY(enderDragon.getWorld(), (int) enderDragon.getDragonBattle().getEndPortalLocation().getX(), (int) enderDragon.getDragonBattle().getEndPortalLocation().getZ()+10);
            Location spawn2 = enderDragon.getDragonBattle().getEndPortalLocation();
            spawn2.setY(Y2);
            DragonMinions.summonEndWatcher(enderDragon, spawn2.add(0.5,0,10.5));
            //CUARTA TORRE

            double Y3 = getHighestY(enderDragon.getWorld(), (int) enderDragon.getDragonBattle().getEndPortalLocation().getX()-10, (int) enderDragon.getDragonBattle().getEndPortalLocation().getZ());
            Location spawn3 = enderDragon.getDragonBattle().getEndPortalLocation();
            spawn3.setY(Y3);
            DragonMinions.summonEndWatcher(enderDragon, spawn3.add(-9.5,0,0.5));
        }
    }

    public void startTowersTimer(EnderDragon enderDragon) {
        areTowersAlive = false;
        new BukkitRunnable() {
            @Override
            public void run() {
                getStages(enderDragon);
                if (!areTowersAlive) {
                    if (STAGE2) {
                        Random random = new Random();
                        float chance = random.nextFloat();
                        if (chance <= 0.25f) {
                            if (enderDragon.getDragonBattle().getEndPortalLocation() != null) {
                                summonTowers(enderDragon);
                                areTowersAlive = true;
                            }
                        }
                    }
                    if (STAGE3) {
                        Random random = new Random();
                        float chance = random.nextFloat();
                        if (chance <= 0.45f) {
                            if (enderDragon.getDragonBattle().getEndPortalLocation() != null) {
                                summonTowers(enderDragon);
                                areTowersAlive = true;
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(DragonRevamped.getPlugin(DragonRevamped.class), 0,60*20);
    }

    public int getHighestY (World world, int x, int z) {
        int y = 255;
        while (world.getBlockAt(x,y,z).getType() == Material.AIR) {
            y--;
        }
        return y+1;
    }

    public void startBossFight(EnderDragon enderDragon) {
        System.out.println("[" + DragonRevamped.pluginName + "]: Started Bossfight");
        enderDragon.setHealth(1000);
        DragonRevamped.onFight = true;
        changeFightVisuals(enderDragon);
        customAttacks(enderDragon);
        startTowersTimer(enderDragon);
    }

    @EventHandler
    public void onDragonDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof EnderDragon) {
            if (e.getEntity().getKiller() != null) {
                Player player = e.getEntity().getKiller();
                for (Player p : e.getEntity().getServer().getOnlinePlayers()) {
                    p.sendMessage(ChatColor.YELLOW + player.getDisplayName() + " has killed " + e.getEntity().getCustomName());
                }
            }
            DragonRevamped.onFight = false;
        }
    }
    @EventHandler
    public void onDragonHit(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof EnderDragon) {
            if (e.getDamager() instanceof EnderDragon) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        if (e.getEntity().getLocation().getBlock().getBiome().equals(Biome.THE_END)) {
            if (e.getEntity() instanceof EnderCrystal && DragonRevamped.onFight) {
                if (((EnderCrystal) e.getEntity()).isShowingBottom()) {
                    crystalPositions.add(e.getLocation());
                    Random random = new Random();
                    float chance = random.nextFloat();
                    if (chance <= 0.75f) {
                        Ghast ghast = e.getEntity().getWorld().spawn(e.getLocation(), Ghast.class);

                        ghast.addScoreboardTag("battle_ghast");
                        ghast.setPersistent(true);
                        AttributeInstance health = ghast.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                        if (health != null) {
                            health.setBaseValue(75);
                            ghast.setHealth(50);
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onGhastShoot(ProjectileLaunchEvent e){
        if (e.getEntity() instanceof Fireball) {
            if (e.getEntity().getShooter() instanceof Ghast && ((Ghast) e.getEntity().getShooter()).getScoreboardTags().contains("battle_ghast")) {
                ((Fireball) e.getEntity()).setYield(2f);
            }
        }
    }
    @EventHandler
    public void onCustomMobsDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof WitherSkeleton && e.getEntity().getScoreboardTags().contains("end_warrior")) {
            e.getDrops().clear();
            Random random = new Random();
            int high = 10;
            int low = 5;
            int result = random.nextInt(high - low) + low;
            e.getDrops().add(new ItemStack(Material.END_STONE, result));
        }
        if (e.getEntity() instanceof Ghast && e.getEntity().getScoreboardTags().contains("battle_ghast")) {
            e.getDrops().clear();
            Random random = new Random();
            float chance = random.nextFloat();
            if (chance <= 0.25f) {
            }
        }
    }
}
