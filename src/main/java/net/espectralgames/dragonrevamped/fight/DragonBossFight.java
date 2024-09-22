package net.espectralgames.dragonrevamped.fight;

import net.espectralgames.dragonrevamped.DragonRevamped;
import net.espectralgames.dragonrevamped.entity.EnderBlazeEntity;
import net.espectralgames.dragonrevamped.fight.attacks.Attack;
import net.espectralgames.dragonrevamped.fight.attacks.Attacks;
import net.espectralgames.dragonrevamped.fight.attacks.HoverAttack;
import net.espectralgames.dragonrevamped.items.PluginItems;
import net.espectralgames.dragonrevamped.util.ServerMessage;
import net.espectralgames.dragonrevamped.util.Time;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Biome;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class DragonBossFight implements Listener {

    DragonRevamped plugin = DragonRevamped.getPlugin();

    Map<Attack, Integer> stage1AttackWeightMap = new HashMap<>();
    Map<Attack, Integer> stage2AttacksWeigthMap = new HashMap<>();
    Map<Attack, Integer> stage3AttacksWeigthMap = new HashMap<>();
    boolean isFighting;
    double DRAGON_MAX_HEALTH = this.plugin.getConfig().getDouble("max-dragon-health");
    double SPIGOT_MAX_HEALTH = this.plugin.getServer().spigot().getSpigotConfig().getDouble("settings.attribute.maxHealth.max");
    int ATTACK_TIMER = this.plugin.getConfig().getInt("attack-timer");

    int STAGE = 1;

    public DragonBossFight() {
        stage1AttackWeightMap.put(Attacks.SPIKES, 1);
        stage1AttackWeightMap.put(Attacks.THUNDER, 1);
        stage1AttackWeightMap.put(Attacks.SPAWN_MINIONS, 2);

        stage2AttacksWeigthMap.put(Attacks.SPIKES, 5);
        stage2AttacksWeigthMap.put(Attacks.THUNDER, 7);
        stage2AttacksWeigthMap.put(Attacks.SPAWN_MINIONS, 7);
        stage2AttacksWeigthMap.put(Attacks.LAUNCH_PLAYERS, 7);
        stage2AttacksWeigthMap.put(Attacks.POISON_CLOUD, 5);
        stage2AttacksWeigthMap.put(Attacks.TNT_RAIN, 8);
        stage2AttacksWeigthMap.put(Attacks.CRYSTAL_GUARDIAN, 1);

        stage3AttacksWeigthMap.put(Attacks.SPIKES, 7);
        stage3AttacksWeigthMap.put(Attacks.THUNDER, 8);
        stage3AttacksWeigthMap.put(Attacks.SPAWN_MINIONS, 7);
        stage3AttacksWeigthMap.put(Attacks.LAUNCH_PLAYERS, 7);
        stage3AttacksWeigthMap.put(Attacks.POISON_CLOUD, 9);
        stage3AttacksWeigthMap.put(Attacks.TNT_RAIN, 8);
        stage3AttacksWeigthMap.put(Attacks.CRYSTAL_GUARDIAN, 3);
    }

    @EventHandler
    public void onDragonSpawn(EntitySpawnEvent e) {
        if (e.getEntity() instanceof EnderDragon enderDragon) {
            if (enderDragon.getWorld().getEnvironment().equals(World.Environment.THE_END)) {
                if (this.plugin.isCustomFight()) {
                    DragonBattle dragonBattle = enderDragon.getDragonBattle();
                    assert dragonBattle != null;
                    startBossfigth(enderDragon);
                }
            }
        }
    }

    // DISPARAR AL DRAGON PARA EVITAR QUE LLEGUE ALL CENTRO Y HACER EL HOVER ATTACK

    @EventHandler
    public void onDragonHit(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof EnderDragon enderDragon) {
            if (this.isFighting && this.plugin.isCustomFight()) {
                if (enderDragon.getPhase().equals(EnderDragon.Phase.LAND_ON_PORTAL)) {
                    Random random = new Random();
                    if (this.STAGE == 1) {
                        if (random.nextFloat() <= 0.75f) {
                            enderDragon.setPhase(EnderDragon.Phase.LEAVE_PORTAL);
                        }
                    }
                    if (this.STAGE == 2) {
                        if (random.nextFloat() <= 0.5f) {
                            enderDragon.setPhase(EnderDragon.Phase.LEAVE_PORTAL);
                        }
                    }
                    if (this.STAGE == 3) {
                        if (random.nextFloat() <= 0.25f) {
                            enderDragon.setPhase(EnderDragon.Phase.LEAVE_PORTAL);
                        }
                    }
                }
            }
        }
    }

    // ATAQUE CUANDO ESTA EN EL PORTAL

    @EventHandler
    public void onDragonPositiones(EnderDragonChangePhaseEvent e) {
        if (this.isFighting && this.plugin.isCustomFight()) {
            if (e.getNewPhase().equals(EnderDragon.Phase.BREATH_ATTACK)) {
                EnderDragon enderDragon = e.getEntity();
                World world = enderDragon.getWorld();
                Attacks.HOVER_ATTACK.use(world.getPlayers(), enderDragon, world);
            }
        }
    }

    // CUANDO MUERE EL DRAGON

    @EventHandler
    public void onDragonDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof EnderDragon enderDragon) {
            if (this.isFighting && this.plugin.isCustomFight()) {
                Player player = enderDragon.getKiller();
                ServerMessage.broadcast("<dark_red><b>" + player.getName() + "</b><yellow> ha matado al " + this.plugin.getConfig().getString("dragon-name-stage-3"));
                player.getInventory().addItem(PluginItems.DRAGON_PRIZE);
                this.isFighting = false;
            }
        }
    }

    // SPAWNEO DE ENDER BLAZES (1 de 4 endermans)

    @EventHandler
    public void onEndermanSpawn(CreatureSpawnEvent e) {
        if (e.getEntity() instanceof Enderman enderman && this.isFighting) {
            World world = enderman.getWorld();
            if (world.getBiome(enderman.getLocation()).equals(Biome.THE_END) && this.plugin.isCustomFight()) {
                Random random = new Random();
                if (random.nextFloat() <= 0.25f) {
                    e.setCancelled(true);
                    Location pos = enderman.getLocation();
                    EnderBlazeEntity.spawn(pos);
                }
            }
        }
    }


    // DROPS DE LOS MOBS CUSTOM EN LA BATALLA
    @EventHandler
    public void onMinionsDeath(EntityDeathEvent e) {
        if (e.getEntity().getWorld().getBiome(e.getEntity().getLocation()).equals(Biome.THE_END)) {
            if (e.getEntity() instanceof Blaze) {
                e.getDrops().clear();
                e.getDrops().add(new ItemStack(Material.END_ROD));
            }
            if (e.getEntity() instanceof WitherSkeleton) {
                e.getDrops().clear();
                e.getDrops().add(new ItemStack(Material.END_STONE, new Random().nextInt(1, 9)));
            }
        }
    }


    // CAMBIAR EL ATAQUE DE LA BOLA DE DRAGON

    @EventHandler
    public void onBallHit(ProjectileHitEvent e) {
        if (e.getEntity() instanceof DragonFireball fireball) {
            if (this.isFighting && this.plugin.isCustomFight()) {
                World world = fireball.getWorld();
                Location pos = fireball.getLocation();
                world.createExplosion(pos, 3.0f);
            }
        }
    }


    private void startBossfigth(EnderDragon enderDragon) {
        this.isFighting = true;
        World world = enderDragon.getWorld();
        AttributeInstance maxHealth = enderDragon.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        this.STAGE = 1;
        if (maxHealth != null) {
            if (this.DRAGON_MAX_HEALTH > this.SPIGOT_MAX_HEALTH) {
                this.plugin.getLogger().warning("dragon-max-health superior a " + this.SPIGOT_MAX_HEALTH + ". Estableciendo " + this.SPIGOT_MAX_HEALTH + " como vida maxima");
                this.plugin.getLogger().info("Para aumentar este numero, cambiar el valor settings.attribute.maxHealth en spigot.yml");
                maxHealth.setBaseValue(this.SPIGOT_MAX_HEALTH);
                enderDragon.setHealth(maxHealth.getBaseValue());
            } else {
                this.plugin.getLogger().info("Vida del Dragon: " + this.DRAGON_MAX_HEALTH + ". (Maxima permitida por el server: " + this.SPIGOT_MAX_HEALTH + ")");
                maxHealth.setBaseValue(this.DRAGON_MAX_HEALTH);
                enderDragon.setHealth(maxHealth.getBaseValue());
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (enderDragon.isDead()) {
                    this.cancel();
                }
                checkStage(enderDragon);
                changeDragonVisuals(enderDragon);
            }
        }.runTaskTimer(this.plugin, 0, 2);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!enderDragon.isDead()) {
                    Attack attack = attackChance();
                    if (attack != null) {
                        attack.use(world.getPlayers(), enderDragon, world);
                    }
                    removeSpikes(enderDragon.getWorld());
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(this.plugin, Time.secondsToTicks(this.ATTACK_TIMER), Time.secondsToTicks(this.ATTACK_TIMER));
    }

    private void checkStage(EnderDragon enderDragon) {
        double dragonHealth = enderDragon.getHealth();
        if (dragonHealth > this.plugin.getConfig().getDouble("health-to-stage-2")) {
            this.STAGE = 1;
        }
        if (dragonHealth < this.plugin.getConfig().getDouble("health-to-stage-2")) {
            this.STAGE = 2;
        }
        if (dragonHealth < this.plugin.getConfig().getDouble("health-to-stage-3")) {
            this.STAGE = 3;
        }
    }

    private void changeDragonVisuals(EnderDragon enderDragon) {
        if (this.STAGE == 1) {
            if (this.plugin.getConfig().getString("dragon-name-stage-1") != null) {
                enderDragon.customName(MiniMessage.miniMessage().deserialize(this.plugin.getConfig().getString("dragon-name-stage-1")));
            } else {
                enderDragon.customName(MiniMessage.miniMessage().deserialize("<red>error: stage not named!"));
            }
        }
        if (this.STAGE == 2) {
            if (this.plugin.getConfig().getString("dragon-name-stage-2") != null) {
                enderDragon.customName(MiniMessage.miniMessage().deserialize(this.plugin.getConfig().getString("dragon-name-stage-2")));
            } else {
                enderDragon.customName(MiniMessage.miniMessage().deserialize("<red>error: stage not named!"));
            }
            enderDragon.setGlowing(false);
        }
        if (this.STAGE == 3) {
            if (this.plugin.getConfig().getString("dragon-name-stage-3") != null) {
                enderDragon.customName(MiniMessage.miniMessage().deserialize(this.plugin.getConfig().getString("dragon-name-stage-3")));
            } else {
                enderDragon.customName(MiniMessage.miniMessage().deserialize("<red>error: stage not named!"));
            }
            enderDragon.setGlowing(true);
        }
    }

    private Attack attackChance() {
        Random random = new Random();
        if (this.STAGE == 1) {
            if (random.nextInt(100) <= this.plugin.getConfig().getInt("attack-chance-stage-1")) {
                return getRandomAttack(stage1AttackWeightMap);
            } else {
                this.plugin.getLogger().info("Ataque de Dragón Fallido!");

            }
        }
        if (this.STAGE == 2) {
            if (random.nextInt(100) <= this.plugin.getConfig().getInt("attack-chance-stage-2")) {
                return getRandomAttack(stage2AttacksWeigthMap);
            } else {
                this.plugin.getLogger().info("Ataque de Dragón Faillo!");
            }
        }
        if (this.STAGE == 3) {
            if (random.nextInt(100) <= this.plugin.getConfig().getInt("attack-chance-stage-3")) {
                return getRandomAttack(stage3AttacksWeigthMap);
            } else {
                this.plugin.getLogger().info("Ataque de Dragón Faillo!");
            }
        }
        return null;
    }
    private Attack getRandomAttack(Map<Attack, Integer> map) {
        List<Attack> list = new ArrayList<>();
        for (Attack attack : map.keySet()) {
            for (int i = 0; i < map.get(attack); i++) {
                list.add(attack);
            }
        }
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }
    private void removeSpikes(World world) {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof BlockDisplay || entity instanceof ItemDisplay) {
                entity.remove();
            }
        }
    }

}
