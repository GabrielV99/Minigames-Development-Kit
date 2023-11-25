package ro.Gabriel.Listener.Listeners.PlayerListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import ro.Gabriel.BuildBattle.Main;
import ro.Gabriel.Listener.CustomListener;

public class PlayerMoveListenerLow extends CustomListener<PlayerMoveEvent> {


    @Override
    @EventHandler(priority = EventPriority.LOW)
    public void run(PlayerMoveEvent event) {
        //Main.log("move low");
    }
}
