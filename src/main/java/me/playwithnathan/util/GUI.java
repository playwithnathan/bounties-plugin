package me.playwithnathan.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GUI implements InventoryHolder {
    public Inventory inv;
    public String invTitle = "None";
    public int invSize = 54;

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }

    /**
     * Add the basic information into the new GUI
     * @param title the title of the GUI
     * @param slots the size of the GUI
     */
    public void create(@NotNull String title, int slots) {
        this.invTitle = TextUtil.color("Bounties " + title);
        this.invSize = slots;
        this.inv = Bukkit.createInventory(this, invSize, invTitle);
    }

    /**
     * Open the GUI
     * @param player the player to open the GUI to
     */
    public void openInventory(@NotNull Player player) {
        // Fill inventory
        for(int i = 0; i < inv.getSize(); i++)
            if(inv.getItem(i) == null)
                inv.setItem(i, invItem(Material.GRAY_STAINED_GLASS_PANE, false, TextUtil.color(ChatColor.BLACK+""), new ArrayList<>()));

        player.openInventory(inv);
    }

    /**
     * Create a custom inventory item
     * @param material the material of the item
     * @param enchanted whether to give the enchant glint
     * @param name the name of the item
     * @param lore the lore of the item
     * @return The custom item as an ItemStack
     */
    public static ItemStack invItem(@NotNull Material material, @NotNull Boolean enchanted, @NotNull String name, @NotNull ArrayList<String> lore) {
        ItemStack itemAdding = new ItemStack(material, 1);
        ItemMeta itemMeta = itemAdding.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(TextUtil.color("&f" + name));

        if(enchanted) itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);

        itemMeta.setLore(TextUtil.colorLore(lore));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemAdding.setItemMeta(itemMeta);

        return itemAdding;
    }

}