package me.playwithnathan.commands;

import me.playwithnathan.guis.ListGUI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListCommand {
    public ListCommand(@NotNull Player player, List<String> args) {
        if(args.size() > 0) {
            int pageNumber = 1;

            try {
                pageNumber = Integer.parseInt(args.get(0));
            } catch(NumberFormatException e) {
                //nothing
            }

            // Send user GUI (on page inputted)
            ListGUI listGUI = new ListGUI(player, pageNumber);
            listGUI.openGUI();
        } else {
            // Send user GUI (on page 1)
            ListGUI listGUI = new ListGUI(player, 1);
            listGUI.openGUI();
        }
    }
}
