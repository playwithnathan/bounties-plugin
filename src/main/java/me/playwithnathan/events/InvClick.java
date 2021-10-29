package me.playwithnathan.events;

import me.playwithnathan.guis.ListGUI;
import me.playwithnathan.util.GUI;
import me.playwithnathan.util.GUITracker;
import me.playwithnathan.util.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InvClick implements Listener {
    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if(GUITracker.isTracking(player)) {
            if(e.getCurrentItem() == null) return;
            GUI gui = GUITracker.getGUI(player);

            e.setCancelled(true);

            ItemStack item = e.getCurrentItem();
            if(item == null) return;
            if(item.getItemMeta() == null) return;

            String itemName = ItemUtil.cleanName(item);
            if(itemName.length() < 1) return;

            // List GUI
            if(gui instanceof ListGUI) {
                ListGUI listGUI = (ListGUI) gui;
                listGUI.onInventoryClick(player, itemName);
            }
        }
    }
}