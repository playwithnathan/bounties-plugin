package me.playwithnathan.classes;

import me.playwithnathan.Bounties;
import me.playwithnathan.util.ConsentUtil;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TabComplete implements TabCompleter {
    private static final Permission perms = Bounties.getPermissions();

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String string, String[] args) {
        // Sender must be player
        if(!(sender instanceof Player)) return null;
        Player player = (Player) sender;

        if(args[0].isEmpty()) {
            // Consent form
            if(ConsentUtil.noHave(player))
                return StringUtil.copyPartialMatches(args[0], Collections.singletonList("consent"), new ArrayList<>());

            // If player is a mod
            if(perms.has(player, "staff.mod"))
                return StringUtil.copyPartialMatches(args[0], Arrays.asList("help", "list", "set", "unconsent"), new ArrayList<>());

            return StringUtil.copyPartialMatches(args[0], Arrays.asList("help", "list", "set"), new ArrayList<>());
        }

        return null;
    }
}
