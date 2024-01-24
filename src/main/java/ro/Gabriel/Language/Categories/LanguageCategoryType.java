package ro.Gabriel.Language.Categories;

import ro.Gabriel.Language.Impl.GeneralLanguage;
import ro.Gabriel.Language.LanguageCategory;
import ro.Gabriel.Language.Language;

public enum LanguageCategoryType {
    GUI,
    COMMANDS;


    public LanguageCategory getCategory(Language language) {
        if(language instanceof GeneralLanguage) {
            switch (this) {
                case GUI: {
                    return ((GeneralLanguage)language).getGui();
                }
                case COMMANDS: {
                    return ((GeneralLanguage)language).getCommands();
                }
            }
        }

        return null;
    }
}