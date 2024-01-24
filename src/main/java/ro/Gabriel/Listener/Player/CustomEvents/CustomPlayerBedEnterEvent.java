package ro.Gabriel.Listener.Player.CustomEvents;

import ro.Gabriel.Listener.Player.CustomPlayerEvent;
import ro.Gabriel.User.SpigotUser;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;

public class CustomPlayerBedEnterEvent<PlayerType extends SpigotUser> extends CustomPlayerEvent<PlayerType> implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean cancel = false;
    private final Block bed;

    public CustomPlayerBedEnterEvent(Player player, PlayerType spigotUser, Block bed) {
        super(player, spigotUser);
        this.bed = bed;
    }

    /**
     * Returns the bed block involved in this event.
     *
     * @return the bed block involved in this event
     */
    public Block getBed() {
        return bed;
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
        return null;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}