package ro.Gabriel.User;

import org.bukkit.entity.Player;
import ro.Gabriel.Language.Language;
import ro.Gabriel.Messages.MessageSender;
import ro.Gabriel.Titles.TitleSender;

public interface MinecraftUser extends MessageSender, TitleSender {
    Player getPlayer();

    Language getLanguage();
    void setLanguage(Language language);

    boolean hasPermission(String permission);
}