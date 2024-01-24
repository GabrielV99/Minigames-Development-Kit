package ro.Gabriel.Listener.Player;

import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Cancellable;

import ro.Gabriel.Listener.Player.CustomEvents.CustomPlayerJoinEvent;
import ro.Gabriel.Listener.Player.CustomEvents.CustomPlayerMoveEvent;
import ro.Gabriel.Listener.ListenerManager;
import ro.Gabriel.Listener.CustomListener;
import ro.Gabriel.Misc.ReflectionUtils;
import ro.Gabriel.User.SpigotUser;
import ro.Gabriel.Main.Minigame;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomPlayerListener<ListenerType extends PlayerEvent> extends CustomListener<ListenerType> {
    private CustomListener<CustomPlayerEvent<? extends SpigotUser>>[] listeners;

    private final Class<ListenerType> bukkitEventType;

    @SuppressWarnings("unchecked")
    public CustomPlayerListener(Class<ListenerType> bukkitEventType) {
        this.listeners = new CustomListener[0];
        this.bukkitEventType = bukkitEventType;
    }

    public void registerListener(Minigame minigame, ListenerManager listenerManager, Class<? extends CustomListener<CustomPlayerEvent<? extends SpigotUser>>> listenerClass) {
        try {
            CustomListener<CustomPlayerEvent<? extends SpigotUser>> listener = listenerClass.getConstructor().newInstance();
            ReflectionUtils.setValue(listener, CustomListener.class, true, "plugin", minigame);

            EventHandler eventHandler = listenerManager.getEventHandler(listener);
            if(eventHandler != null) {
                CustomListener<CustomPlayerEvent<? extends SpigotUser>>[] oldListeners = this.listeners;

                // create a new array with length increment, put all the CustomPlayerEvents on their positions and a added CustomPlayerEvent on last position.
                this.listeners = new CustomListener[oldListeners.length + 1];
                for(int i = 0; i < oldListeners.length; i++) {
                   this.listeners[i] = oldListeners[i];
                }
                this.listeners[this.listeners.length - 1] = listener;

                // rearrange the positions of the CustomPlayerEvent elements by event priority.
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
        ExecutorService executor = null;

        for(int i = 0; i < this.listeners.length; i++) {
            CustomListener<CustomPlayerEvent<? extends SpigotUser>> listener = this.listeners[i];
            if(listener == null) continue;

            SpigotUser spigotUser = listener.getPlugin().getUserRepository().findById(event.getPlayer().getUniqueId());
            if(spigotUser != null) {
                CustomPlayerEvent<? extends SpigotUser> customPlayerEvent = this.getCustomPlayerEvent(event, spigotUser);
                listener.run(customPlayerEvent);

                if(customPlayerEvent instanceof Cancellable & event instanceof Cancellable) {
                    ((Cancellable)event).setCancelled(((Cancellable)customPlayerEvent).isCancelled());
                }
            } else {
                if(executor == null) {
                    executor = Executors.newSingleThreadExecutor();
                }

                try {
                    executor.submit(() -> {
                        CustomPlayerEvent<? extends SpigotUser> customPlayerEvent = this.getCustomPlayerEvent(
                                event,
                                listener.getPlugin().getUserRepository().getById(event.getPlayer().getUniqueId())
                        );
                        listener.run(customPlayerEvent);

                        if(customPlayerEvent instanceof Cancellable & event instanceof Cancellable) {
                            ((Cancellable)event).setCancelled(((Cancellable)customPlayerEvent).isCancelled());
                        }
                    }).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private CustomPlayerEvent<? extends SpigotUser> getCustomPlayerEvent(ListenerType event, SpigotUser spigotUser) {
        if (event instanceof PlayerJoinEvent) {
            return new CustomPlayerJoinEvent<>(event.getPlayer(), spigotUser, ((PlayerJoinEvent)event).getJoinMessage());
        } else if(event instanceof PlayerMoveEvent) {
            return new CustomPlayerMoveEvent<>(event.getPlayer(), spigotUser, ((PlayerMoveEvent)event).getFrom(), ((PlayerMoveEvent)event).getTo());
        }

        return null;
    }
}