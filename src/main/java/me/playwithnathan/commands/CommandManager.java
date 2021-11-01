package me.playwithnathan.commands;

import me.playwithnathan.Bounties;
import me.playwithnathan.util.ConsentUtil;
import me.playwithnathan.util.PlayerUtil;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor {
    private static final Permission perms = Bounties.getPermissions();

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        // Sender must be player
        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        // Consent form
        if(ConsentUtil.noHave(player)) {
            // The consent command
            if(args.length != 0)
                if(args[0].equalsIgnoreCase("consent")) {
                new ConsentCommand(player);
                return true;
            }

            // Can't run any other command until they've given consent
            PlayerUtil.message(player, "Would you like to participate in bounties?", "If yes, type &e/bounties consent &7to participate in bounties.");
            return true;
        }

        // Requires an arg
        if(args.length == 0) {
            PlayerUtil.message(player, "Invalid Command.", "Type &e/bounties help &7for help.");
            return true;
        }

        // Convert the array-args into an arraylist-newArgs
        // This removes the "Actual command" from the args too
        List<String> newArgs = new ArrayList<>();
        for(String arg : args) {
            if(args[0].equals(arg)) continue;
            newArgs.add(arg);
        }

        // Actual command
        switch(args[0].toLowerCase()) {
            case "help":
                new HelpCommand(player);
                break;
            case "list":
                new ListCommand(player, newArgs);
                break;
            case "set":
                new SetCommand(player, newArgs);
                break;
            case "unconsent":
                if(perms.has(player, "staff.mod"))
                    new UnconsentCommand(player, newArgs);
                break;
            default:
                PlayerUtil.message(player, "Invalid Command.", "Type &e/bounties help &7for help.");
                return true;
        }

        return true;
    }
}
