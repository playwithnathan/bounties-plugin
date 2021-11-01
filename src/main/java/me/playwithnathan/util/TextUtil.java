package me.playwithnathan.util;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

    /**
     * The string to color
     * @param msg the string with hex codes in it
     * @return the colored string
     */
    public static String color(String msg) {
        Pattern HEX_PATTERN = Pattern.compile("&#(\\w{5}[0-9a-f])");
        Matcher matcher = HEX_PATTERN.matcher(msg);
        StringBuffer buffer = new StringBuffer();

        while(matcher.find())
            matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());

        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }

    /**
     * Color lore (string list or arraylist) using the color() function
     * @param lore the lore (string list or arraylist) to convert
     * @return the lore (string list or arraylist) uncolored
     */
    public static List<String> colorLore(List<String> lore) {
        List<String> newLore = new ArrayList<>();
        lore.forEach(loreLine -> newLore.add(color(loreLine)));
        return newLore;
    }

    /**
     * Remove color from the string
     * @param str the string to remove the color from
     * @return the string with no color
     */
    public static String removeColor(String str) {
        str = color(str);
        str = ChatColor.stripColor(str);
        str = str.replaceAll("➤ ", "");
        return str;
    }

    /**
     * Get a random integer between the max and min
     * @param min the minimum the random number can be
     * @param max the maximum the random number can be
     * @return integer between the min and max param
     */
    public static int random(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}