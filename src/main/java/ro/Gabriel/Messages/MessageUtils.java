package ro.Gabriel.Messages;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import ro.Gabriel.Main.Minigame;
import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MessageUtils {

    public static String colorString(char altColorCode, String string) {
        return ChatColor.translateAlternateColorCodes(altColorCode, string);
    }

    public static String colorString(Minigame minigame, String string) {
        return colorString(minigame.getAltColorCode(), string);
    }

    public static List<String> colorStringList(char altColorCode, List<String> stringList) {
        return stringList.stream().map(line -> MessageUtils.colorString(altColorCode, line)).collect(Collectors.toList());
    }

    public static List<String> colorStringList(Minigame minigame, List<String> stringList) {
        return colorStringList(minigame.getAltColorCode(), stringList);
    }

    public static String textComponentToString(TextComponent textComponent) {
        StringBuilder stringBuilder = new StringBuilder(textComponent.getText());
        textComponent.getExtra().forEach(e -> stringBuilder.append( ((TextComponent)e).getText() ));

        return stringBuilder.toString();
    }

    @SuppressWarnings("unchecked")
    public static String listToString(Object messageSource) {
        if(messageSource instanceof List) {
            StringBuilder stringBuilder = new StringBuilder();

            List<String> messages = ((List<String>)messageSource);
            for(int i = 0; i < messages.size(); i++) {
                stringBuilder.append(messages.get(i));
                if(i < messages.size() - 1) {
                    stringBuilder.append("\n");
                }
            }
            return stringBuilder.toString();
        }

        return messageSource instanceof String ? ((String)messageSource) : DataDefaultValues.get(String.class);
    }

    public static boolean isColored(String color) {
        return Pattern.compile("(&[0-9a-fk-or])", Pattern.CASE_INSENSITIVE).matcher(color).find() || Pattern.compile("(§[0-9a-fk-or])", Pattern.CASE_INSENSITIVE).matcher(color).find();
    }

    public static String getColorCode(String[] texts, int index) {
        String colorCode = null;
        for (int i = index - 1; i >= 0; i--) {
            colorCode = getColorCode(texts[i]);

            if(colorCode != null) {
                break;
            }
        }

        return colorCode != null ? colorCode : DataDefaultValues.get(String.class);
    }

    private static String getColorCode(String text) {
        int colorCharIndex = text.lastIndexOf('§');
        if (colorCharIndex >= 0 && colorCharIndex < text.length() - 1) {
            char colorChar = text.charAt(colorCharIndex + 1);
            ChatColor chatColor = ChatColor.getByChar(colorChar);
            if (chatColor != null) {
                return "§" + colorChar;
            }
        }
        return null;
    }
}