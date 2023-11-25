package ro.Gabriel.Listener.Listeners.PlayerListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import ro.Gabriel.BuildBattle.Main;
import ro.Gabriel.Listener.CustomListener;

public class PlayerMoveListenerHigh extends CustomListener<PlayerMoveEvent> {
    @Override
    @EventHandler(priority = EventPriority.HIGH)
    public void run(PlayerMoveEvent event) {

        //Main.log("move high");
    }
}
