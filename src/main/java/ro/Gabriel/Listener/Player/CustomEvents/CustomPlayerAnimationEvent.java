package ro.Gabriel.Listener.Player.CustomEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerAnimationType;
import ro.Gabriel.Listener.Player.CustomPlayerEvent;
import ro.Gabriel.User.SpigotUser;

public class CustomPlayerAnimationEvent<PlayerType extends SpigotUser> extends CustomPlayerEvent<PlayerType> implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final PlayerAnimationType animationType;
    private boolean cancel = false;

    public CustomPlayerAnimationEvent(Player player, PlayerType spigotUser, PlayerAnimationType animationType) {
        super(player, spigotUser);
        this.animationType = animationType;
    }

    public CustomPlayerAnimationEvent(Player player, PlayerType spigotUser) {
        super(player, spigotUser);
        this.animationType = PlayerAnimationType.ARM_SWING;
    }

    /**
     * Get the type of this animation event
     *
     * @return the animation type
     */
    public PlayerAnimationType getAnimationType() {
        return animationType;
    }

    @Override
    public boolean isCancelled() {
        return this.cancel;
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