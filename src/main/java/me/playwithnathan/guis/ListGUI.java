package me.playwithnathan.guis;

import me.playwithnathan.util.DataUtil;
import me.playwithnathan.util.GUI;
import me.playwithnathan.util.GUITracker;
import me.playwithnathan.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class ListGUI extends GUI {
    private final GUI gui = new GUI();
    private final Player player;

    public static final String title = "List";
    public static final int slots = 54;

    // Open our GUI to the player
    public void openGUI() {
        gui.openInventory(player);
        GUITracker.track(player, this);
    }

    /**
     * Creating the gui
     * @param player the player who will see the GUI
     */
    public ListGUI(Player player, int pageNumber) {
        gui.create(title+" #"+pageNumber, slots);

        Inventory inv = gui.getInventory();
        this.player = player;

        // We need this for paging
        int maxItems = 45;
        int maxCounter = 1;
        int startAt = (maxItems * pageNumber) - maxItems;
        int startCounter = 1;

        for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if(startCounter >= startAt) {
                // Player must give consent
                if(DataUtil.noConsent(onlinePlayer)) continue;

                // Player doesn't have a bounty on them
                if(!DataUtil.has(onlinePlayer)) continue;

                // Player is the target
                if(player.equals(onlinePlayer)) continue;

                String bounties = DataUtil.get(onlinePlayer);
                double money = Double.parseDouble(bounties.split(":")[1]);

                // Create the item
                ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta playerHeadMeta = (SkullMeta) playerHead.getItemMeta();
                assert playerHeadMeta != null;
                playerHeadMeta.setOwningPlayer(onlinePlayer);
                playerHeadMeta.setDisplayName(TextUtil.color("&7"+onlinePlayer.getDisplayName()));
                playerHeadMeta.setLore(TextUtil.colorLore(Arrays.asList("&6"+onlinePlayer.getName(), "&a$"+money)));
                playerHead.setItemMeta(playerHeadMeta);

                // Add item
                inv.addItem(playerHead);

                // Stop adding items after we've reached our max
                if(maxCounter > maxItems) {
                    inv.setItem(53, invItem(Material.OAK_SIGN, false, "Next Page", new ArrayList<>()));
                    break;
                }
                maxCounter++;
            }
            startCounter++;
        }

        // If there is more than 1 page then show a previous page button
        if(pageNumber > 1) inv.setItem(45, invItem(Material.OAK_SIGN, false, "Previous Page", new ArrayList<>()));
    }

    // When the player interacts with this GUI
    public void onInventoryClick(Player player, String itemName) {
        for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            // Player must give consent
            if(DataUtil.noConsent(onlinePlayer)) continue;

            // Player doesn't have a bounty on them
            if(!DataUtil.has(onlinePlayer)) continue;

            // Paging
            String guiTitle = TextUtil.removeColor(gui.invTitle);
            int pageNumber = 1;

            if(guiTitle.contains(title) && guiTitle.contains("#"))
                pageNumber = Integer.parseInt(guiTitle.substring(guiTitle.indexOf("#")+1).trim());

            switch(itemName) {
                case "Previous Page":
                    pageNumber--;
                    player.performCommand("bounties list "+pageNumber);
                    break;

                case "Next Page":
                    pageNumber++;
                    player.performCommand("bounties list "+pageNumber);
                    break;
            }

        }
    }
}