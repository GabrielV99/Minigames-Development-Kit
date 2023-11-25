package ro.Gabriel.Messages;

import ro.Gabriel.Language.LanguageKey;
import ro.Gabriel.Player.SpigotUser;

import java.util.List;

public interface MessageSender {

    void sendMessage(String message);

    void sendMessage(LanguageKey key, Object placeholderSource);
    void sendMessage(LanguageKey key);

    void sendMessage(List<SpigotUser> users, LanguageKey key, Object placeholderSource);
    void sendMessage(List<SpigotUser> users, LanguageKey key);
}