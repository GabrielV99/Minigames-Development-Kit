package ro.Gabriel.Language;

import ro.Gabriel.BuildBattle.ConfigManager;
import ro.Gabriel.BuildBattle.Main;
import ro.Gabriel.Managers.Annotations.ManagerClass;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Language.Impl.GeneralLanguage;
import ro.Gabriel.Language.Impl.FailedLanguage;
import ro.Gabriel.Player.UserManager;

import java.util.HashMap;

@ManagerClass(bungee = true, lobby = true, arena = true, managersBefore = ConfigManager.class)
public class LanguageManager {

    private static volatile LanguageManager INSTANCE;

    private HashMap<String, Language> languages;

    private Language defaultLanguage;

    private boolean alwaysUseDefaultLanguage;

    public static void load() {
        LanguageManager instance = getInstance();
        instance.languages = new HashMap<>();

        DataStorage config = DataStorage.getStorage("Languages/config.yml", true);

        try {
            instance.defaultLanguage = new GeneralLanguage(config.getString("default-language"));
        } catch (Exception ignored) {
            instance.defaultLanguage = new FailedLanguage();
        }

        instance.alwaysUseDefaultLanguage = config.getBoolean("always-default-language");

        instance.languages.put(instance.defaultLanguage.getLocale(), instance.defaultLanguage);

        UserManager.getDefaultUser().setLanguage(instance.defaultLanguage);
        Main.log("&ds-a activat language manager");
    }

    public static Language getDefaultLanguage() {
        return getInstance().defaultLanguage;
    }

    public static Language getLanguage(String locale) {
        LanguageManager instance = getInstance();

        Language language = instance.languages.get(locale);
        if(language == null) {
            try {
                language = new GeneralLanguage(locale);
                instance.languages.put(locale, language);
            } catch (Exception e) {
                return instance.defaultLanguage;
            }
        }

        return language;
    }

    private static LanguageManager getInstance() {
        if (INSTANCE == null) {
            synchronized (LanguageManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LanguageManager();
                }
            }
        }
        return INSTANCE;
    }
}