package ro.Gabriel.Language;

import ro.Gabriel.Player.SpigotUser;

import java.util.List;

public class LanguageUtils {
    public static boolean wasBefore(List<SpigotUser> users, int index) {
        if(index < 0 || index >= users.size()) {
            return true;
        }

        Language language = users.get(index).getLanguage();
        for(int i = 0; i < index; i++) {
            if(users.get(i).getLanguage().equals(language)) {
                return true;
            }
        }
        return false;
    }
}