package ro.Gabriel.Language.Impl;

import ro.Gabriel.BuildBattle.Main;
import ro.Gabriel.Language.Language;
import ro.Gabriel.Language.LanguageCategory;
import ro.Gabriel.Language.LanguageKey;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;

import java.util.List;

public class GeneralLanguage extends Language {

    private DataStorage messages, gui, scoreboard, leaderboard, commands;

    public GeneralLanguage(String locale) throws Exception {
        super(locale);
        Main.log("s-a creat language-ul: &c&l" + locale);

        messages = DataStorage.getStorage("Languages/" + locale + "/" + LanguageCategory.MESSAGES.getPath() + ".yml", true);

        if(messages.isFailed()) {
            throw new Exception();
        }


    }

    @Override
    public Object get(LanguageKey key, LanguageCategory category) {
        switch (category) {
            case MESSAGES: {
                return messages.get(key.getId());
            }
            case GUI: {
                return gui.get(key.getId());
            }
            case SCOREBOARD: {
                return scoreboard.get(key.getId());
            }
            case LEADERBOARD: {
                return leaderboard.get(key.getId());
            }
            case COMMANDS: {
                return commands.get(key.getId());
            }
        }

        return null;
    }

    @Override
    public String getString(LanguageKey key, LanguageCategory category) {
        Object object = get(key, category);
        return object instanceof String ? (String)object : DataDefaultValues.get(String.class);
    }

    @Override
    public String getString(String path, LanguageCategory category) {
        switch (category) {
            case MESSAGES: {
                return messages.getString(path);
            }
            case GUI: {
                return gui.getString(path);
            }
            case SCOREBOARD: {
                return scoreboard.getString(path);
            }
            case LEADERBOARD: {
                return leaderboard.getString(path);
            }
            case COMMANDS: {
                return commands.getString(path);
            }
        }
        return DataDefaultValues.get(String.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getStringList(String path) {
        return (List<String>) messages.get(path);
    }
}
