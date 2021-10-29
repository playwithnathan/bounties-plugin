package me.playwithnathan.commands;

import me.playwithnathan.Bounties;
import me.playwithnathan.util.PlayerUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class ConsentCommand {
    private static final Bounties instance = Bounties.getInstance();

    public ConsentCommand(@NotNull Player player) {
        PersistentDataContainer Data = player.getPersistentDataContainer();
        Data.set(new NamespacedKey(instance, "bounty-consent"), PersistentDataType.STRING, "yes");

        PlayerUtil.message(player, "Welcome to &c&lBounties&7!", "Type &e/bounties help &7to learn more!");
    }
}