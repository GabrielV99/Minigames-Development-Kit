package ro.Gabriel.Listener.Player.CustomEvents;

import ro.Gabriel.Listener.Player.CustomPlayerEvent;
import ro.Gabriel.User.SpigotUser;

import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;

public class CustomPlayerExpChangeEvent<PlayerType extends SpigotUser> extends CustomPlayerEvent<PlayerType> {
    private static final HandlerList handlers = new HandlerList();

    private int exp;

    public CustomPlayerExpChangeEvent(Player player, PlayerType spigotUser, int exp) {
        super(player, spigotUser);
        this.exp = exp;
    }

    /**
     * Get the amount of experience the player will receive
     *
     * @return The amount of experience
     */
    public int getAmount() {
        return exp;
    }

    /**
     * Set the amount of experience the player will receive
     *
     * @param amount The amount of experience to set
     */
    public void setAmount(int amount) {
        exp = amount;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}