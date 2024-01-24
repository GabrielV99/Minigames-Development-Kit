package ro.Gabriel.Listener.Player;

import org.bukkit.event.player.PlayerEvent;
import org.bukkit.entity.Player;

import ro.Gabriel.User.SpigotUser;
import ro.Gabriel.Main.Minigame;

public abstract class CustomPlayerEvent<PlayerType extends SpigotUser> extends PlayerEvent {

    private final PlayerType spigotPlayer;

    public CustomPlayerEvent(Player who, PlayerType spigotUser) {
        super(who);
        this.spigotPlayer = spigotUser;
    }

    public CustomPlayerEvent(Minigame minigame, Player who) {
        super(who);
        this.spigotPlayer = (PlayerType) minigame.getUserRepository().findById(who.getUniqueId());
    }

    public PlayerType getSpigotUser() {
        return this.spigotPlayer;
    }
}