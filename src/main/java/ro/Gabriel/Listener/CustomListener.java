package ro.Gabriel.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event;
import ro.Gabriel.Main.Minigame;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class CustomListener<ListenerType extends Event> implements Listener {
    protected Minigame mainInstance;

    private void registerListener(Minigame mainInstance) {
        this.mainInstance = mainInstance;
        ListenerUtils.registerListener(mainInstance, this, getListenerType());
        //mainInstance.getServer().getPluginManager().registerEvents(new Listener(), this);
    }

    public Minigame getMainInstance() {
        return this.mainInstance;
    }

    @SuppressWarnings("unchecked")
    public Class<? extends Event> getListenerType() {
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