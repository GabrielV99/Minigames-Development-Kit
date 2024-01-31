package ro.Gabriel.Language.Impl;

import ro.Gabriel.Main.Minigame;
import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;
import ro.Gabriel.Language.LanguageCategory;
import ro.Gabriel.Language.LanguagePath;
import ro.Gabriel.Language.Language;

import java.util.ArrayList;
import java.util.List;

public class FailedLanguage extends Language {

    public FailedLanguage(Minigame plugin) {
        super(plugin, "failed");
    }

    @Override
    public String getString(LanguagePath path) {
        return DataDefaultValues.get(String.class);
    }

    @Override
    public String getString(LanguageCategory category, String path) {
        return DataDefaultValues.get(String.class);
    }

    @Override
    public List<String> getStringList(LanguagePath path) {
        return new ArrayList<>();
    }

    @Override
    public List<String> getStringList(LanguageCategory category, String path) {
        return new ArrayList<>();
    }
}