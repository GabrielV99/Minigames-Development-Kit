package ro.Gabriel.Language.Categories;

import ro.Gabriel.Language.Impl.GeneralLanguage;
import ro.Gabriel.Language.LanguageCategory;
import ro.Gabriel.Language.Language;

public enum LanguageCategoryType implements LanguageCategory {
    GUI("Gui"),
    COMMANDS("commands");

    String storagePath;

    LanguageCategoryType(String storagePath) {
        this.storagePath = storagePath;
    }

    @Override
    public String getStoragePath() {
        return storagePath;
    }
}