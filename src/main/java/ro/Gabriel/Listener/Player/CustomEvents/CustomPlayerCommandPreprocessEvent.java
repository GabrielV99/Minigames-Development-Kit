package ro.Gabriel.Listener.Player.CustomEvents;

import org.apache.commons.lang.Validate;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;

import ro.Gabriel.Listener.Player.CustomPlayerEvent;
import ro.Gabriel.User.SpigotUser;

import java.util.Set;

public class CustomPlayerCommandPreprocessEvent<PlayerType extends SpigotUser> extends CustomPlayerEvent<PlayerType> implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean cancel = false;
    private String message;
    private final Set<Player> recipients;

    public CustomPlayerCommandPreprocessEvent(Player player, PlayerType spigotUser, String message, Set<Player> recipients) {
        super(player, spigotUser);

        this.message = message;
        this.recipients = recipients;
    }

    /**
     * Gets the command that the player is attempting to send.
     * <p>
     * All commands begin with a special character; implementations do not
     * consider the first character when executing the content.
     *
     * @return Message the player is attempting to send
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the command that the player will send.
     * <p>
     * All commands begin with a special character; implementations do not
     * consider the first character when executing the content.
     *
     * @param command New message that the player will send
     * @throws IllegalArgumentException if command is null or empty
     */
    public void setMessage(String command) throws IllegalArgumentException {
        Validate.notNull(command, "Command cannot be null");
        Validate.notEmpty(command, "Command cannot be empty");
        this.message = command;
    }

    /**
     * Sets the player that this command will be executed as.
     *
     * @param player New player which this event will execute as
     * @throws IllegalArgumentException if the player provided is null
     */
    public void setPlayer(final Player player) throws IllegalArgumentException {
        Validate.notNull(player, "Player cannot be null");
        this.player = player;
    }

    /**
     * Gets a set of recipients that this chat message will be displayed to.
     * <p>
     * The set returned is not guaranteed to be mutable and may auto-populate
     * on access. Any listener accessing the returned set should be aware that
     * it may reduce performance for a lazy set implementation. Listeners
     * should be aware that modifying the list may throw {@link
     * UnsupportedOperationException} if the event caller provides an
     * unmodifiable set.
     *
     * @deprecated This method is provided for backward compatibility with no
     *     guarantee to the effect of viewing or modifying the set.
     * @return All Players who will see this chat message
     */
    @Deprecated
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