package ro.Gabriel.Listener;

import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.spigotmc.CustomTimingsHandler;
import ro.Gabriel.Class.ClassScanner;
import ro.Gabriel.Class.ClassUtils;
import ro.Gabriel.Listener.Listeners.PlayerListeners.PlayerJoinListener;
import ro.Gabriel.Listener.Player.CustomPlayerEvent;
import ro.Gabriel.Listener.Player.CustomPlayerListener;
import ro.Gabriel.Main.Minigame;
import ro.Gabriel.Managers.Manager;
import ro.Gabriel.Misc.ReflectionUtils;
import ro.Gabriel.User.SpigotUser;
import ro.Gabriel.test;

import java.lang.reflect.*;
import java.util.*;

public class ListenerManager implements Manager {
    private final Minigame basePlugin;

    private final Map<Class<? extends PlayerEvent>, CustomPlayerListener<? extends PlayerEvent>> customPlayerListeners;
    private final Map<Minigame, List<CustomListener<?>>> listeners;

    public ListenerManager(Minigame basePlugin) {
        this.basePlugin = basePlugin;
        this.customPlayerListeners = new HashMap<>();
        this.listeners = new HashMap<>();

        //this.registerListener(this.getPlugin(), test.class);

        AsyncPlayerChatEvent s;
        PlayerAchievementAwardedEvent s1;
        PlayerAdvancementDoneEvent s2;
        PlayerAnimationEvent s3;
        PlayerArmorStandManipulateEvent s4;//de revenit cand e cazul
        PlayerBedEnterEvent s5;
        PlayerBedLeaveEvent s6;
        PlayerBucketEvent s7;
        PlayerBucketEmptyEvent s8;
        PlayerBucketFillEvent s9;
        PlayerChangedMainHandEvent s10;
        PlayerChangedWorldEvent s11;
        PlayerChannelEvent s12;
        PlayerRegisterChannelEvent s13;
        PlayerUnregisterChannelEvent s14;
        PlayerChatEvent s15;
        PlayerChatTabCompleteEvent s16;
        PlayerCommandPreprocessEvent s17;
        PlayerDropItemEvent s18;
        PlayerEditBookEvent s19;
        PlayerEggThrowEvent s20;
        PlayerExpChangeEvent s21;
        PlayerFishEvent s22;

        /*
        * AsyncPlayerChatEvent                                  OK
        * AsyncPlayerPreLoginEvent                              --
        * PlayerAchievementAwardedEvent - Deprecated            OK
        * PlayerAdvancementDoneEvent                            OK
        * PlayerAnimationEvent                                  OK
        * PlayerArmorStandManipulateEvent
        * PlayerBedEnterEvent                                   OK
        * PlayerBedLeaveEvent                                   OK
        *
        * PlayerBucketEvent:                                    OK
        * PlayerBucketEmptyEvent                                OK
        * PlayerBucketFillEvent                                 OK
        *
        * PlayerChangedMainHandEvent                            OK
        * PlayerChangedWorldEvent                               OK
        *
        * PlayerChannelEvent:                                   OK
        * PlayerRegisterChannelEvent                            OK
        * PlayerUnregisterChannelEvent                          OK
        *
        * PlayerChatEvent - Deprecated                          OK
        *
        * PlayerChatTabCompleteEvent                            OK
        * PlayerCommandPreprocessEvent                          OK
        * PlayerDropItemEvent                                   OK
        * CustomPlayerEditBookEvent                             OK
        * PlayerEggThrowEvent                                   OK
        * PlayerExpChangeEvent                                  OK
        * PlayerFishEvent                                       OK
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

    }

    @Override
    public Minigame getPlugin() {
        return this.basePlugin;
    }


    @SuppressWarnings("unchecked")
    public void registerListeners(Minigame plugin) {
        ClassScanner.getAllClassesByPlugin(plugin, clazz -> {
            Class<?> listenerEventType = this.getListenerEventType(clazz);// true && true &&
            return listenerEventType != null && ClassUtils.extendsClass(clazz, CustomListener.class)
                    & (!plugin.isBasePlugin() | (!ClassUtils.extendsClass(listenerEventType, PlayerEvent.class) | ClassUtils.extendsClass(listenerEventType, CustomPlayerEvent.class) ));
        }).forEach(clazz -> {
            try {
                this.registerListener(plugin, (Class<? extends CustomListener<? extends Event>>) clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void registerListener(Minigame plugin, Class<? extends CustomListener<?>> listenerClass) {
        Class<?> listenerEventType = this.getListenerEventType(listenerClass);
        if(listenerEventType == null) return;

        if(!listenerEventType.equals(CustomPlayerEvent.class) & ClassUtils.extendsClass(listenerEventType, PlayerEvent.class) & !plugin.isBasePlugin()) {
            plugin.log("&cUnable to register &6" + listenerClass.getSimpleName() + " &6listener because contain PlayerEvent type. You can use &aCustom" + listenerEventType.getSimpleName() + " &6instead for your listener...");
            //minigame.log("&cUnable to register &6" + listenerEventType.getSimpleName() + " &6listener. You can use &aCustom" + listenerEventType.getSimpleName() + " &6instead...");
            return;
        }

        try {
            if(ClassUtils.extendsClass(listenerEventType, CustomPlayerEvent.class)) {
                Class<? extends PlayerEvent> bukkitPlayerEvent = (Class<? extends PlayerEvent>) Class.forName(
                        "org.bukkit.event.player." + listenerEventType.getSimpleName().replace("Custom", "")
                );

                CustomPlayerListener<? extends PlayerEvent> playerListener = this.customPlayerListeners.get(bukkitPlayerEvent);
                if(playerListener == null) {
                    playerListener = new CustomPlayerListener(bukkitPlayerEvent);
                    this.customPlayerListeners.put(bukkitPlayerEvent, playerListener);

                    RegisteredListener registeredListener = this.createRegisteredListener(plugin.getBasePluginInstance(), playerListener, bukkitPlayerEvent);
                    this.getEventListeners(bukkitPlayerEvent).register(registeredListener);
                }
                playerListener.registerListener(plugin, this, (Class<? extends CustomListener<CustomPlayerEvent<? extends SpigotUser>>>)listenerClass);

                return;
            }

            CustomListener<?> listener = listenerClass.getConstructor().newInstance();
            ReflectionUtils.setValue(listener, CustomListener.class, true, "plugin", plugin);

            RegisteredListener registeredListener = this.createRegisteredListener(this.getPlugin(), listener, (Class<? extends Event>) listenerEventType);
            this.getEventListeners((Class<? extends Event>) listenerEventType).register(registeredListener);

            this.listeners.computeIfAbsent(plugin, k -> new ArrayList<>()).add(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public RegisteredListener createRegisteredListener(Minigame minigame, CustomListener<? extends Event> listener, Class<? extends Event> eventType) {
        EventHandler eventHandled = this.getEventHandler(listener);

        final CustomTimingsHandler timings = new CustomTimingsHandler("Plugin: " + minigame.getDescription().getFullName() + " Event: " + listener.getClass().getName() + "::run(" + eventType.getSimpleName() + ")", JavaPluginLoader.pluginParentTimer); // Spigot
        EventExecutor executor = (listener1, event) -> {
            try {
                if (!eventType.isAssignableFrom(event.getClass())) {
                    return;
                }

                // Spigot start
                boolean isAsync = event.isAsynchronous();
                if (!isAsync) timings.startTiming();
                ((CustomListener<Event>)listener).run(event);

                if (!isAsync) timings.stopTiming();
                // Spigot end
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        return new RegisteredListener(listener, executor, eventHandled.priority(), minigame, eventHandled.ignoreCancelled());
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
    @SuppressWarnings("unchecked")
    private Class<? extends Event> getListenerEventType(Class<?> listenerClass) {
        Type type = listenerClass.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type parameterizedType = ((ParameterizedType) type).getActualTypeArguments()[0];
            String typeName = parameterizedType.toString();

            try {
                return (Class<? extends Event>) (typeName.contains("<") ? Class.forName(typeName.substring(0, typeName.indexOf('<'))) : parameterizedType);
            } catch (Exception ignored) { }
        }
        return null;
    }

    public EventHandler getEventHandler(CustomListener<? extends Event> listener) {
       try {
           EventHandler eventHandler = ClassUtils.getAnnotation(ReflectionUtils.getMethod(listener.getClass(), "run", true, Event.class), EventHandler.class);
           if(eventHandler == null) {
               Class<? extends CustomListener<? extends Event>> superClass = (Class<? extends CustomListener<? extends Event>>)listener.getClass().getSuperclass();
               if(superClass != null) {
                   eventHandler = ClassUtils.getAnnotation(ReflectionUtils.getMethod(superClass, "run", true, Event.class), EventHandler.class);
               }
           }
           return eventHandler;
       } catch (Exception e) {
           e.printStackTrace();
       }

       return null;
    }
}

/*public RegisteredListener createRegisteredListener(Minigame minigame, CustomListener<? extends Event> listener) {
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
    }*/