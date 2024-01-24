package ro.Gabriel.Language.Impl;

import ro.Gabriel.Language.Categories.LanguageCategoryType;
import ro.Gabriel.Language.LanguagePath;
import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;
import ro.Gabriel.Language.Language;

import java.util.ArrayList;
import java.util.List;

public class FailedLanguage extends Language {

    public FailedLanguage() {
        super("failed");
    }

    @Override
    public Object get(LanguagePath path) {
        return new Object();
    }

    @Override
    public Object get(LanguageCategoryType category, String path) {
        return new Object();
    }

    @Override
    public String getString(LanguagePath path) {
        return DataDefaultValues.get(String.class);
    }

    @Override
    public String getString(LanguageCategoryType category, String path) {
        return DataDefaultValues.get(String.class);
    }

    @Override
    public List<String> getStringList(LanguagePath path) {
        return new ArrayList<>();
    }

    @Override
    public List<String> getStringList(LanguageCategoryType category, String path) {
        return new ArrayList<>();
    }
}