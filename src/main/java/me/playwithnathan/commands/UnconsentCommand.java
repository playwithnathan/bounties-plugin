package me.playwithnathan.commands;

import me.playwithnathan.util.BountyUtil;
import me.playwithnathan.util.ConsentUtil;
import me.playwithnathan.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UnconsentCommand {
    public UnconsentCommand(@NotNull Player player, List<String> args) {
        // Need a player arg
        if(args.size() == 0) {
            PlayerUtil.message(player, "&7You need to include an online player's name.");
            return;
        }

        // Get the player object from arg
        Player target = PlayerUtil.find(player, args.get(0));
        if(target == null) return;

        // Has the target given consent
        if(ConsentUtil.noHave(target)) {
            PlayerUtil.message(player, "That player hasn't consented already.");
            return;
        }

        // Remove bounty data from target
        ConsentUtil.remove(player, target);
        if(BountyUtil.has(target)) BountyUtil.remove(target);
    }
}
