package ro.Gabriel.Class;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import ro.Gabriel.Main.MinigamesDevelopmentKit;

import java.util.HashMap;

public class ClassManager {

    private static volatile ClassManager INSTANCE;

    public static void load(Plugin plugin) {
        ClassManager instance = getInstance();


    }

    public static void load() {
        MinigamesDevelopmentKit.getInstance().getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cLOADD()"));


    }

    private static ClassManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ClassManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ClassManager();
                }
            }
        }
        return INSTANCE;
    }
}
