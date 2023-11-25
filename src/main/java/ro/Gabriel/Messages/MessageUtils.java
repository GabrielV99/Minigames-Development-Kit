package ro.Gabriel.Messages;

import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;
import ro.Gabriel.Language.LanguageCategory;
import ro.Gabriel.Placeholder.Placeholder;
import ro.Gabriel.Language.LanguageKey;
import ro.Gabriel.Language.Language;
import ro.Gabriel.BuildBattle.Main;

import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.ChatColor;

import java.util.stream.Collectors;
import java.util.regex.Pattern;
import java.util.List;

public class MessageUtils {

    public static String colorString(char altColorCode, String string) {
        return ChatColor.translateAlternateColorCodes(altColorCode, string);
    }

    public static String colorString(String string) {
        return colorString(Main.getAltColorCode(), string);
    }

    public static List<String> colorStringList(char altColorCode, List<String> stringList) {
        return stringList.stream().map(line -> MessageUtils.colorString(altColorCode, line)).collect(Collectors.toList());
    }

    public static List<String> colorStringList(List<String> stringList) {
        return colorStringList(Main.getAltColorCode(), stringList);
    }

    public static Object transform(Object message) {
        return transform(message, null, null);
    }

    public static Object transform(LanguageKey languageKey, Language language, Object placeholderSource) {
        return transform(languageKey, language, languageKey.getPlaceholder(), placeholderSource);
    }

    @SuppressWarnings("unchecked")
    public static Object transform(LanguageKey languageKey, Language language, Placeholder<?> placeholder, Object placeholderSource) {
        return transform(language.get(languageKey, LanguageCategory.MESSAGES), language, (Placeholder<Object>)placeholder, placeholderSource);
    }

    public static Object transform(Object message, Placeholder<Object> placeholder, Object placeholderSource) {
        return transform(message, null, placeholder, placeholderSource);
    }

    public static Object transform(Object message, Language language, Placeholder<Object> placeholder, Object placeholderSource) {
        try {
            if(message instanceof String) {
                return transform(placeholder != null
                        ? placeholder.makeReplace(MessageUtils.colorString((String) message), placeholderSource)
                        : MessageUtils.colorString((String) message), language, placeholder, placeholderSource);
            } else if(message instanceof List) {
                return transform((Object)listToString(message), language, placeholder, placeholderSource);
            }
        } catch (Exception ignored) { ignored.printStackTrace(); }

        return DataDefaultValues.get(String.class);
    }

    private static Object transform(String text, Language language, Placeholder<Object> placeholder, Object placeholderSource) {
        if(!(text.contains("[") && text.contains("]"))) {
            return text;
        }
        String[] parts = text.split("\\[");

        if(parts.length == 1) {
            return text;
        }

        TextComponent textComponent = null;

        for(int i = 1; i < parts.length; i++) {
            String[] subParts = parts[i].split("]", 2);

            HoverText hoverText = MessagesManager.getHoverText(subParts[0]);
            if(hoverText != null) {
                String hText = language != null ? language.getString(hoverText.getText(), LanguageCategory.MESSAGES) : hoverText.getText();
                hText = hText == DataDefaultValues.get(String.class) ? hoverText.getText() : hText;

                (textComponent != null ? textComponent : (textComponent = new TextComponent(MessageUtils.buildParts(parts, i)))).addExtra(hoverText.getComponent(!MessageUtils.isColored(hText) ? getColorCode(parts, i) : "", language, placeholder, placeholderSource));
                textComponent.addExtra(subParts[1]);
            } else {
                Main.log("TM2: " + subParts[0]);
                if(textComponent != null) {
                    textComponent.addExtra(parts[i]);
                }
            }
        }

        return textComponent != null ? textComponent : text;
    }

    private static String buildParts(String[] parts, int index) {
        StringBuilder text = new StringBuilder(parts[0]);

        for(int i = 1; i < index; i++) {
            text.append("[").append(parts[i]);
        }

        return text.toString();
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

    private static String getColorCode(String[] texts, int index) {
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
