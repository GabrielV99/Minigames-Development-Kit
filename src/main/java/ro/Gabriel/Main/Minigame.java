package ro.Gabriel.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import ro.Gabriel.Class.ClassValidator;

import java.util.HashMap;

public class Minigame extends JavaPlugin {

    private HashMap<Class<? extends ClassValidator>, ClassValidator> validators;

    private String prefix;

    public Minigame() {
        this.prefix = "&e[&6" + this.getName() + "&e] ";
    }

//    public Minigame(Plugin plugin) {
//
//        plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&1S-a activat pluginul &5" + plugin.getName()));
//
//        Bukkit.getPluginManager().enablePlugin(plugin);
//    }

    public Minigame load(Plugin plugin) {
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&1S-a activat pluginul &5" + plugin.getName()));
        //Bukkit.getPluginManager().enablePlugin(plugin);
        return this;
    }

    public String getPrefix() {
        return prefix != null ? prefix : "";
    }


    public void log(String message, String prefix) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
    }

    public void log(String message) {
        log(message, getPrefix());
    }
    public void sendMessage(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
