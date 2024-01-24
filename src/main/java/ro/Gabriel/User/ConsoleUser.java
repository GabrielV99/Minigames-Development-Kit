package ro.Gabriel.User;

import org.bukkit.entity.Player;

import ro.Gabriel.Language.LanguagePath;
import ro.Gabriel.Messages.MessageUtils;
import ro.Gabriel.Language.Language;
import ro.Gabriel.Main.Minigame;

public class ConsoleUser implements MinecraftUser {
    private  final Minigame plugin;

    public ConsoleUser(Minigame plugin) {
        this.plugin = plugin;
    }

    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
    public Language getLanguage() {
        return plugin.getLanguageManager().getDefaultLanguage();
    }

    @Override
    public void setLanguage(Language language) { }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public void sendMessage(String message) {
        plugin.getServer().getConsoleSender().sendMessage(MessageUtils.colorString(plugin.getConfigManager().getAltColorCode(), message));
    }

    @Override
    public void sendMessage(LanguagePath category, Object replacement) {
        this.sendMessage(category, replacement, this.getLanguage());
    }

    @Override
    public void sendMessage(LanguagePath category) {
        this.sendMessage(category, this);
    }

    @Override
    public void sendMessage(LanguagePath category, Object replacement, Language language) {
        Object message = plugin.getLanguageManager().getMessageManager().buildMessage(category, language, replacement);

        if(message instanceof String) {
            this.sendMessage((String) message);
        }
    }

    @Override
    public void sendMessage(LanguagePath category, Language language) {
        this.sendMessage(category, this, language);
    }

    @Override
    public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) { }

    @Override
    public void sendTitle(LanguagePath title, LanguagePath subtitle, Object replacement, int stay) { }
}