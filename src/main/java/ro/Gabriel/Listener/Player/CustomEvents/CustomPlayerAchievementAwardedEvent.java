package ro.Gabriel.Listener.Player.CustomEvents;

import org.bukkit.Achievement;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import ro.Gabriel.Listener.Player.CustomPlayerEvent;
import ro.Gabriel.User.SpigotUser;

@Deprecated
public class CustomPlayerAchievementAwardedEvent<PlayerType extends SpigotUser> extends CustomPlayerEvent<PlayerType> implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Achievement achievement;
    private boolean cancel = false;

    public CustomPlayerAchievementAwardedEvent(Player who, PlayerType spigotUser, Achievement achievement) {
        super(who, spigotUser);
        this.achievement = achievement;
    }

    /**
     * Gets the Achievement being awarded.
     *
     * @return the achievement being awarded
     */
    public Achievement getAchievement() {
        return achievement;
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