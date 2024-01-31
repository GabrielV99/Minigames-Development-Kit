package ro.Gabriel.Listener.Listeners.PlayerListeners;

import ro.Gabriel.Listener.CustomListener;
import ro.Gabriel.Listener.Player.CustomEvents.CustomPlayerJoinEvent;
import ro.Gabriel.Messages.MessageUtils;
import ro.Gabriel.User.SpigotUser;

public class PlayerJoinListener extends CustomListener<CustomPlayerJoinEvent<SpigotUser>> {
    @Override
    public void run(CustomPlayerJoinEvent<SpigotUser> event) {

        //event.getPlayer().sendMessage("linia1\nlinia2\nlinia3");

        event.getSpigotUser().sendMessage(MessageUtils.colorString(getPlugin(), "&6titluuuuuuuuuuuu"));
    }
}