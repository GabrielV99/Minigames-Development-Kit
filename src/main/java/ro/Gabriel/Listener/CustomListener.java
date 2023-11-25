package ro.Gabriel.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class CustomListener<ListenerType extends Event> implements Listener {
    public CustomListener() {
        ListenerUtils.registerListener(this, getListenerType());
    }

    private Class<? extends Event> getListenerType() {
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return (Class<? extends Event>) parameterizedType.getActualTypeArguments()[0];
        }
        return null;
    }

    @EventHandler
    public abstract void run(ListenerType event);
}