package ro.Gabriel.Language.Impl;

import ro.Gabriel.Class.Annotations.ObjectId;
import ro.Gabriel.Language.Categories.CommandMessages;
import ro.Gabriel.Language.Categories.LanguageCategoryType;
import ro.Gabriel.Language.LanguageCategory;
import ro.Gabriel.Language.LanguagePath;
import ro.Gabriel.Placeholder.Placeholder;
import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Language.LanguageManager;
import ro.Gabriel.Main.Config.FilePath;
import ro.Gabriel.Language.Language;
import ro.Gabriel.Main.Minigame;

import java.util.ArrayList;
import java.util.List;

public class GeneralLanguage extends Language {

    private final LanguageManager languageManager;

    protected DataStorage[] categories;
    //Map<String, Object> paths;

    //pentru partea de Gui atunci cand ele se creeaza si trebuie sa extragi toate numele itemelor titlurile inventarelor, etc,
    // acestea nu trebuie extrease cu valori ale enumerarilor adica eu nu o sa creez o enumerare si sa adaug in ea cate o valoarea
    // pentru fiecare item dintr-un inventar, deoarece itemele dintr-un inventar poate varia. Ca sa pot totusi ca extrac toate numele
    /*acest DataStorage se va referi la fisierul de unde sistemul de Gui isi va extrage toate titlurile pentru itemele inventarelor,
    titlurile de inventare etc, deoarece toate stringurile sau listele de stringuri se voi extrage dupa id-ul path-ului din fisier
    automat cand gui-ul se va creea*/
    private LanguageCategory gui, commands;

    public GeneralLanguage(Minigame minigame, LanguageManager languageManager, String locale) {
        super(locale);

        this.languageManager = languageManager;
        this.categories = new DataStorage[languageManager.getCategories().size() + 1];

        for(int i = 0; i < categories.length - 1; i++) {
            this.categories[i] = minigame.getConfigManager().getStorage(
                    FilePath.language,
                    locale + "\\" + LanguagePath.getLanguageId(languageManager.getCategories().get(i)) + languageManager.getCategoryExtension()
            );
        }

        this.commands = new LanguageCategory() {
            final DataStorage commands = minigame.getConfigManager().getStorage(FilePath.language,
                    locale + "\\commands" + languageManager.getCategoryExtension());
            @Override
            public DataStorage getStorage() {
                return commands;
            }
        };
        this.categories[this.categories.length - 1] = this.commands.getStorage();

    }

    @Override
    public Object get(LanguagePath path) {

        path.getClass().isEnum();

        int index = this.languageManager.getIndex(path);
        if(this.categories.length > index) {
            DataStorage data = this.categories[index];
            return data != null ? data.get(path.getPath()) : DataDefaultValues.get(String.class);
        } else {
            return "";
        }
    }

    @Override
    public Object get(LanguageCategoryType category, String path) {
        Object value = category.getCategory(this).getStorage().get(path);
        return value != null ? value : DataDefaultValues.get(String.class);
    }

    @Override
    public String getString(LanguagePath path) {
        Object value = this.get(path);
        return value instanceof String ? (String) value : DataDefaultValues.get(String.class);
    }

    @Override
    public String getString(LanguageCategoryType category, String path) {
        Object value = this.get(category, path);
        return value instanceof String ? (String) value : DataDefaultValues.get(String.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getStringList(LanguagePath path) {
        Object value = this.get(path);
        return value instanceof List ? (List<String>) value : new ArrayList<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getStringList(LanguageCategoryType category, String path) {
        Object value = this.get(category, path);
        return value instanceof List ? (List<String>) value : new ArrayList<>();
    }

    public LanguageCategory getCommands() {
        int index = this.languageManager.getIndex(CommandMessages.COMMAND_ACCESSED_BY);

        return this.commands;
    }

    public LanguageCategory getGui() {
        return this.gui;
    }
}