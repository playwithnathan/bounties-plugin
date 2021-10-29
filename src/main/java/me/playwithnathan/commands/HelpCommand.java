package me.playwithnathan.commands;

import me.playwithnathan.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HelpCommand {
    public HelpCommand(@NotNull Player player) {
        PlayerUtil.message(player, "&f&nBounties Commands:", "&f- &e/bounties list &7Displays all the current bounties.", "&f- &e/bounties new <player> <amount> &7Set a bounty on a player.");
    }
}
