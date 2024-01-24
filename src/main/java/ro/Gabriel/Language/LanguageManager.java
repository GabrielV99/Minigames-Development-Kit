package ro.Gabriel.Language;

import ro.Gabriel.Class.Validators.LanguageCategoryValidator;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Language.Impl.GeneralLanguage;
import ro.Gabriel.Messages.MessageManager;
import ro.Gabriel.Main.Config.FilePath;
import ro.Gabriel.Managers.Manager;
import ro.Gabriel.Main.Minigame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageManager implements Manager {

    private final Minigame plugin;
    private final Map<String, Language> registeredLanguages;
    private final List<Class<? extends LanguagePath>> languageCategories;

    private final MessageManager messageManager;

    private final Language defaultLanguage;
    private final boolean alwaysDefaultLanguage;
    private final String hoverMessageBounded;

    private final List<String> availableLocales;

    private String categoryExtension = ".yml";

    public LanguageManager(Minigame plugin) {
        this.plugin = plugin;
        this.registeredLanguages = new HashMap<>();
        this.languageCategories = plugin.searchClasses(LanguageCategoryValidator.class, LanguagePath.class);

        this.categoryExtension = (!categoryExtension.contains(".") ? "." : "") + categoryExtension;

        DataStorage config = plugin.getConfigManager().getStorage(FilePath.language, "config.yml");

        this.availableLocales = plugin.getConfigManager().getValue(config, "available-locales", new ArrayList<>(){{add("en");}});
        this.defaultLanguage = this.getLanguage(plugin.getConfigManager().getValue(config, "default-language", "en"));
        this.alwaysDefaultLanguage = plugin.getConfigManager().getValue(config, "always-default-language", false);
        this.hoverMessageBounded = plugin.getConfigManager().getValue(config, "hover-message-bounded", "%hm%");

        // aici trebuie sa adaug toate categoriile pentru fiecare locale, si continutul acelei categorii(stringuri si liste de string)

        this.messageManager = new MessageManager(plugin, config);

        config.save();
    }

    @Override
    public Minigame getPlugin() {
        return this.plugin;
    }

    public MessageManager getMessageManager() {
        return this.messageManager;
    }

    public List<Class<? extends LanguagePath>> getCategories() {
        return this.languageCategories;
    }

    public int getIndex(LanguagePath languagePath) {
        for (int i = 0; i < this.languageCategories.size(); i++) {
            if(this.languageCategories.get(i) == languagePath.getClass()) {
                return i;
            }
        }

        return 0;
    }

    public Class<? extends LanguagePath> getCategory(String id) {
        Class<? extends LanguagePath> category;
        for(int i = 0; i < this.getCategories().size(); i++) {
            category = this.getCategories().get(i);
            if(LanguagePath.getLanguageId(category).equals(id)) {
                return category;
            }
        }
        return null;
    }

    public Language getLanguage(String locale) {
        if(locale == null || !this.availableLocales.contains(locale)) {
            return this.defaultLanguage;
        }

        Language language = this.registeredLanguages.get(locale);
        if(language == null) {
            language = new GeneralLanguage(plugin, this, locale);
            this.registeredLanguages.put(locale, language);
        }

        return language;
    }

    public Language getDefaultLanguage() {
        return this.defaultLanguage;
    }

    public String getCategoryExtension() {
        return this.categoryExtension;
    }
}