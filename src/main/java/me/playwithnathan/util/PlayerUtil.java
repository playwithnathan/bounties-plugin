package me.playwithnathan.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class PlayerUtil {

    public static void message(@NotNull Player player, @NotNull String... messages) {
        player.sendMessage(TextUtil.color("&8[Bounties] ➤ &7" + messages[0]));

        // Only one message
        if(messages.length == 1) return;

        // Multiple messages
        messages = Arrays.copyOfRange(messages, 1, messages.length);
        for(String msg : messages) player.sendMessage(TextUtil.color("&8➤ &7" + msg));
    }

    public static @Nullable Player find(@NotNull Player player, @NotNull String playerArg) {
        Player target = find(playerArg);

        // Invalid player arg
        if(target == null) {
            PlayerUtil.message(player, "Invalid player name.");
            return null;
        }

        return target;
    }

    public static @Nullable Player find(@NotNull String playerArg) {
        Player target = null;

        for(Player onlinePlayer : Bukkit.getOnlinePlayers())
            if(onlinePlayer.getName().equals(playerArg)) target = onlinePlayer;

        return target;
    }

}
