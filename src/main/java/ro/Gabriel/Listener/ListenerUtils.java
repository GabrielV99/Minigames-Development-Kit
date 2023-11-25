package ro.Gabriel.Listener;

import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.plugin.AuthorNagException;
import org.bukkit.plugin.RegisteredListener;
import org.spigotmc.CustomTimingsHandler;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.Warning;
import org.bukkit.event.*;

import ro.Gabriel.BuildBattle.Main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.*;

public class ListenerUtils {

    public static void registerListener(Listener listener, Class<? extends Event> listenerClass) {
        RegisteredListener registeredListener = createRegisteredListener(listener);
        if(registeredListener != null) {
            getEventListeners(getRegistrationClass(listenerClass)).register(registeredListener);
        }
    }

    private static RegisteredListener createRegisteredListener(Listener listener) {
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
                    Warning.WarningState warningState = Main.getInstance().getServer().getWarningState();
                    if (!warningState.printFor(warning)) {
                        break;
                    }
                    Main.getInstance().getLogger().log(
                            Level.WARNING,
                            String.format(
                                    "\"%s\" has registered a listener for %s on method \"%s\", but the event is Deprecated. \"%s\"; please notify the authors %s.",
                                    Main.getInstance().getDescription().getFullName(),
                                    clazz.getName(),
                                    run.toGenericString(),
                                    (warning != null && warning.reason().length() != 0) ? warning.reason() : "Server performance will be affected",
                                    Arrays.toString(Main.getInstance().getDescription().getAuthors().toArray())),
                            warningState == Warning.WarningState.ON ? new AuthorNagException(null) : null);
                    break;
                }
            }

            final CustomTimingsHandler timings = new CustomTimingsHandler("Plugin: " + Main.getInstance().getDescription().getFullName() + " Event: " + listener.getClass().getName() + "::" + run.getName() + "(" + eventClass.getSimpleName() + ")", JavaPluginLoader.pluginParentTimer); // Spigot
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
            return new RegisteredListener(listener, executor, eh.priority(), Main.getInstance(), eh.ignoreCancelled());
        }

        return null;
    }

    private static Class<? extends Event> getRegistrationClass(Class<? extends Event> clazz) {
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

    private static HandlerList getEventListeners(Class<? extends Event> type) {
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
}