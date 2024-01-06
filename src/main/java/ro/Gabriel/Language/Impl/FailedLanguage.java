package ro.Gabriel.Language.Impl;

import ro.Gabriel.Language.Language;
import ro.Gabriel.Language.LanguageCategory;
import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;

import java.util.ArrayList;
import java.util.List;

public class FailedLanguage extends Language {

    public FailedLanguage() {
        super("failed");
    }

    @Override
    public Object get(LanguageCategory category) {
        return new Object();
    }

    @Override
    public String getString(LanguageCategory category) {
        return DataDefaultValues.get(String.class);
    }

    @Override
    public List<String> getStringList(LanguageCategory category) {
        return new ArrayList<>();
    }
}