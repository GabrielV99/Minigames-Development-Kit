package ro.Gabriel.Messages;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

import ro.Gabriel.Language.LanguageCategory;
import ro.Gabriel.Language.LanguageManager;
import ro.Gabriel.Placeholder.Placeholder;
import ro.Gabriel.Language.LanguagePath;
import ro.Gabriel.Language.Language;

import javax.annotation.Nonnull;

public class HoverText {
    private String text, hoverText;
    private final String command;

    private LanguagePath textPath, hoverTextPath;

    private final boolean executeCommand;

    private final LanguageManager languageManager;

    // used for giving text and hoverText as string and use them as such
    public HoverText(@Nonnull LanguageManager languageManager, @Nonnull String text, String hoverText, String command, boolean executeCommand) {
        this.languageManager = languageManager;
        this.text = text;
        this.hoverText = hoverText;
        this.command = command;
        this.executeCommand = executeCommand;
    }

    public HoverText(@Nonnull LanguageManager languageManager, @Nonnull String text, String hoverText) {
        this(languageManager, text, hoverText, null, false);
    }

    public HoverText(@Nonnull LanguageManager languageManager, @Nonnull String text) {
        this(languageManager, text, null, null, false);
    }

    public HoverText(@Nonnull LanguageManager languageManager, @Nonnull LanguagePath textPath, LanguagePath hoverTextPath, String command, boolean executeCommand) {
        this.languageManager = languageManager;
        this.textPath = textPath;
        this.hoverTextPath = hoverTextPath;
        this.command = command;
        this.executeCommand = executeCommand;
    }

    public HoverText(@Nonnull LanguageManager languageManager, @Nonnull String textPath, String hoverTextPath, @Nonnull LanguageCategory category, String command, boolean executeCommand) {
        this.languageManager = languageManager;

        this.text = (this.textPath = languageManager.getPath(category, textPath)) == null ? textPath : null;
        if(hoverTextPath != null) {
            this.hoverText = (this.hoverTextPath = languageManager.getPath(category, hoverTextPath)) == null ? hoverTextPath : null;
        }

        this.command = command;
        this.executeCommand = executeCommand;
    }

    public HoverText(@Nonnull LanguageManager languageManager, @Nonnull String textPath, String hoverTextPath, @Nonnull String category, String command, boolean executeCommand) {
        this(languageManager, textPath, hoverTextPath, languageManager.getCategory(category), command, executeCommand);
    }

    public TextComponent getComponent(Language language, Placeholder<Object> placeholder, Object placeholderSource) {
        return getComponent("", language, placeholder, placeholderSource);
    }

    public TextComponent getComponent(String textPrefix, Language language, Placeholder<Object> placeholder, Object placeholderSource) {
        String text = textPrefix + this.getText(language);
        TextComponent hoverComponent = new TextComponent(this.getString(text, this.textPath, placeholder, placeholderSource));

        String hoverText = this.getHoverText(language);
        hoverComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(
                MessageUtils.colorString(this.languageManager.getPlugin(), this.getString(hoverText, this.hoverTextPath, placeholder, placeholderSource))
        ).create()));

        if(command != null) {
            hoverComponent.setClickEvent(new ClickEvent((executeCommand ? ClickEvent.Action.RUN_COMMAND : ClickEvent.Action.SUGGEST_COMMAND), command));
        }

        return hoverComponent;
    }

    public String getText(Language language) {
        String text = this.textPath != null && language != null ? language.getString(this.textPath) : this.text;
        return text != null ? text : this.text;
    }

    public String getHoverText(Language language) {
        String hoverText = this.hoverTextPath != null && language != null ? language.getString(this.hoverTextPath) : this.hoverText;
        return hoverText != null ? hoverText : this.hoverText;
    }

    @SuppressWarnings("unchecked")
    private String getString(String text, LanguagePath path, Placeholder<Object> placeholder, Object placeholderSource) {
        if(placeholderSource != null) {
            if(path != null && path.getPlaceholder() != null) {
                text = ((Placeholder<Object>)path.getPlaceholder()).makeReplace(text, placeholderSource);
            }
            if(placeholder != null) {
                text = placeholder.makeReplace(text, placeholderSource);
            }
        }
        return text;
    }
}