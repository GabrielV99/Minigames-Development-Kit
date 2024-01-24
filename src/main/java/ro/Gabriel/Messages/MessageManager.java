package ro.Gabriel.Messages;

import net.md_5.bungee.api.chat.TextComponent;

import ro.Gabriel.Language.LanguagePath;
import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Placeholder.Placeholder;
import ro.Gabriel.Language.Language;
import ro.Gabriel.Managers.Manager;
import ro.Gabriel.Main.Minigame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MessageManager implements Manager {
    private final Minigame plugin;

    private final Map<String, HoverText> hoverTexts;

    public MessageManager(Minigame plugin, DataStorage config) {
        this.plugin = plugin;
        this.hoverTexts = new HashMap<>();

        Set<String> hoverMessages = config.getKeys("HoverMessages");

        if(hoverMessages != null) {
            hoverMessages.forEach(hover -> {
                this.hoverTexts.put(hover,
                        new HoverText(plugin.getLanguageManager(), config.getString("text-path"), config.getString("hover-text-path"), config.getString("language-category"), config.getString("command"), config.getBoolean("execute-command"))
                );
            });
        }
    }

    @Override
    public Minigame getPlugin() {
        return this.plugin;
    }

    public void addHoverText(String id, String text, String hoverText, String command, boolean executeCommand) {
        this.hoverTexts.put(id, new HoverText(this.plugin.getLanguageManager(), text, hoverText, command, executeCommand));
    }

    public HoverText getHoverText(String id) {
        return this.hoverTexts.get(id);
    }

    public Object buildMessage(Object message) {
        return this.buildMessage(message, null, null);
    }

    public Object buildMessage(LanguagePath languageKey, Language language, Object placeholderSource) {
        return this.buildMessage(languageKey, language, languageKey.getPlaceholder(), placeholderSource);
    }

    @SuppressWarnings("unchecked")
    public Object buildMessage(LanguagePath languageKey, Language language, Placeholder<?> placeholder, Object placeholderSource) {
        return this.buildMessage(language.get(languageKey), language, (Placeholder<Object>)placeholder, placeholderSource);
    }

    public Object buildMessage(Object message, Placeholder<Object> placeholder, Object placeholderSource) {
        return this.buildMessage(message, null, placeholder, placeholderSource);
    }

    public Object buildMessage(Object message, Language language, Placeholder<Object> placeholder, Object placeholderSource) {
        try {
            if(message instanceof String) {
                return buildMessage(placeholder != null
                        ? placeholder.makeReplace(MessageUtils.colorString(this.plugin, (String) message), placeholderSource)
                        : MessageUtils.colorString(this.plugin, (String) message), language, placeholder, placeholderSource);
            } else if(message instanceof List) {
                return this.buildMessage((Object)MessageUtils.listToString(message), language, placeholder, placeholderSource);
            }
        } catch (Exception ignored) { ignored.printStackTrace(); }

        return DataDefaultValues.get(String.class);
    }

    private Object buildMessage(String text, Language language, Placeholder<Object> placeholder, Object placeholderSource) {
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

            HoverText hoverText = this.getHoverText(subParts[0]);
            if(hoverText != null) {
                String hText = hoverText.getText(language);

                (textComponent != null ? textComponent : (textComponent = new TextComponent(this.buildParts(parts, i)))).addExtra(hoverText.getComponent(!MessageUtils.isColored(hText) ? MessageUtils.getColorCode(parts, i) : "", language, placeholder, placeholderSource));
                textComponent.addExtra(subParts[1]);
            } else {
                if(textComponent != null) {
                    textComponent.addExtra(parts[i]);
                }
            }
        }

        return textComponent != null ? textComponent : text;
    }

    private String buildParts(String[] parts, int index) {
        StringBuilder text = new StringBuilder(parts[0]);

        for(int i = 1; i < index; i++) {
            text.append("[").append(parts[i]);
        }

        return text.toString();
    }
}