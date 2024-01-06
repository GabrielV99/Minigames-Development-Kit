package ro.Gabriel.Language;

import ro.Gabriel.Class.Validators.LanguageCategoryValidator;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Language.Impl.GeneralLanguage;
import ro.Gabriel.Messages.MessageManager;
import ro.Gabriel.Main.Config.FilePath;
import ro.Gabriel.Managers.Manager;
import ro.Gabriel.Main.Minigame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageManager implements Manager {

    private final Minigame minigame;
    private final Map<String, Language> languages;
    private final List<Class<? extends LanguageCategory>> languageCategories;

    private final MessageManager messageManager;

    private final Language defaultLanguage;
    private final boolean alwaysDefaultLanguage;
    private final String hoverMessageBounded;

    private String categoryExtension = ".yml";

    public LanguageManager(Minigame minigame) {
        this.minigame = minigame;
        this.languages = new HashMap<>();
        this.languageCategories = minigame.searchClasses(LanguageCategoryValidator.class, LanguageCategory.class);

        this.categoryExtension = (!categoryExtension.contains(".") ? "." : "") + categoryExtension;

        DataStorage config = minigame.getConfigManager().getStorage(FilePath.language, "config.yml");

        this.defaultLanguage = this.getLanguage(minigame.getConfigManager().getValue(config, "default-language", "en"));
        this.alwaysDefaultLanguage = minigame.getConfigManager().getValue(config, "always-default-language", false);
        this.hoverMessageBounded = minigame.getConfigManager().getValue(config, "hover-message-bounded", "%hm%");

        this.messageManager = new MessageManager(minigame, config);

        config.save();


    }

    @Override
    public Minigame getMainInstance() {
        return this.minigame;
    }

    public MessageManager getMessageManager() {
        return this.messageManager;
    }

    public List<Class<? extends LanguageCategory>> getCategories() {
        return this.languageCategories;
    }

    public int getIndex(LanguageCategory languageCategory) {
        for (int i = 0; i < this.languageCategories.size(); i++) {
            if(this.languageCategories.get(i) == languageCategory.getClass()) {
                return i;
            }
        }
        return 0;
    }

    public Class<? extends LanguageCategory> getCategory(String id) {
        Class<? extends LanguageCategory> category;
        for(int i = 0; i < this.getCategories().size(); i++) {
            category = this.getCategories().get(i);
            if(LanguageCategory.getLanguageId(category).equals(id)) {
                return category;
            }
        }
        return null;
    }

    public Language getLanguage(String locale) {
        if(locale == null) {
            return this.defaultLanguage;
        }

        Language language = this.languages.get(locale);
        if(language == null) {
            language = new GeneralLanguage(minigame, this, locale);
            this.languages.put(locale, language);
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