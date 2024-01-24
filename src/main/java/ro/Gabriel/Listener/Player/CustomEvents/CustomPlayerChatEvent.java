package ro.Gabriel.Listener.Player.CustomEvents;

import ro.Gabriel.Listener.Player.CustomPlayerEvent;
import ro.Gabriel.User.SpigotUser;

import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;

import org.apache.commons.lang.Validate;

import java.util.HashSet;
import java.util.Set;

/**
 * Holds information for player chat and commands
 *
 * @deprecated This event will fire from the main thread and allows the use of
 *     all of the Bukkit API, unlike the {@link AsyncPlayerChatEvent}.
 *     <p>
 *     Listening to this event forces chat to wait for the main thread which
 *     causes delays for chat. {@link AsyncPlayerChatEvent} is the encouraged
 *     alternative for thread safe implementations.
 */
@Deprecated
public class CustomPlayerChatEvent<PlayerType extends SpigotUser> extends CustomPlayerEvent<PlayerType> implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean cancel = false;
    private String message;
    private String format;
    private final Set<Player> recipients;

    public CustomPlayerChatEvent(Player player, PlayerType spigotUser, String message, String format, Set<Player> recipients) {
        super(player, spigotUser);
        this.message = message;
        this.format = format;
        this.recipients = recipients;
    }

    public CustomPlayerChatEvent(Player player, PlayerType spigotUser, String message) {
        this(player, spigotUser, message, "<%1$s> %2$s", new HashSet<>(player.getServer().getOnlinePlayers()));
    }

    /**
     * Gets the message that the player is attempting to send
     *
     * @return Message the player is attempting to send
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message that the player will send
     *
     * @param message New message that the player will send
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sets the player that this message will display as, or command will be
     * executed as
     *
     * @param player New player which this event will execute as
     */
    public void setPlayer(final Player player) {
        Validate.notNull(player, "Player cannot be null");
        this.player = player;
    }

    /**
     * Gets the format to use to display this chat message
     *
     * @return String.Format compatible format string
     */
    public String getFormat() {
        return format;
    }

    /**
     * Sets the format to use to display this chat message
     *
     * @param format String.Format compatible format string
     */
    public void setFormat(final String format) {
        // Oh for a better way to do this!
        try {
            String.format(format, player, message);
        } catch (RuntimeException ex) {
            ex.fillInStackTrace();
            throw ex;
        }

        this.format = format;
    }

    /**
     * Gets a set of recipients that this chat message will be displayed to
     *
     * @return All Players who will see this chat message
     */
    public Set<Player> getRecipients() {
        return recipients;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}