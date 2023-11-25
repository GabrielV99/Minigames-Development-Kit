package ro.Gabriel.Placeholder.Placeholders;

import ro.Gabriel.Placeholder.Annotations.PlaceholderClass;
import ro.Gabriel.Placeholder.Placeholder;
import ro.Gabriel.Player.SpigotUser;

@PlaceholderClass(bungee = true, lobby = true, arena = true)
public class PlayerPlaceholder implements Placeholder<SpigotUser> {
    @Override
    public String makeReplace(String text, SpigotUser replacementSource) {
        return text.replace("%player%", replacementSource.getName());
    }
}