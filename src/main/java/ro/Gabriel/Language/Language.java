package ro.Gabriel.Language;

import java.util.List;

public abstract class Language {
    private final String locale;

    public Language(String locale) {
        this.locale = locale;
    }

    public abstract Object get(LanguageCategory category);

    public abstract String getString(LanguageCategory category);

    public abstract List<String> getStringList(LanguageCategory category);

    public String getLocale() {
        return this.locale;
    }
}