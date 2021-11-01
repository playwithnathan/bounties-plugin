package me.playwithnathan.util;

import me.playwithnathan.Bounties;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class BountyUtil {
    private static final Bounties instance = Bounties.getInstance();
    private static final Economy econ = Bounties.getEconomy();

    public static void set(@NotNull Player target, @NotNull Player player, double money) {
        set(target, player.getName(), money);

        // Remove bal
        econ.withdrawPlayer(player, money);
    }

    public static void set(@NotNull Player target, @NotNull String player, double money) {
        // Sets target data
        PersistentDataContainer Data = target.getPersistentDataContainer();
        Data.set(new NamespacedKey(instance, "bounty"), PersistentDataType.STRING, player+":"+money);

        // Send server wide message
        for(Player onlinePlayer : Bukkit.getOnlinePlayers()) if(onlinePlayer != target)
            PlayerUtil.message(onlinePlayer, "A bounty of &a$" + money + " &7has been placed on &c" + target.getName() + "&7.");

        // Message the target
        PlayerUtil.message(target, "A bounty of &a$" + money + " &7has been placed on &cyou&7.");
    }

    public static void remove(@NotNull Player player, @NotNull Player killer, double money) {
        remove(player);

        // Add bal
        econ.depositPlayer(killer, money);

        // Send server wide message
        Bukkit.getServer().broadcastMessage(TextUtil.color("&7The bounty of &a$" + money + " &7on &c" + player.getName() + " &7has been claimed by &c" + killer.getName() + "&7."));
    }

    public static void remove(@NotNull Player player) {
        PersistentDataContainer Data = player.getPersistentDataContainer();
        Data.remove(new NamespacedKey(instance, "bounty"));
    }

    public static boolean has(@NotNull Player player) {
        PersistentDataContainer Data = player.getPersistentDataContainer();
        return Data.has(new NamespacedKey(instance, "bounty"), PersistentDataType.STRING);
    }

    public static String get(@NotNull Player player) {
        PersistentDataContainer Data = player.getPersistentDataContainer();
        if(!has(player)) return "";
        return Data.get(new NamespacedKey(instance, "bounty"), PersistentDataType.STRING);
    }

}
