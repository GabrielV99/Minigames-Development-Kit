package ro.Gabriel.User;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import ro.Gabriel.Language.LanguageCategory;
import ro.Gabriel.Messages.MessageSender;
import ro.Gabriel.Repository.BaseEntity;
import ro.Gabriel.Messages.MessageUtils;
import ro.Gabriel.Titles.TitleSender;
import ro.Gabriel.Language.Language;
import ro.Gabriel.Main.Minigame;

import java.util.UUID;

public class SpigotUser extends BaseEntity<UUID> implements MessageSender, TitleSender {
    private Player player;

    protected Language language;

    private final Minigame minigame;

    /*public SpigotUser(Minigame minigame, Player player, String locale) {
        this(minigame, player);
        this.language = minigame.getLanguageManager().getLanguage(locale);
    }

    public SpigotUser(Minigame minigame, Player player) {
        super(player != null ? player.getUniqueId() : UUID.randomUUID());
        this.player = player;
        this.minigame = minigame;
    }*/

    public SpigotUser(Minigame minigame, UUID uuid, String locale) {
        super(uuid);
        this.minigame = minigame;
        this.language = minigame.getLanguageManager().getLanguage(locale);
        this.getPlayer().sendMessage("ID: "  + uuid);
    }

    public SpigotUser(Minigame minigame, UUID uuid) {
        this(minigame, uuid, null);

        System.out.println("PLAYERRRRRRRR");
    }

    public Player getPlayer() {
        return this.player == null ? (this.getId() != null ? (this.player = Bukkit.getPlayer(this.getId())) : null) : this.player;
    }

    @Override
    public void sendMessage(String message) {
        this.getPlayer().sendMessage(message);
    }

    @Override
    public void sendMessage(LanguageCategory category, Object replacement) {
        Object message = minigame.getLanguageManager().getMessageManager().buildMessage(category, language, replacement);
        this.sendMessage(message);
    }

    @Override
    public void sendMessage(LanguageCategory category) {
        this.sendMessage(category, this);
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
    public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        this.getPlayer().sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }
}