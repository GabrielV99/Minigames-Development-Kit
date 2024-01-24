package ro.Gabriel.Main;

import ro.Gabriel.Listener.ListenerManager;

import org.bukkit.plugin.java.JavaPlugin;

abstract class DevelopmentMinigame extends JavaPlugin {
    private static MinigamesDevelopmentKit BASE_PLUGIN_INSTANCE;

    public DevelopmentMinigame() {
        if (BASE_PLUGIN_INSTANCE == null) {
            BASE_PLUGIN_INSTANCE = (MinigamesDevelopmentKit) this;
            ((MinigamesDevelopmentKit) this).listenerManager = new ListenerManager((MinigamesDevelopmentKit) this);
        }
    }

    public MinigamesDevelopmentKit getBasePluginInstance() {
        return BASE_PLUGIN_INSTANCE;
    }

    public boolean isBasePlugin(Minigame plugin) {
        return plugin instanceof MinigamesDevelopmentKit;
    }

    public boolean isBasePlugin() {
        return this instanceof MinigamesDevelopmentKit;
    }

    protected ListenerManager getListenerManager() {
        return BASE_PLUGIN_INSTANCE.listenerManager;
    }
}