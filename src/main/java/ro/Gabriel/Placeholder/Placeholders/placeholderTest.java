package ro.Gabriel.Placeholder.Placeholders;

import ro.Gabriel.Placeholder.Placeholder;

public class placeholderTest implements Placeholder {
    @Override
    public String makeReplace(String text, Object replacementSource) {
        return text.replace("%onlinePlayers%", replacementSource.toString()).replace("%maxPlayers%", "100");
    }
}
