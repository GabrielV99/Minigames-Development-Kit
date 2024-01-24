package ro.Gabriel.Listener.Player.CustomEvents;

import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import ro.Gabriel.Listener.Player.CustomPlayerEvent;
import ro.Gabriel.User.SpigotUser;

public class CustomPlayerAdvancementDoneEvent<PlayerType extends SpigotUser> extends CustomPlayerEvent<PlayerType> {
    private static final HandlerList handlers = new HandlerList();
    private final Advancement advancement;

    public CustomPlayerAdvancementDoneEvent(Player player, PlayerType spigotUser, Advancement advancement) {
        super(player, spigotUser);
        this.advancement = advancement;
    }

    /**
     * Get the advancement which has been completed.
     *
     * @return completed advancement
     */
    public Advancement getAdvancement() {
        return this.advancement;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}