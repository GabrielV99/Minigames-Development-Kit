package ro.Gabriel.Language.Impl;

import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;
import ro.Gabriel.Language.LanguageCategory;
import ro.Gabriel.Language.LanguageKey;
import ro.Gabriel.Language.Language;

import java.util.ArrayList;
import java.util.List;

public class FailedLanguage extends Language {

    public FailedLanguage() {
        super("failed");
    }

    @Override
    public Object get(LanguageKey key, LanguageCategory category) {
        return new Object();
    }

    @Override
    public String getString(LanguageKey key, LanguageCategory category) {
        return DataDefaultValues.get(String.class);
    }

    @Override
    public String getString(String path, LanguageCategory category) {
        return DataDefaultValues.get(String.class);
    }

    @Override
    public List<String> getStringList(String path) {
        return new ArrayList<>();
    }
}