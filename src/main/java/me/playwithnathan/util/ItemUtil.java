package me.playwithnathan.util;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ItemUtil {

    /**
     * Get the colorless name of an item
     * @param item the item
     * @return the colorless name of the item
     */
    public static String cleanName(@NotNull ItemStack item) {
        return TextUtil.removeColor(Objects.requireNonNull(item.getItemMeta()).getDisplayName());
    }

}
