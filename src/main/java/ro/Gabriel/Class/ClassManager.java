package ro.Gabriel.Class;

import org.bukkit.plugin.Plugin;
import ro.Gabriel.Main.Minigame;

import java.util.HashMap;
import java.util.List;

public class ClassManager {

    private static volatile ClassManager INSTANCE;

    private HashMap<Class<? extends ClassValidator>, ClassValidator> validators;

    public static void load(Plugin plugin) {
        ClassManager instance = getInstance();


    }

    public static void load() {
        ClassManager instance = getInstance();

        instance.validators = new HashMap<>();
        ClassScanner.getAllTypeClasses(clazz -> !clazz.isInterface() && ClassValidator.class.isAssignableFrom(clazz)).forEach(clazz -> {
            try {
                instance.registerValidator((ClassValidator) clazz.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private static ClassValidator getValidator(Class<? extends ClassValidator> validatorType) {
        return getInstance().validators.get(validatorType);
    }

    private void registerValidator(ClassValidator validator) {
        this.validators.put(validator.getClass(), validator);
    }

    public static List<Class<?>> searchClasses(Class<? extends ClassValidator> validatorClass, Object ... parameters) {
        return ClassScanner.getAllTypeClasses(getValidator(validatorClass).validate(parameters));
    }

    public static List<Class<?>> searchClasses(Plugin plugin, Class<? extends ClassValidator> validatorClass, Object ... parameters) {
        return ClassScanner.getAllTypeClassesByPlugin(plugin, getValidator(validatorClass).validate(parameters));
    }

    public static <T> List<Class<? extends T>> searchClasses(Plugin plugin, Class<? extends ClassValidator> validatorClass, Class<T> type, Object ... parameters) {
        return ClassScanner.getAllTypeClassesByPlugin(plugin, getValidator(validatorClass).validate(parameters), type);
    }

    public static boolean isManagerClassAvailable(Minigame minigame, Class<?> clazz) {
//        return clazz.isAnnotationPresent(ManagerClass.class)
//                && Main.getServerType().match(clazz.getAnnotation(ManagerClass.class))
//                && Utils.isMethodPresent(clazz, "load");
        return false;
    }

    public static void load(Class<?> clazz) {
//        try {
//            if(isManagerClassAvailable(clazz)) {
//                ReflectionUtils.invokeMethod(null, true, clazz, "load");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
