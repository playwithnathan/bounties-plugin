package me.playwithnathan.commands;

import me.playwithnathan.util.ConsentUtil;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ConsentCommand {
    public ConsentCommand(@NotNull Player player) {
        ConsentUtil.add(player);
    }
}