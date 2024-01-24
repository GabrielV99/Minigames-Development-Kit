package ro.Gabriel.User;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import ro.Gabriel.Language.LanguagePath;
import ro.Gabriel.Main.IPluginInstance;
import ro.Gabriel.Placeholder.Placeholder;
import ro.Gabriel.Repository.BaseEntity;
import ro.Gabriel.Messages.MessageUtils;
import ro.Gabriel.Language.Language;
import ro.Gabriel.Main.Minigame;

import java.util.UUID;

public class SpigotUser extends BaseEntity<UUID> implements MinecraftUser, IPluginInstance {
    private final Minigame plugin;

    private Player player;

    protected Language language;

    public SpigotUser(Minigame minigame, UUID uuid, String locale) {
        super(uuid);
        this.plugin = minigame;
        this.language = minigame.getLanguageManager().getLanguage(locale);
    }

    public SpigotUser(Minigame minigame, UUID uuid) {
        this(minigame, uuid, null);
    }

    @Override
    public Minigame getPlugin() {
        return this.plugin;
    }

    @Override
    public final Player getPlayer() {
        return this.player == null ? (this.getId() != null ? (this.player = Bukkit.getPlayer(this.getId())) : null) : this.player;
    }

    @Override
    public final Language getLanguage() {
        return this.language;
    }

    @Override
    public final void setLanguage(Language language) {
        this.language = language;
    }

    public void changeLanguage(String locale) {
        this.language = this.plugin.getLanguageManager().getLanguage(locale);
    }

    @Override
    public final void sendMessage(String message) {
        this.getPlayer().sendMessage(message);
    }

    @Override
    public final void sendMessage(LanguagePath category, Object replacement) {
        this.sendMessage(category, replacement, this.language);
    }

    @Override
    public final void sendMessage(LanguagePath category) {
        this.sendMessage(category, this);
    }

    @Override
    public void sendMessage(LanguagePath category, Object replacement, Language language) {
        Object message = this.getPlugin().getLanguageManager().getMessageManager().buildMessage(category, language, replacement);
        this.sendMessage(message);
    }

    @Override
    public void sendMessage(LanguagePath category, Language language) {
        this.sendMessage(category, this, language);
    }

    private void sendMessage(Object message) {
        if(message == null)	{
            return;
        }

        if(message instanceof String) {
            this.sendMessage((String) message);
        } else if(message instanceof TextComponent) {
            if(this.getPlayer() != null) {
                this.getPlayer().spigot().sendMessage((TextComponent) message);
            } else {
                this.sendMessage(MessageUtils.textComponentToString((TextComponent)message));
            }
        }
    }

    @Override
    public final void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        this.getPlayer().sendTitle(
                MessageUtils.colorString(this.getPlugin(), title),
                MessageUtils.colorString(this.getPlugin(), subtitle),
                fadeIn,
                stay,
                fadeOut
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void sendTitle(LanguagePath title, LanguagePath subtitle, Object replacement, int stay) {
        if(language != null) {
            String titleText = "", subtitleText = "";
            if(title != null) {
                titleText = title.getPlaceholder() != null && replacement != null
                        ? ((Placeholder<Object>)title.getPlaceholder()).makeReplace(this.language.getString(title), replacement)
                        : this.language.getString(title);
            }

            if(subtitle != null) {
                subtitleText = subtitle.getPlaceholder() != null && replacement != null
                        ? ((Placeholder<Object>)subtitle.getPlaceholder()).makeReplace(this.language.getString(subtitle), replacement)
                        : this.language.getString(subtitle);
            }
            this.sendTitle(titleText, subtitleText, 10, stay, 20);
        }
    }

    @Override
    public final boolean hasPermission(String permission) {
        return this.player.hasPermission(permission);
    }
}