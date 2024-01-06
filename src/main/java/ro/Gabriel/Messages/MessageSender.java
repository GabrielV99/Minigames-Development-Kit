package ro.Gabriel.Messages;

import ro.Gabriel.Language.LanguageCategory;

public interface MessageSender {
    void sendMessage(String message);

    void sendMessage(LanguageCategory category, Object replacement);
    void sendMessage(LanguageCategory category);
}