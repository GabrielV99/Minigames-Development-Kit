package ro.Gabriel.Language;

import java.util.List;

public abstract class Language {// implements ICustomMessages, ICustomTitles, IHoverMessages, IActionBar

    private final String locale;

    public Language(String locale) {
        this.locale = locale;
    }

    public abstract Object get(LanguageKey key, LanguageCategory category);

    public abstract String getString(LanguageKey key, LanguageCategory category);

    public abstract String getString(String path, LanguageCategory category);

    public abstract List<String> getStringList(String path);

    public String getLocale() {
        return this.locale;
    }
}