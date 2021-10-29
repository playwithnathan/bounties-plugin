package me.playwithnathan.commands;

import me.playwithnathan.Bounties;
import me.playwithnathan.util.DataUtil;
import me.playwithnathan.util.PlayerUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UnconsentCommand {
    private static final Bounties instance = Bounties.getInstance();

    public UnconsentCommand(@NotNull Player player, List<String> args) {
        // Need a player arg
        if(args.size() == 0) {
            PlayerUtil.message(player, "&7You need to include an online player's name.");
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

        // Has the target given consent
        if(DataUtil.noConsent(target)) {
            PlayerUtil.message(player, "That player hasn't consented already.");
            return;
        }

        // Target already has a bounty
        if(!DataUtil.has(target)) {
            PlayerUtil.message(player, "That player doesn't have a bounty.");
            return;
        }

        // Remove bounty data from target
        PersistentDataContainer Data = target.getPersistentDataContainer();
        Data.remove(new NamespacedKey(instance, "bounty-consent"));
        if(DataUtil.has(target)) DataUtil.remove(target);

        // Send messages to player and target
        PlayerUtil.message(player, "Consent revoked from &6" + player.getName() + " &7to participate in bounties.");
        PlayerUtil.message(target, "&6" + player.getName() + " &7has revoked your consent to participate in bounties.", "To consent again, type &e/bounties consent&7.");
    }
}
