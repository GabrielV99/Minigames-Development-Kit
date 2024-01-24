package ro.Gabriel.Listener.Player.CustomEvents;

import ro.Gabriel.Listener.Player.CustomPlayerEvent;
import ro.Gabriel.User.SpigotUser;

import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;
import org.bukkit.World;

public class CustomPlayerChangedWorldEvent<PlayerType extends SpigotUser> extends CustomPlayerEvent<PlayerType> {
    private static final HandlerList handlers = new HandlerList();

    private final World from;

    public CustomPlayerChangedWorldEvent(Player player, PlayerType spigotUser, World from) {
        super(player, spigotUser);
        this.from = from;
    }

    /**
     * Gets the world the player is switching from.
     *
     * @return  player's previous world
     */
    public World getFrom() {
        return from;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}