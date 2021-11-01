package me.playwithnathan.commands;

import me.playwithnathan.Bounties;
import me.playwithnathan.util.BountyUtil;
import me.playwithnathan.util.ConsentUtil;
import me.playwithnathan.util.PlayerUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SetCommand {
    public SetCommand(@NotNull Player player, List<String> args) {
        Economy econ = Bounties.getEconomy();

        // Need a player arg
        if(args.size() == 0) {
            PlayerUtil.message(player, "You need to include an online player's name.");
            return;
        }

        // Get the player object from arg
        Player target = PlayerUtil.find(player, args.get(0));
        if(target == null) return;

        // Target hasn't given consent
        if(ConsentUtil.noHave(target)) {
            PlayerUtil.message(player, "You can't put a bounty on that person.");
            return;
        }

        // Target already has a bounty
        if(BountyUtil.has(target)) {
            PlayerUtil.message(player, "You can't put a bounty on that person.");
            return;
        }

        // Can't put a bounty on yourself
        if(target.equals(player)) {
            PlayerUtil.message(player, "You can't put a bounty on yourself.");
            return;
        }

        // Need a money arg
        if(args.size() == 1) {
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

        // Set placeholder onto target
        BountyUtil.set(target, player, money);
    }
}