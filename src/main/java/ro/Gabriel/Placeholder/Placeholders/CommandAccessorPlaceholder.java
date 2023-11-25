package ro.Gabriel.Placeholder.Placeholders;

import ro.Gabriel.Placeholder.Annotations.PlaceholderClass;
import ro.Gabriel.Placeholder.Placeholder;

@PlaceholderClass(bungee = true, lobby = true, arena = true)
public class CommandAccessorPlaceholder implements Placeholder<String> {
    @Override
    public String makeReplace(String text, String replacementSource) {
        return text.replace("%commandAccessor%", replacementSource);
    }
}