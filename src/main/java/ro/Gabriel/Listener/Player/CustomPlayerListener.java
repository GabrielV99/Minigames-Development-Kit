package ro.Gabriel.Listener.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import ro.Gabriel.Listener.CustomListener;
import ro.Gabriel.Listener.ListenerManager;
import ro.Gabriel.Listener.Player.CustomEvents.CustomPlayerJoinEvent;
import ro.Gabriel.Main.Minigame;
import ro.Gabriel.Misc.ReflectionUtils;
import ro.Gabriel.User.SpigotUser;

public class CustomPlayerListener<ListenerType extends PlayerEvent> extends CustomListener<ListenerType> {

    private CustomListener<CustomPlayerEvent<? extends SpigotUser>>[] listeners;

    private final Class<ListenerType> bukkitEventType;

    public CustomPlayerListener(ListenerManager listenerManager, Class<ListenerType> bukkitEventType) {
        this.listeners = new CustomListener[0];
        this.bukkitEventType = bukkitEventType;
        //RegisteredListener registeredListener = listenerManager.createRegisteredListener(this.getMainInstance().getBasePluginInstance(), this);
    }

    /*public CustomPlayerListener() {
        this.listeners = new CustomListener[0];
    }*/

    public void registerListener(Minigame minigame, ListenerManager listenerManager, Class<? extends CustomListener<CustomPlayerEvent<? extends SpigotUser>>> listenerClass) {
        try {
            CustomListener<CustomPlayerEvent<? extends SpigotUser>> listener = listenerClass.getConstructor().newInstance();
            ReflectionUtils.setValue(listener, CustomListener.class, true, "mainInstance", minigame);

            EventHandler eventHandler = listenerManager.getEventHandler(listener);
            if(eventHandler != null) {
                CustomListener<CustomPlayerEvent<? extends SpigotUser>>[] oldListeners = this.listeners;// 0, 1, 2, 3, 4, 5, 6, 7

                this.listeners = new CustomListener[oldListeners.length + 1];// 0, 1, 2, 3, 4, 5, 8, 6, 7
                for(int i = 0; i < oldListeners.length; i++) {
                   this.listeners[i] = oldListeners[i];
                }
                this.listeners[this.listeners.length - 1] = listener;


                EventHandler currentOldEventHandler;
                for(int i = this.listeners.length - 2; i >= 0; i--) {
                    currentOldEventHandler = listenerManager.getEventHandler(listeners[i]);

                    if(eventHandler.priority().getSlot() < currentOldEventHandler.priority().getSlot()) {
                        this.listeners[i+1] = this.listeners[i];
                        this.listeners[i] = listener;
                    } else {
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(ListenerType event) {
        for(int i = 0; i < this.listeners.length; i++) {
            CustomListener<CustomPlayerEvent<? extends SpigotUser>> listener = this.listeners[i];
            if(listener != null) {
                CustomPlayerEvent<? extends SpigotUser> customEvent = getCustomPlayerEvent(listener.getMainInstance(), event);
                if(customEvent.getSpigotPlayer() == null) {

                    event.getPlayer().sendMessage("player is not in the repository!");

                } else {
                    listener.run(customEvent);
                }

            }

        }

    }

    private CustomPlayerEvent<? extends SpigotUser> getCustomPlayerEvent(Minigame minigame, ListenerType event) {
        if (event instanceof PlayerJoinEvent) {
            return new CustomPlayerJoinEvent<>(minigame, event.getPlayer(), ((PlayerJoinEvent)event).getJoinMessage());
        }

        return null;
    }
}

/*public void registerListener(Minigame minigame, ListenerManager listenerManager, Class<? extends CustomListener<? extends CustomPlayerEvent<? extends SpigotUser>>> listenerClass) {
        try {
            CustomListener<? extends CustomPlayerEvent<? extends SpigotUser>> listener = listenerClass.getConstructor().newInstance();
            ReflectionUtils.setValue(listener, CustomListener.class, true, "mainInstance", minigame);

//            EventHandler eventHandler = ClassUtils.getAnnotation(listenerClass, EventHandler.class);
//            if(eventHandler != null) {
//                eventHandler = ClassUtils.getAnnotation(listener.getClass(), EventHandler.class);
//            }
            EventHandler eventHandler = listenerManager.getEventHandler(listener);
            if(eventHandler != null) {
                CustomListener<? extends CustomPlayerEvent<? extends SpigotUser>>[] oldListeners = this.listeners;// 0, 1, 2, 3, 4, 5, 6, 7

                EventHandler currentOldEventHandler;

                this.listeners = new CustomListener[oldListeners.length + 1];// 0, 1, 2, 3, 4, 5, 6, 7, 8
                for(int i = 0; i < this.listeners.length; i++) {
                    currentOldEventHandler = listenerManager.getEventHandler(i < oldListeners.length ? oldListeners[i] : listener);


                    this.listeners[i] = (i > 0 && i < this.listeners.length - 1) ? () : ();


                    this.listeners[i] = currentOldEventHandler.priority().getSlot() < eventHandler.priority().getSlot()
                            ? oldListeners[i - (eventHandler.priority().getSlot() < currentOldEventHandler.priority().getSlot() ? 1 : 0) ]
                            : listener;
                }




            }

                    } catch (Exception e) {
                    e.printStackTrace();
                    }
                    }
                    */


/**/