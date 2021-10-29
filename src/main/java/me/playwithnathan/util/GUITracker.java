package me.playwithnathan.util;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUITracker {
    private static final Map<UUID, GUI> tracker = new HashMap<>();

    public static void track(Player player, GUI gui) {
        if(isTracking(player)) {
            tracker.replace(player.getUniqueId(), gui);
        } else {
            tracker.put(player.getUniqueId(), gui);
        }
    }

    public static void untrack(Player player) {
        tracker.remove(player.getUniqueId());
    }

    public static boolean isTracking(Player player) {
        return tracker.containsKey(player.getUniqueId());
    }

    public static GUI getGUI(Player player) {
        return tracker.get(player.getUniqueId());
    }
}
