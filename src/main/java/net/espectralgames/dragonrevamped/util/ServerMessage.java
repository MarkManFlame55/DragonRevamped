package net.espectralgames.dragonrevamped.util;

import net.espectralgames.dragonrevamped.DragonRevamped;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ServerMessage {
    public static void broadcast(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(message));
        }
    }

}
