package me.playwithnathan.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

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
     * Uncolor lore (string list or arraylist) using the removeColor() function
     * @param lore the lore (string list or arraylist) to convert
     * @return the lore (string list or arraylist) uncolored
     */
    public static List<String> uncolorLore(List<String> lore) {
        List<String> newLore = new ArrayList<>();
        lore.forEach(loreLine -> newLore.add(removeColor(loreLine)));
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
     * Capitalize the first letter of string
     * @param str the string to capitalize
     * @return the capitalized string
     */
    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Translate the input string into text component (or components if rgb)
     * @param input display name of item
     * @return text component list
     */
    public static TextComponent[] toTextComponents(String input) {
        List<TextComponent> itemComponent = new ArrayList<>();

        // No RGB
        if(!input.contains("§x")) {
            TextComponent wordComponent = new TextComponent(color(input));
            itemComponent.add(wordComponent);
            return itemComponent.toArray(new TextComponent[0]);
        }

        // Has RGB
        for(String textSplit : input.split("§x")) {
            if(textSplit.isEmpty()) continue;

            if(textSplit.length()-textSplit.replaceAll("§","").length() == 1) {
                TextComponent wordComponent = new TextComponent(color(textSplit));
                itemComponent.add(wordComponent);
                continue;
            }

            // Get color needed to convert
            String color = textSplit.substring(0, 12)
                    .replace("§x", "")
                    .replaceAll("§", ""); // then just remove extra § to get the hex code

            // If text after color, get it
            String text = textSplit.substring(12);

            TextComponent wordComponent = new TextComponent(text);
            wordComponent.setColor(ChatColor.of("#" + color)); // Convert color
            itemComponent.add(wordComponent); // Add colored word to item name
        }
        return itemComponent.toArray(new TextComponent[0]);
    }

    /**
     * Convert a string list (arraylist) to a single string
     * @param list the string list to convert
     * @return the compiled string
     */
    public static String listToString(List<String> list) {
        StringBuilder newString = new StringBuilder();
        for(String listItem : list)
            newString.append(listItem);
        return newString.toString();
    }

}