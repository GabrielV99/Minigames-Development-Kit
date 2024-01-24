package ro.Gabriel.Listener.Player.CustomEvents;

import ro.Gabriel.Listener.Player.CustomPlayerEvent;
import ro.Gabriel.User.SpigotUser;

import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;

public class CustomPlayerChannelEvent<PlayerType extends SpigotUser> extends CustomPlayerEvent<PlayerType> {
    private static final HandlerList handlers = new HandlerList();

    private final String channel;

    public CustomPlayerChannelEvent(Player player, PlayerType spigotUser, String channel) {
        super(player, spigotUser);
        this.channel = channel;
    }

    public final String getChannel() {
        return channel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}