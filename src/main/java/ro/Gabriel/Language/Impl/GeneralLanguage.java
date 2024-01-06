package ro.Gabriel.Language.Impl;

import ro.Gabriel.Class.ClassUtils;
import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Language.LanguageCategory;
import ro.Gabriel.Language.LanguageManager;
import ro.Gabriel.Main.Config.FilePath;
import ro.Gabriel.Language.Language;
import ro.Gabriel.Main.Minigame;

import java.util.ArrayList;
import java.util.List;

public class GeneralLanguage extends Language {

    private final LanguageManager languageManager;

    protected DataStorage[] categories;

    //pentru partea de Gui atunci cand ele se creeaza si trebuie sa extragi toate numele itemelor titlurile inventarelor, etc,
    // acestea nu trebuie extrease cu valori ale enumerarilor adica eu nu o sa creez o enumerare si sa adaug in ea cate o valoarea
    // pentru fiecare item dintr-un inventar, deoarece itemele dintr-un inventar poate varia. Ca sa pot totusi ca extrac toate numele
    /*acest DataStorage se va referi la fisierul de unde sistemul de Gui isi va extrage toate titlurile pentru itemele inventarelor,
    titlurile de inventare etc, deoarece toate stringurile sau listele de stringuri se voi extrage dupa id-ul path-ului din fisier
    automat cand gui-ul se va creea*/
    private DataStorage gui;

    public GeneralLanguage(Minigame minigame, LanguageManager languageManager, String locale) {
        super(locale);

        this.languageManager = languageManager;
        this.categories = new DataStorage[languageManager.getCategories().size()];

        for(int i = 0; i < categories.length; i++) {
            this.categories[i] = minigame.getConfigManager().getStorage(
                    FilePath.language,
                    locale + "\\" + LanguageCategory.getLanguageId(languageManager.getCategories().get(i)) + languageManager.getCategoryExtension()
            );
        }
    }

    @Override
    public Object get(LanguageCategory category) {
        int index = this.languageManager.getIndex(category);
        if(this.categories.length > index) {
            DataStorage data = this.categories[this.languageManager.getIndex(category)];
            return data != null ? data.get(category.getPath()) : DataDefaultValues.get(String.class);
        } else {
            return "";
        }
    }

    @Override
    public String getString(LanguageCategory category) {
        Object value = this.get(category);
        return value instanceof String ? (String) value : DataDefaultValues.get(String.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getStringList(LanguageCategory category) {
        Object value = this.get(category);
        return value instanceof List ? (List<String>) value : new ArrayList<>();
    }
}