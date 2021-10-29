package me.playwithnathan.util;

import org.bukkit.entity.Player;

public class PlayerUtil {

    public static void message(Player player, String... messages) {
        // Only one message
        if(messages.length == 1) {
            player.sendMessage(TextUtil.color("&7" + messages[0]));
            return;
        }

        // Multiple messages
        for(String msg : messages) player.sendMessage(TextUtil.color("&7" + msg));
    }

}
