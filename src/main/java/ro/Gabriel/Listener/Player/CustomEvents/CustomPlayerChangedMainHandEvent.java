package ro.Gabriel.Listener.Player.CustomEvents;

import ro.Gabriel.Listener.Player.CustomPlayerEvent;
import ro.Gabriel.User.SpigotUser;

import org.bukkit.inventory.MainHand;
import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;

public class CustomPlayerChangedMainHandEvent<PlayerType extends SpigotUser> extends CustomPlayerEvent<PlayerType> {
    private static final HandlerList handlers = new HandlerList();

    private final MainHand mainHand;

    public CustomPlayerChangedMainHandEvent(Player player, PlayerType spigotUser, MainHand mainHand) {
        super(player, spigotUser);

        this.mainHand = mainHand;
    }

    /**
     * Gets the new main hand of the player. The old hand is still momentarily
     * available via {@link Player#getMainHand()}.
     *
     * @return the new {@link MainHand} of the player
     */
    public MainHand getMainHand() {
        return mainHand;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}