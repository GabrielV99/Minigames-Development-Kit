package ro.Gabriel.Messages;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

import ro.Gabriel.Language.LanguageCategory;
import ro.Gabriel.Language.LanguageManager;
import ro.Gabriel.Placeholder.Placeholder;
import ro.Gabriel.Language.Language;
import ro.Gabriel.Class.ClassUtils;

public class HoverText {
    private final String textPath, hoverTextPath, command;

    private LanguageCategory text, hoverText;

    private final boolean executeCommand;

    private final LanguageManager languageManager;

    public HoverText(LanguageManager languageManager, String textPath, String hoverTextPath, String command) {
        this(languageManager, textPath, hoverTextPath, command, true);
    }

    public HoverText(LanguageManager languageManager, String textPath, String hoverTextPath, String command, boolean executeCommand) {
        this(languageManager, textPath, hoverTextPath, null, command, executeCommand);
    }

    public HoverText(LanguageManager languageManager, String textPath, String hoverTextPath, String languageCategory, String command) {
        this(languageManager, textPath, hoverTextPath, languageCategory, command, true);
    }

    public HoverText(LanguageManager languageManager, String textPath, String hoverTextPath, String languageCategory, String command, boolean executeCommand) {
        this.textPath = textPath;
        this.hoverTextPath = hoverTextPath;
        this.command = command;
        this.executeCommand = executeCommand;
        this.languageManager = languageManager;

        Class<? extends LanguageCategory> languageCategoryClass = null;
        if(languageCategory != null) {
            languageCategoryClass = this.languageManager.getCategory(languageCategory);

            this.text = ClassUtils.getEnumValue(languageCategoryClass, textPath);
            this.hoverText = ClassUtils.getEnumValue(languageCategoryClass, hoverTextPath);
        } else {
            for(Class<? extends LanguageCategory> category : this.languageManager.getCategories()) {
                if(this.text == null) {
                    this.text = ClassUtils.getEnumValue(category, textPath);
                }
                if(this.hoverText == null) {
                    this.hoverText = ClassUtils.getEnumValue(category, hoverTextPath);
                }

                if(this.text != null && this.hoverText != null) {
                    break;
                }
            }
        }
    }

    public TextComponent getComponent(Language language, Placeholder<Object> placeholder, Object placeholderSource) {
        return getComponent("", language, placeholder, placeholderSource);
    }

    public TextComponent getComponent(String textPrefix, Language language, Placeholder<Object> placeholder, Object placeholderSource) {
        String text = textPrefix + this.getText(language);
        TextComponent hoverComponent = new TextComponent(this.getString(text, this.text, placeholder, placeholderSource));

        String hoverText = this.getHoverText(language);
        hoverComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(
                MessageUtils.colorString(this.languageManager.getMainInstance(), this.getString(hoverText, this.hoverText, placeholder, placeholderSource))
        ).create()));

        hoverComponent.setClickEvent(new ClickEvent((executeCommand ? ClickEvent.Action.RUN_COMMAND : ClickEvent.Action.SUGGEST_COMMAND), command));
        return hoverComponent;
    }

    public String getText(Language language) {
        String text = this.text != null && language != null ? language.getString(this.text) : this.textPath;
        return text != null ? text : this.textPath;
    }

    public String getHoverText(Language language) {
        String hoverText = this.hoverText != null && language != null ? language.getString(this.hoverText) : this.hoverTextPath;
        return hoverText != null ? hoverText : this.hoverTextPath;
    }

    @SuppressWarnings("unchecked")
    private String getString(String text, LanguageCategory path, Placeholder<Object> placeholder, Object placeholderSource) {
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