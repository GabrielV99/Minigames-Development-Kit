package ro.Gabriel.Language;

import ro.Gabriel.Main.IPluginInstance;
import ro.Gabriel.Main.Minigame;

import java.util.List;

public abstract class Language implements IPluginInstance {

    private final Minigame plugin;
    private final String locale;

    public Language(Minigame plugin, String locale) {
        this.plugin = plugin;
        this.locale = locale;
    }

    //public abstract Object get(LanguagePath path);
    //public abstract Object get(LanguageCategory category, String path);//

    public String getString(LanguagePath path) {
        return this.getString(path, path.getId());
    }
    public abstract String getString(LanguageCategory category, String path);

    public List<String> getStringList(LanguagePath path) {
        return this.getStringList(path, path.getId());
    }
    public abstract List<String> getStringList(LanguageCategory category, String path);//

    public String getLocale() {
        return this.locale;
    }

    @Override
    public Minigame getPlugin() {
        return plugin;
    }
}