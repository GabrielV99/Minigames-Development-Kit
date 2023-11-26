package ro.Gabriel.Class;

import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class ClassManager {

    private static volatile ClassManager INSTANCE;

    public static void load(Plugin plugin) {
        ClassManager instance = getInstance();


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
