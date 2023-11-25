package ro.Gabriel.Placeholder.Placeholders;

import ro.Gabriel.Placeholder.Annotations.PlaceholderClass;
import ro.Gabriel.Placeholder.Placeholder;

@PlaceholderClass(bungee = true, lobby = true, arena = true)
public class HelpCommandsPlaceholder implements Placeholder<Object[]> {
    @Override
    public String makeReplace(String text, Object[] replacementSource) {
        return text
                .replace("%commands%", replacementSource[0].toString())
                .replace("%group%", replacementSource[1].toString());
    }
}
