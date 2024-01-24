package ro.Gabriel.Listener.Player.CustomEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import ro.Gabriel.Listener.Player.CustomPlayerEvent;
import ro.Gabriel.User.SpigotUser;

public class CustomPlayerJoinEvent<PlayerType extends SpigotUser> extends CustomPlayerEvent<PlayerType> {
    private static final HandlerList handlers = new HandlerList();

    private String joinMessage;

    public CustomPlayerJoinEvent(Player player, PlayerType spigotUser, String joinMessage) {
        super(player, spigotUser);
        this.joinMessage = joinMessage;
    }

    /**
     * Gets the join message to send to all online players
     *
     * @return string join message
     */
    public String getJoinMessage() {
        return this.joinMessage;
    }

    /**
     * Sets the join message to send to all online players
     *
     * @param joinMessage join message
     */
    public void setJoinMessage(String joinMessage) {
        this.joinMessage = joinMessage;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}