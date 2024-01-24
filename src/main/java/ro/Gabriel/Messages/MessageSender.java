package ro.Gabriel.Messages;

import ro.Gabriel.Language.Language;
import ro.Gabriel.Language.LanguagePath;

public interface MessageSender {
    void sendMessage(String message);

    void sendMessage(LanguagePath category, Object replacement);
    void sendMessage(LanguagePath category);


    void sendMessage(LanguagePath category, Object replacement, Language language);
    void sendMessage(LanguagePath category, Language language);
}