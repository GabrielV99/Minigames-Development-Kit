package ro.Gabriel.Listener.Player.CustomEvents;

import ro.Gabriel.User.SpigotUser;

import org.bukkit.entity.Player;

public class CustomPlayerUnregisterChannelEvent<PlayerType extends SpigotUser> extends CustomPlayerChannelEvent<PlayerType> {
    public CustomPlayerUnregisterChannelEvent(Player player, PlayerType spigotUser, String channel) {
        super(player, spigotUser, channel);
    }
}