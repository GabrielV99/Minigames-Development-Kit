package ro.Gabriel.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import ro.Gabriel.Class.ClassValidator;

import java.util.HashMap;

public class Minigame extends JavaPlugin {

    private final HashMap<Class<? extends ClassValidator>, ClassValidator> validators;

    private final String prefix;

    public Minigame() {
        this.validators = new HashMap<>();
        this.prefix = "&e[&6" + this.getName() + "&e] ";
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