package ro.Gabriel.Language;

import ro.Gabriel.Language.Categories.LanguageCategoryType;

import java.util.List;

public abstract class Language {
    private final String locale;

    public Language(String locale) {
        this.locale = locale;
    }

    public abstract Object get(LanguagePath path);
    public abstract Object get(LanguageCategoryType category, String path);//

    public abstract String getString(LanguagePath path);
    public abstract String getString(LanguageCategoryType category, String path);//

    public abstract List<String> getStringList(LanguagePath path);
    public abstract List<String> getStringList(LanguageCategoryType category, String path);//

    public String getLocale() {
        return this.locale;
    }
}