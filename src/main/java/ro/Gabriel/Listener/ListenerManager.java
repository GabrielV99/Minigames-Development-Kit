package ro.Gabriel.Listener;

import org.bukkit.Warning;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.plugin.AuthorNagException;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.spigotmc.CustomTimingsHandler;
import ro.Gabriel.Class.ClassScanner;
import ro.Gabriel.Class.ClassUtils;
import ro.Gabriel.Listener.Player.CustomPlayerEvent;
import ro.Gabriel.Listener.Player.CustomPlayerListener;
import ro.Gabriel.Main.Minigame;
import ro.Gabriel.Managers.Manager;
import ro.Gabriel.User.SpigotUser;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;

public class ListenerManager implements Manager {
    private final Minigame mainInstance;

    private Map<Class<? extends PlayerEvent>, CustomPlayerListener<? extends PlayerEvent>> playerListeners;

    public ListenerManager(Minigame mainInstance) {
        this.mainInstance = mainInstance;

        /*
        * AsyncPlayerChatEvent
        * AsyncPlayerPreLoginEvent
        * PlayerAchievementAwardedEvent - Deprecated
        * PlayerAdvancementDoneEvent
        * PlayerAnimationEvent
        * PlayerArmorStandManipulateEvent
        * PlayerBedEnterEvent
        * PlayerBedLeaveEvent
        *
        * PlayerBucketEvent:
        * PlayerBucketEmptyEvent
        * PlayerBucketFillEvent
        *
        * PlayerChangedMainHandEvent
        * PlayerChangedWorldEvent
        *
        * PlayerChannelEvent:
        * PlayerRegisterChannelEvent
        * PlayerUnregisterChannelEvent
        *
        * PlayerChatEvent - Deprecated
        *
        * PlayerChatTabCompleteEvent
        * PlayerCommandPreprocessEvent
        * PlayerDropItemEvent
        * PlayerEditBookEvent
        * PlayerEggThrowEvent
        * PlayerExpChangeEvent
        * PlayerFishEvent
        * PlayerGameModeChangeEvent
        * PlayerInteractAtEntityEvent
        * PlayerInteractEntityEvent
        * PlayerInteractEvent
        * PlayerItemBreakEvent
        * PlayerItemConsumeEvent
        * PlayerItemDamageEvent
        * PlayerItemHeldEvent
        * PlayerItemMendEvent
        * PlayerJoinEvent
        * PlayerKickEvent
        * PlayerLevelChangeEvent
        * PlayerLocaleChangeEvent
        * PlayerLoginEvent
        * PlayerMoveEvent
        * PlayerPickupArrowEvent
        * PlayerPickupItemEvent - Deprecated
        * PlayerPortalEvent
        * PlayerPreLoginEvent - Deprecated
        * PlayerQuitEvent
        * PlayerResourcePackStatusEvent
        * PlayerRespawnEvent
        * PlayerShearEntityEvent
        * PlayerStatisticIncrementEvent
        * PlayerSwapHandItemsEvent
        * PlayerTeleportEvent
        * PlayerToggleFlightEvent
        * PlayerToggleSneakEvent
        * PlayerToggleSprintEvent
        * PlayerUnleashEntityEvent
        * PlayerVelocityEvent
        *
        *
        * PlayerInteractAtEntityEvent
        * PlayerInteractEntityEvent
        * PlayerInteractEvent
        * PlayerShearEntityEvent
        *
        *
        * */
        PlayerInteractAtEntityEvent e1;
        PlayerInteractEntityEvent e2;
        PlayerInteractEvent e3;
        PlayerShearEntityEvent e4;

    }

    @Override
    public Minigame getMainInstance() {
        return this.mainInstance;
    }


    @SuppressWarnings("unchecked")
    public void registerListeners(Minigame minigame) {
        if(minigame.isBasePlugin()) {
            return;
        }

        ClassScanner.getAllClassesByPlugin(minigame, clazz -> {// orice CustomListener dintr-un plugin secundar, sau orice CustomListener din pluginul MDK dar sa nu fie PlayerListener
            Class<?> listenerEventType = this.getListenerEventType(clazz);
            return ClassUtils.extendsClass(clazz, CustomListener.class)
                    & (!minigame.isBasePlugin() | !ClassUtils.extendsClass(listenerEventType, PlayerEvent.class));
        }).forEach(clazz -> {
            this.registerListener(minigame, (Class<? extends CustomListener<? extends Event>>) clazz);
        });
    }

    @SuppressWarnings("unchecked")
    private void registerListener(Minigame minigame, Class<? extends CustomListener<?>> listenerClass) {
        Class<?> listenerEventType = this.getListenerEventType(listenerClass);
        if(listenerEventType == null) return;

        if(ClassUtils.extendsClass(listenerEventType, PlayerEvent.class) & !ClassUtils.extendsClass(listenerEventType, CustomPlayerEvent.class) & !minigame.isBasePlugin()) {
            minigame.log("&cUnable to register &6" + listenerEventType.getSimpleName() + " &6listener. You can use &aCustom" + listenerEventType.getSimpleName() + " &6instead...");
            return;
        }

        //CustomPlayerEvent din MDK sau plugin secundar
        //PlayerEvent spigot/bukkit din MDK

        if(ClassUtils.extendsClass(listenerEventType, CustomPlayerEvent.class)) {
            try {
                Class<? extends PlayerEvent> bukkitPlayerEvent = (Class<? extends PlayerEvent>) Class.forName(
                        "org.bukkit.event.player." + listenerEventType.getSimpleName().replace("Custom", "")
                );

                CustomPlayerListener<? extends PlayerEvent> playerListener = this.playerListeners.get(bukkitPlayerEvent);
                if(playerListener == null) {
                    playerListener = new CustomPlayerListener(this, bukkitPlayerEvent);
                    this.playerListeners.put(bukkitPlayerEvent, playerListener);

                    RegisteredListener registeredListener = this.createRegisteredListener(minigame, playerListener);
                    this.getEventListeners(this.getRegistrationClass(bukkitPlayerEvent)).register(registeredListener);
                }
                playerListener.registerListener(minigame, this, (Class<? extends CustomListener<CustomPlayerEvent<? extends SpigotUser>>>)listenerClass);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }


        /*try {
            CustomListener<?> listener = (CustomListener<?>) listenerClass.getConstructor().newInstance();
            //RegisteredListener registeredListener = this.createRegisteredListener(minigame, listenerClass);
            //this.getEventListeners(this.getRegistrationClass(bukkitPlayerEvent)).register(registeredListener);
        } catch (Exception e) {

        }*/
    }

    public RegisteredListener createRegisteredListener(Minigame minigame, CustomListener<? extends Event> listener) {
        Method run;

        try {
            run = listener.getClass().getDeclaredMethod("run", Event.class);
        } catch (Exception e) {
            try {
                run = listener.getClass().getMethod("run", Event.class);
            } catch (Exception e1) {
                e1.printStackTrace();
                return null;
            }
        }

        EventHandler eh = run.getAnnotation(EventHandler.class);

        if(eh == null) {
            try {
                eh = run.getDeclaringClass().getSuperclass().getMethod(run.getName(), run.getParameterTypes()).getAnnotation(EventHandler.class);
            } catch (Exception e) {
                return null;
            }
        }

        if(eh != null) {
            Class<?> checkClass = run.getParameterTypes()[0];
            Class<? extends Event> eventClass = checkClass.asSubclass(Event.class);

            run.setAccessible(true);

            for (Class<?> clazz = eventClass; Event.class.isAssignableFrom(clazz); clazz = clazz.getSuperclass()) {
                if (clazz.getAnnotation(Deprecated.class) != null) {
                    Warning warning = clazz.getAnnotation(Warning.class);
                    Warning.WarningState warningState = minigame.getServer().getWarningState();
                    if (!warningState.printFor(warning)) {
                        break;
                    }
                    minigame.getLogger().log(
                            Level.WARNING,
                            String.format(
                                    "\"%s\" has registered a listener for %s on method \"%s\", but the event is Deprecated. \"%s\"; please notify the authors %s.",
                                    minigame.getDescription().getFullName(),
                                    clazz.getName(),
                                    run.toGenericString(),
                                    (warning != null && warning.reason().length() != 0) ? warning.reason() : "Server performance will be affected",
                                    Arrays.toString(minigame.getDescription().getAuthors().toArray())),
                            warningState == Warning.WarningState.ON ? new AuthorNagException(null) : null);
                    break;
                }
            }

            final CustomTimingsHandler timings = new CustomTimingsHandler("Plugin: " + minigame.getDescription().getFullName() + " Event: " + listener.getClass().getName() + "::" + run.getName() + "(" + eventClass.getSimpleName() + ")", JavaPluginLoader.pluginParentTimer); // Spigot
            final Method runMethod = run;

            EventExecutor executor = (listener1, event) -> {
                try {
                    if (!eventClass.isAssignableFrom(event.getClass())) {
                        return;
                    }
                    // Spigot start
                    boolean isAsync = event.isAsynchronous();
                    if (!isAsync) timings.startTiming();
                    runMethod.invoke(listener1, event);
                    if (!isAsync) timings.stopTiming();
                    // Spigot end
                } catch (InvocationTargetException ex) {
                    throw new EventException(ex.getCause());
                } catch (Throwable t) {
                    throw new EventException(t);
                }
            };
            return new RegisteredListener(listener, executor, eh.priority(), minigame, eh.ignoreCancelled());
        }

        return null;
    }

    private Class<? extends Event> getRegistrationClass(Class<? extends Event> clazz) {
        try {
            clazz.getDeclaredMethod("getHandlerList");
            return clazz;
        } catch (NoSuchMethodException e) {
            if (clazz.getSuperclass() != null
                    && !clazz.getSuperclass().equals(Event.class)
                    && Event.class.isAssignableFrom(clazz.getSuperclass())) {
                return getRegistrationClass(clazz.getSuperclass().asSubclass(Event.class));
            } else {
                throw new IllegalPluginAccessException("Unable to find handler list for event " + clazz.getName() + ". Static getHandlerList method required!");
            }
        }
    }

    private HandlerList getEventListeners(Class<? extends Event> type) {
        try {
            Method method = getRegistrationClass(type).getDeclaredMethod("getHandlerList");
            method.setAccessible(true);

            if (!Modifier.isStatic(method.getModifiers())) {
                throw new IllegalAccessException("getHandlerList must be static");
            }

            return (HandlerList) method.invoke(null);
        } catch (Exception e) {
            throw new IllegalPluginAccessException("Error while registering listener for event type " + type.toString() + ": " + e.toString());
        }
    }



    ////////////////////////////////////////////////////////////////////////////

    private Class<? extends Event> getListenerEventType(Class<?> listenerClass) {
        Type type = listenerClass.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return (Class<? extends Event>) parameterizedType.getActualTypeArguments()[0];
        }
        return null;
    }

    public EventHandler getEventHandler(CustomListener<? extends Event> listener) {
        EventHandler eventHandler = ClassUtils.getAnnotation(listener.getClass(), EventHandler.class);
        if(eventHandler == null) {
            Class<? extends CustomListener<? extends Event>> superClass = (Class<? extends CustomListener<? extends Event>>)listener.getClass().getSuperclass();
            if(superClass != null) {
                eventHandler = ClassUtils.getAnnotation(superClass, EventHandler.class);
            }
        }
        return eventHandler;
    }
}