package me.playwithnathan.commands;

import me.playwithnathan.Bounties;
import me.playwithnathan.util.DataUtil;
import me.playwithnathan.util.PlayerUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NewCommand {
    public NewCommand(@NotNull Player player, List<String> args) {
        Economy econ = Bounties.getEconomy();

        // Need a player arg
        if(args.size() == 0) {
            PlayerUtil.message(player, "You need to include an online player's name.");
            return;
        }

        // Get the player object from arg
        String argTarget = args.get(0);
        Player target = null;

        for(Player onlinePlayer : player.getServer().getOnlinePlayers())
            if(onlinePlayer.getName().equals(argTarget)) target = onlinePlayer;

        // Invalid player arg
        if(target == null) {
            PlayerUtil.message(player, "Invalid player name.");
            return;
        }

        // Target hasn't given consent
        if(DataUtil.noConsent(target)) {
            PlayerUtil.message(player, "You can't put a bounty on that person.");
            return;
        }

        // Target already has a bounty
        if(DataUtil.has(target)) {
            PlayerUtil.message(player, "You can't put a bounty on that person.");
            return;
        }

        // Can't put a bounty on yourself
        if(target.equals(player)) {
            PlayerUtil.message(player, "You can't put a bounty on yourself.");
            return;
        }

        // Need a money arg
        if(args.size() < 2) {
            PlayerUtil.message(player, "You need to include an amount.");
            return;
        }

        String argMoney = args.get(1);
        double money = Double.parseDouble(argMoney);

        // Money can't be 0.0
        if(money < 1.0) {
            PlayerUtil.message(player, "A bounty must be at least &a$1.0&7.");
            return;
        }

        // Not enough money
        if(econ.getBalance(player) < money) {
            PlayerUtil.message(player, "You don't have that much money.");
            return;
        }

        // Take money from player setting bounty
        econ.withdrawPlayer(player, money);

        // Set placeholder onto target
        DataUtil.set(target, player, money);

        // Send server wide message
        for(Player onlinePlayer : Bukkit.getOnlinePlayers())
            if(onlinePlayer != target)
                PlayerUtil.message(onlinePlayer, "A bounty of &a$" + money + " &7has been placed on &c" + target.getName() + "&7.");

        // Message the target
        PlayerUtil.message(target, "A bounty of &a$" + money + " &7has been placed on &cyou&7.");
    }
}