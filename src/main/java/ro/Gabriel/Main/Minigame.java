package ro.Gabriel.Main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import ro.Gabriel.Class.ClassValidator;

import java.util.HashMap;

public class Minigame extends JavaPlugin {

    private HashMap<Class<? extends ClassValidator>, ClassValidator> validators;

    public Minigame(Plugin plugin) {

        plugin.getServer().getConsoleSender().sendMessage("s-a activat pluginul " + plugin.getName());

        //Bukkit.getPluginManager().enablePlugin(plugin);
    }

}
