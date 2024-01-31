package ro.Gabriel.Language;

import ro.Gabriel.Class.ClassScanner;
import ro.Gabriel.Class.ClassUtils;
import ro.Gabriel.Class.Validators.LanguageCategoryValidator;
import ro.Gabriel.Placeholder.Placeholder;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Language.Impl.GeneralLanguage;
import ro.Gabriel.Messages.MessageManager;
import ro.Gabriel.Main.Config.FilePath;
import ro.Gabriel.Managers.Manager;
import ro.Gabriel.Main.Minigame;
import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LanguageManager implements Manager {

    private final Minigame plugin;
    private final Map<String, Language> registeredLanguages;
    private List<LanguageCategory> languageCategories;

    private final MessageManager messageManager;

    private final Language defaultLanguage;
    private final boolean alwaysDefaultLanguage;
    private final String hoverMessageBounded;

    private final List<String> availableLocales;

    private String categoryExtension = ".yml";

    public LanguageManager(Minigame plugin) {
        this.plugin = plugin;
        this.registeredLanguages = new HashMap<>();
        this.languageCategories = new ArrayList<>();

        ClassScanner.getAllTypeClassesByPlugin(plugin, clazz -> clazz.isEnum() && clazz.getEnumConstants().length > 0, LanguagePath.class)
                .forEach(clazz -> languageCategories.add(clazz.getEnumConstants()[0]));

        ClassScanner.getAllTypeClassesByPlugin(plugin, clazz -> clazz.isEnum() && clazz.getEnumConstants().length > 0, LanguageCategory.class)
                .forEach(clazz -> languageCategories.addAll(Arrays.asList(clazz.getEnumConstants())));

        this.languageCategories = new ArrayList<>(languageCategories.stream()
                .collect(Collectors.toMap(LanguageCategory::getStoragePath, Function.identity(), (existing, replacement) -> existing))
                .values());

        this.categoryExtension = (!categoryExtension.contains(".") ? "." : "") + categoryExtension;

        DataStorage config = plugin.getConfigManager().getStorage(FilePath.language, "config.yml");

        this.availableLocales = plugin.getConfigManager().getValue(config, "available-locales", new ArrayList<>(){{add("en");}});
        this.defaultLanguage = this.getLanguage(plugin.getConfigManager().getValue(config, "default-language", "en"));
        this.alwaysDefaultLanguage = plugin.getConfigManager().getValue(config, "always-default-language", false);
        this.hoverMessageBounded = plugin.getConfigManager().getValue(config, "hover-message-bounded", "%hm%");

        // aici trebuie sa adaug in fisiere toate categoriile pentru fiecare locale, si continutul acelei categorii(stringuri si liste de string)
        this.languageCategories.forEach(category -> {

        });

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

    public List<LanguageCategory> getCategories() {
        return this.languageCategories;
    }

    public LanguageCategory getCategory(String id) {
        for (LanguageCategory languageCategory : this.languageCategories) {
            if (languageCategory.getId().equals(id)) {
                return languageCategory;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public LanguagePath getPath(LanguageCategory category, String path) {
        LanguagePath languagePath = null;

        if(category instanceof LanguagePath && ClassUtils.isEnum(category.getClass())) {
            languagePath = ClassUtils.getEnumValue(((Class<LanguagePath>)category.getClass()), path);
        } else {
            int count = 0;
            Collection<Language> registeredLanguages = this.registeredLanguages.values();
            for(Language language : registeredLanguages) {
                count += !language.getString(category, path).equals(DataDefaultValues.get(String.class)) ? 1 : 0;
            }

            if(count == registeredLanguages.size()) {
                languagePath = new LanguagePath() {
                    @Override
                    public String getPath() {
                        return path;
                    }

                    @Override
                    public Placeholder<?> getPlaceholder() { return null; }

                    @Override
                    public String getStoragePath() {
                        return category.getStoragePath();
                    }
                };
            }
        }

        return languagePath;
    }
    public LanguagePath getPath(String category, String path) {
        LanguageCategory languageCategory = this.getCategory(category);
        return languageCategory != null ? this.getPath(languageCategory, path) : null;
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