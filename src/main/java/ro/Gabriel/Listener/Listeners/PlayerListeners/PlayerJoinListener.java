package ro.Gabriel.Listener.Listeners.PlayerListeners;

import ro.Gabriel.Listener.CustomListener;
import ro.Gabriel.Listener.Player.CustomEvents.CustomPlayerJoinEvent;
import ro.Gabriel.User.SpigotUser;

public class PlayerJoinListener extends CustomListener<CustomPlayerJoinEvent<SpigotUser>> {
    @Override
    public void run(CustomPlayerJoinEvent<SpigotUser> event) {

        event.getSpigotUser().sendMessage("&6titluuuuuuuuuuuu");
    }
}