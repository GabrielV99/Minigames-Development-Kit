package ro.Gabriel.Placeholder.test2;

import org.bukkit.entity.Player;
import ro.Gabriel.Managers.Annotations.ManagerClass;

import java.util.HashMap;

@ManagerClass(bungee = true, lobby = true, arena = true)
public class PlaceholderManager {

    private static PlaceholderManager INSTANCE;

    private HashMap<String, Placeholder<Object>> placeholders;

    private static PlaceholderManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ro.Gabriel.Placeholder.PlaceholderManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PlaceholderManager();
                }
            }
        }
        return INSTANCE;
    }

    public static void load() {
        getInstance().placeholders = new HashMap<>();

        register("playerPlaceholder",  new Placeholder<Player>("%player_name%") {
            @Override
            public String apply(String text, Player player) {
                return text = text.replace(placeholder, player.getName());
            }
        });

    }

    @SuppressWarnings("unchecked")
    private static void register(String id, Placeholder<?> placeholder) {
        getInstance().placeholders.put(id, (Placeholder<Object>) placeholder);
    }

    public static String makeReplacement(String text, String placeholderId, Object source) {
        return getInstance().placeholders.get(placeholderId).apply(text, source);
    }

    /*public static String makeReplacement(String text, String ... placeholders) {
        Stream.of(placeholders).map(placeholderId -> getInstance().placeholders.get(placeholderId)).forEach(placeholder -> {
            placeholder.apply(text,)
        });
        return text;
    }*/
}
