package org.markmanflame55.dragonrevamped.EventListeners;

import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.markmanflame55.dragonrevamped.DragonRevamped;

public class EndWatcherEvent implements Listener {
    @EventHandler
    public void onTowerKill(EntityDeathEvent e) {
        if (e.getEntity() instanceof Enderman tower && tower.getScoreboardTags().contains("end_watcher")) {
            e.getDrops().clear();
            for (Entity entity : tower.getNearbyEntities(3,6,3)) {
                if (entity instanceof ItemDisplay) {
                    entity.remove();
                }
            }
            e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_SKELETON_HORSE_DEATH, 1.0f, 0.5f);
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Entity entity : e.getEntity().getNearbyEntities(3, 6,3)) {
                        if (entity instanceof BlockDisplay) {
                            if (((BlockDisplay) entity).getTransformation().getTranslation().y > -4) {
                                Transformation transformation = ((BlockDisplay) entity).getTransformation();
                                transformation.getTranslation().set(((BlockDisplay) entity).getTransformation().getTranslation().x, ((BlockDisplay) entity).getTransformation().getTranslation().y-0.1, ((BlockDisplay) entity).getTransformation().getTranslation().z);
                                ((BlockDisplay) entity).setTransformation(transformation);

                            } else {
                                entity.remove();
                                this.cancel();
                            }
                        }
                    }
                }
            }.runTaskTimer(DragonRevamped.getPlugin(DragonRevamped.class), 0, 1);
            if (!lookForblockDisplays(e.getEntity())) {
                DragonBossFight.areTowersAlive = false;
                System.out.println("[" + DragonRevamped.pluginName + "]: All towers are dead");
            }
        }
    }
    @EventHandler
    public void onTowerHit(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Enderman tower && tower.getScoreboardTags().contains("end_watcher")) {
            e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.BLOCK_DEEPSLATE_BRICKS_PLACE, 2.5f, 0.3f);
        }
    }
    @EventHandler
    public void onTowerTeleport(EntityTeleportEvent e) {
        if (e.getEntity() instanceof Enderman tower && tower.getScoreboardTags().contains("end_watcher")) {
            e.setCancelled(true);
        }
    }
    private boolean lookForblockDisplays(Entity entity) {
        for (Entity entity1 : entity.getWorld().getNearbyEntities(entity.getLocation(), 50,50,50)) {
            if (entity1 instanceof ItemDisplay) {
                return true;
            }
        }
        return false;
    }
}
