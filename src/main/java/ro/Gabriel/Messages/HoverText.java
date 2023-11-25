package ro.Gabriel.Messages;

import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;
import ro.Gabriel.Language.LanguageCategory;
import ro.Gabriel.Placeholder.Placeholder;
import ro.Gabriel.Language.Language;
import net.md_5.bungee.api.chat.*;

public class HoverText {
    private final String text, hoverText, command;

    private final boolean executeCommand;

    public HoverText(String text, String hoverText, String command) {
        this.text = text;
        this. hoverText  = hoverText;
        this.command = command;
        this.executeCommand = true;
    }

    public HoverText(String text, String hoverText, String command, boolean executeCommand) {
        this.text = text;
        this. hoverText  = hoverText;
        this.command = command;
        this.executeCommand = executeCommand;
    }

    public TextComponent getComponent(Language language, Placeholder<Object> placeholder, Object placeholderSource) {
        return getComponent("", language, placeholder, placeholderSource);
    }

    public TextComponent getComponent(String textPrefix, Language language, Placeholder<Object> placeholder, Object placeholderSource) {
        String text = (language != null ? language.getString(this.text, LanguageCategory.MESSAGES) : this.text);
        text = textPrefix + (DataDefaultValues.equals(text, String.class) ? this.text : text);

        TextComponent hoverComponent = new TextComponent(
                placeholder != null && placeholderSource != null
                        ? placeholder.makeReplace(text, placeholderSource)
                        : text);

        String hoverText = language != null ? language.getString(this.hoverText, LanguageCategory.MESSAGES) : this.hoverText;
        hoverText = DataDefaultValues.equals(hoverText, String.class) ? this.hoverText : hoverText;

        hoverComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(
                MessageUtils.colorString(placeholder != null && placeholderSource != null
                        ? placeholder.makeReplace(hoverText, placeholderSource)
                        : hoverText
                )).create()));
        hoverComponent.setClickEvent(new ClickEvent((executeCommand ? ClickEvent.Action.RUN_COMMAND : ClickEvent.Action.SUGGEST_COMMAND), command));

        return hoverComponent;
    }

    public String getText() {
        return this.text;
    }
}