package me.playwithnathan.util;

import me.playwithnathan.Bounties;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class DataUtil {
    private static final Bounties instance = Bounties.getInstance();

    public static void set(Player target, Player player, double money) {
        PersistentDataContainer Data = target.getPersistentDataContainer();
        Data.set(new NamespacedKey(instance, "bounty"), PersistentDataType.STRING, player.getName()+":"+money);
    }

    public static void remove(Player player) {
        PersistentDataContainer Data = player.getPersistentDataContainer();
        Data.remove(new NamespacedKey(instance, "bounty"));
    }

    public static boolean has(Player player) {
        PersistentDataContainer Data = player.getPersistentDataContainer();
        return Data.has(new NamespacedKey(instance, "bounty"), PersistentDataType.STRING);
    }

    public static String get(Player player) {
        PersistentDataContainer Data = player.getPersistentDataContainer();
        if(!has(player)) return "";
        return Data.get(new NamespacedKey(instance, "bounty"), PersistentDataType.STRING);
    }

    public static boolean noConsent(Player player) {
        PersistentDataContainer Data = player.getPersistentDataContainer();
        return !Data.has(new NamespacedKey(instance, "bounty-consent"), PersistentDataType.STRING);
    }

}
