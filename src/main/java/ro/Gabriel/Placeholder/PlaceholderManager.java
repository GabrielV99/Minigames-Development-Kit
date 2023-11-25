package ro.Gabriel.Placeholder;

import ro.Gabriel.BuildBattle.Main;
import ro.Gabriel.Misc.ReflectionUtils;
import ro.Gabriel.Misc.Utils;
import ro.Gabriel.Placeholder.Annotations.PlaceholderClass;

//@ManagerClass(bungee = true, lobby = true, arena = true)
public class PlaceholderManager {

    /*private static volatile PlaceholderManager INSTANCE;

    private HashMap<Class<? extends Placeholder>, Placeholder> placeholders;

    private Placeholder failedPlaceholder;

    @SuppressWarnings("unchecked")
    public static void load() {
        PlaceholderManager instance = getInstance();
        instance.placeholders = new HashMap<>();

        instance.failedPlaceholder = new FailedPlaceholder();

        ClassManager.searchClasses(PlaceholderValidator.class).forEach(clazz -> {
            try {
                instance.placeholders.put((Class<? extends Placeholder>) clazz, (Placeholder) ReflectionUtils.instantiateObject(clazz, true));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static Placeholder getPlaceholder(Class<? extends Placeholder> clazz) {
        Placeholder placeholder = getInstance().placeholders.get(clazz);

        return placeholder != null ? placeholder : getInstance().failedPlaceholder;
    }

    private static PlaceholderManager getInstance() {
        if (INSTANCE == null) {
            synchronized (PlaceholderManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PlaceholderManager();
                }
            }
        }
        return INSTANCE;
    }*/

    public static Placeholder<Object> getPlaceholder(Class<? extends Placeholder<?>> placeholder) {
        if(Utils.isAnnotated(placeholder, PlaceholderClass.class)
                &&  Main.getServerType().match(Utils.getAnnotation(placeholder, PlaceholderClass.class))
                && Utils.isExtends(placeholder, Placeholder.class)) {
            try {
                return (Placeholder<Object>) ReflectionUtils.instantiateObject(placeholder, true);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}