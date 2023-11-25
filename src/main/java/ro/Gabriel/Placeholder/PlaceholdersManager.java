package ro.Gabriel.Placeholder;

import ro.Gabriel.Managers.Annotations.ManagerClass;

@ManagerClass(bungee = true, lobby = true, arena = true)
public class PlaceholdersManager {

    private volatile static PlaceholdersManager INSTANCE;

    private static PlaceholdersManager getInstance() {
        if (INSTANCE == null) {
            synchronized (PlaceholdersManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PlaceholdersManager();
                }
            }
        }
        return INSTANCE;
    }

    private static void load() {

    }
}
