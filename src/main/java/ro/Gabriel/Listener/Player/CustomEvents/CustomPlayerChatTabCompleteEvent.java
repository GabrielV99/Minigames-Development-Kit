package ro.Gabriel.Listener.Player.CustomEvents;

import ro.Gabriel.Listener.Player.CustomPlayerEvent;
import ro.Gabriel.User.SpigotUser;

import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;

import java.util.Collection;

public class CustomPlayerChatTabCompleteEvent<PlayerType extends SpigotUser> extends CustomPlayerEvent<PlayerType> {
    private static final HandlerList handlers = new HandlerList();

    private final String message;
    private final String lastToken;
    private final Collection<String> completions;

    public CustomPlayerChatTabCompleteEvent(Player who, PlayerType spigotUser, String message, String lastToken, Collection<String> completions) {
        super(who, spigotUser);

        this.message = message;
        this.lastToken = lastToken;
        this.completions = completions;
    }

    /**
     * Gets the chat message being tab-completed.
     *
     * @return the chat message
     */
    public String getChatMessage() {
        return message;
    }

    /**
     * Gets the last 'token' of the message being tab-completed.
     * <p>
     * The token is the substring starting with the character after the last
     * space in the message.
     *
     * @return The last token for the chat message
     */
    public String getLastToken() {
        return lastToken;
    }

    /**
     * This is the collection of completions for this event.
     *
     * @return the current completions
     */
    public Collection<String> getTabCompletions() {
        return completions;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}