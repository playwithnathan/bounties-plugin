package me.playwithnathan.util;

import me.playwithnathan.Bounties;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class ConsentUtil {
    private static final Bounties instance = Bounties.getInstance();

    public static void add(@NotNull Player player) {
        PersistentDataContainer Data = player.getPersistentDataContainer();
        Data.set(new NamespacedKey(instance, "bounty-consent"), PersistentDataType.STRING, "yes");

        PlayerUtil.message(player, "Welcome to &c&lBounties&7!", "Type &e/bounties help &7to learn more!");
    }

    public static void remove(@NotNull Player player, @NotNull Player target) {
        PersistentDataContainer Data = target.getPersistentDataContainer();
        Data.remove(new NamespacedKey(instance, "bounty-consent"));

        // Send messages to player and target
        PlayerUtil.message(player, "Consent revoked from &6" + target.getName() + " &7to participate in bounties.");
        PlayerUtil.message(target, "&6" + player.getName() + " &7has revoked your consent to participate in bounties.", "To consent again, type &e/bounties consent&7.");
    }

    public static boolean noHave(@NotNull Player player) {
        PersistentDataContainer Data = player.getPersistentDataContainer();
        return !Data.has(new NamespacedKey(instance, "bounty-consent"), PersistentDataType.STRING);
    }

}
