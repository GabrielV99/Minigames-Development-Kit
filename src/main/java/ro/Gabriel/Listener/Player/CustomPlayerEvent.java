package ro.Gabriel.Listener.Player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import ro.Gabriel.Main.Minigame;
import ro.Gabriel.User.SpigotUser;

public abstract class CustomPlayerEvent<PlayerType extends SpigotUser> extends PlayerEvent {

    private final PlayerType spigotPlayer;

    public CustomPlayerEvent(Minigame minigame, Player who) {
        super(who);
        this.spigotPlayer = (PlayerType) minigame.getUserRepository().findById(who.getUniqueId());
    }

    public PlayerType getSpigotPlayer() {
        return this.spigotPlayer;
    }
}