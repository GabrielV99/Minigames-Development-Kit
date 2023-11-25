package ro.Gabriel.Listener;

import ro.Gabriel.Class.Validators.CustomListenerValidator;
import ro.Gabriel.Managers.Annotations.ManagerClass;
import ro.Gabriel.Class.ClassManager;

import java.util.ArrayList;
import java.util.List;

@ManagerClass(bungee = false, lobby = true, arena = true)
public final class ListenerManager {

    private volatile static ListenerManager INSTANCE;

    private List<CustomListener<?>> listeners;

    public static void load() {
        ListenerManager instance = getInstance();
        instance.listeners = new ArrayList<>();
        ClassManager.searchClasses(CustomListenerValidator.class).forEach( clazz -> {
            try {
                instance.listeners.add((CustomListener<?>) clazz.getConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static ListenerManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ListenerManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ListenerManager();
                }
            }
        }
        return INSTANCE;
    }
}