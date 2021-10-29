package me.playwithnathan.events;

import me.playwithnathan.util.GUITracker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InvClose implements Listener {
    @EventHandler
    public void onInvClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        if(GUITracker.isTracking(player)) GUITracker.untrack(player);
    }
}
