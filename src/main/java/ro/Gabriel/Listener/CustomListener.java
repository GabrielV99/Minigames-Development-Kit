package ro.Gabriel.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event;

import ro.Gabriel.Main.IPluginInstance;
import ro.Gabriel.Main.Minigame;

public abstract class CustomListener<ListenerType extends Event> implements IPluginInstance, Listener {
    protected Minigame plugin;

    @Override
    public Minigame getPlugin() {
        return this.plugin;
    }

    @EventHandler
    public abstract void run(ListenerType event);
}