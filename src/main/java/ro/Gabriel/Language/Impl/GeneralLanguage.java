package ro.Gabriel.Language.Impl;

import ro.Gabriel.Language.LanguageCategory;
import ro.Gabriel.Messages.MessageUtils;
import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Language.LanguageManager;
import ro.Gabriel.Main.Config.FilePath;
import ro.Gabriel.Language.Language;
import ro.Gabriel.Main.Minigame;

import java.util.*;

public class GeneralLanguage extends Language {

    private final LanguageManager languageManager;

    private final Map<String, DataStorage> categories;
    private final Map<String, String> strings;
    private final Map<String, List<String>> stringLists;

    //pentru partea de Gui atunci cand ele se creeaza si trebuie sa extragi toate numele itemelor titlurile inventarelor, etc,
    // acestea nu trebuie extrease cu valori ale enumerarilor adica eu nu o sa creez o enumerare si sa adaug in ea cate o valoarea
    // pentru fiecare item dintr-un inventar, deoarece itemele dintr-un inventar poate varia. Ca sa pot totusi ca extrac toate numele
    /*acest DataStorage se va referi la fisierul de unde sistemul de Gui isi va extrage toate titlurile pentru itemele inventarelor,
    titlurile de inventare etc, deoarece toate stringurile sau listele de stringuri se voi extrage dupa id-ul path-ului din fisier
    automat cand gui-ul se va creea*/
    //private LanguageCategory gui, commands;

    public GeneralLanguage(Minigame plugin, LanguageManager languageManager, String locale) {
        super(plugin, locale);

        this.languageManager = languageManager;
        this.categories = new HashMap<>(languageManager.getCategories().size());
        this.strings = new HashMap<>();
        this.stringLists = new HashMap<>();

        languageManager.getCategories().forEach(category -> {
            this.categories.put(category.getStoragePath(), plugin.getConfigManager().getStorage(FilePath.language,
                    locale + "\\" + category.getStoragePath() + languageManager.getCategoryExtension()));
        });
    }

    @Override
    public String getString(LanguageCategory category, String path) {
        String value = this.strings.get(path);
        if(value == null) {
            DataStorage storage = this.categories.get(category.getStoragePath());
            if(storage != null) {
                Object relativeValue = storage.get(path);
                value = MessageUtils.colorString(this.getPlugin(), relativeValue instanceof List ? MessageUtils.listToString(relativeValue) : ((String) relativeValue));
            }

            if(value == null) {
                value = DataDefaultValues.get(String.class);
            }

            this.strings.put(path, value);
        }
        return value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getStringList(LanguageCategory category, String path) {
        List<String> value = this.stringLists.get(path);
        if(value == null) {
            DataStorage storage = this.categories.get(category.getStoragePath());
            if(storage != null) {
                Object relativeValue = storage.get(path);
                value = relativeValue instanceof List
                        ? (List<String>)relativeValue
                        : new ArrayList<>(){{add(relativeValue instanceof String ? (String)relativeValue : DataDefaultValues.get(String.class));}};
            }

            if(value == null) {
                value = new ArrayList<>();
            }

            this.stringLists.put(path, MessageUtils.colorStringList(this.getPlugin(), value));
        }
        return value;
    }
}