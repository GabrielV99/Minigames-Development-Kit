package ro.Gabriel.Messages;

import org.bukkit.ChatColor;

public class MessageUtils {

    public static String colorString(char altColorCode, String string) {
        return ChatColor.translateAlternateColorCodes(altColorCode, string);
    }

}
