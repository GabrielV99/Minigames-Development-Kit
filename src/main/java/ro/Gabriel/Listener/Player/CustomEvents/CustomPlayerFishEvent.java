package ro.Gabriel.Listener.Player.CustomEvents;

import ro.Gabriel.Listener.Player.CustomPlayerEvent;
import ro.Gabriel.User.SpigotUser;

import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Fish;

public class CustomPlayerFishEvent<PlayerType extends SpigotUser> extends CustomPlayerEvent<PlayerType> implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final Entity entity;
    private boolean cancel = false;
    private int exp;
    private final PlayerFishEvent.State state;
    private final Fish hookEntity;

    public CustomPlayerFishEvent(Player player, PlayerType spigotUser, Entity entity, Fish hookEntity, PlayerFishEvent.State state, int exp) {
        super(player, spigotUser);
        this.entity = entity;
        this.hookEntity = hookEntity;
        this.state = state;
        this.exp = exp;
    }

    /**
     * Gets the entity caught by the player.
     * <p>
     * If player has fished successfully, the result may be cast to {@link
     * org.bukkit.entity.Item}.
     *
     * @return Entity caught by the player, Entity if fishing, and null if
     *     bobber has gotten stuck in the ground or nothing has been caught
     */
    public Entity getCaught() {
        return entity;
    }

    /**
     * Gets the fishing hook.
     *
     * @return Fish the entity representing the fishing hook/bobber.
     */
    public Fish getHook() {
        return hookEntity;
    }

    /**
     * Gets the amount of experience received when fishing.
     * <p>
     * Note: This value has no default effect unless the event state is {@link
     * PlayerFishEvent.State#CAUGHT_FISH}.
     *
     * @return the amount of experience to drop
     */
    public int getExpToDrop() {
        return exp;
    }

    /**
     * Sets the amount of experience received when fishing.
     * <p>
     * Note: This value has no default effect unless the event state is {@link
     * PlayerFishEvent.State#CAUGHT_FISH}.
     *
     * @param amount the amount of experience to drop
     */
    public void setExpToDrop(int amount) {
        exp = amount;
    }

    /**
     * Gets the state of the fishing
     *
     * @return A State detailing the state of the fishing
     */
    public PlayerFishEvent.State getState() {
        return state;
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