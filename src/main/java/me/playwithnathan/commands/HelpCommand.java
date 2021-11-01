package me.playwithnathan.commands;

import me.playwithnathan.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HelpCommand {
    public HelpCommand(@NotNull Player player) {
        PlayerUtil.message(player, "&f&lBounties Commands:", "&e/bounties list &7Displays all the current bounties.", "&e/bounties set <player> <amount> &7Set a bounty on a player.");
    }
}
