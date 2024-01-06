package ro.Gabriel.Managers;

import org.bukkit.plugin.Plugin;
import ro.Gabriel.Class.ClassScanner;
import ro.Gabriel.Class.ClassUtils;
import ro.Gabriel.Class.Validators.ManagerClassValidator;
import ro.Gabriel.Main.Minigame;
import ro.Gabriel.Main.MinigamesDevelopmentKit;
import ro.Gabriel.Misc.ReflectionUtils;

import java.util.stream.Stream;

public class ManagersProcessor {
    public static void loadManagers(Minigame plugin) {
        plugin.getBasePluginInstance().log("&aTrying to load &6" + plugin.getName() + " &amanagers...");

        try {
            Object instance = ReflectionUtils.invokeMethod(null, ReflectionUtils.getClass(plugin.getDescription().getMain()), "getInstance");
            if(instance instanceof Minigame) {
                ((Minigame)instance).searchClasses(plugin.getBasePluginInstance(), ManagerClassValidator.class, Manager.class, instance)
                        .forEach(clazz -> loadManager(instance, clazz));
                plugin.getBasePluginInstance().log("&aWe successfully load &6" + ((Minigame)instance).getManagersCount() + " &amanager" + (((Minigame)instance).getManagersCount() > 1 ? "s" : "") + " &ain &6" + plugin.getName() + " &aminigame!");
            } else {
                String[] mainClassComponents = plugin.getDescription().getMain().split("\\.");
                plugin.getBasePluginInstance().log("&cThe &4" + plugin.getName() + " &cmanagers cannot be loaded because '&4getInstance&c' method from main class (&4" + mainClassComponents[mainClassComponents.length - 1] +  "&c) return null value!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadManager(Object minigame, Class<? extends Manager> managerClass) {
        Manager manager = ((Minigame)minigame).getManager(managerClass);
        if(manager == null) {
            ManagerClass managerClassAnnotation = ClassUtils.getAnnotation(managerClass, ManagerClass.class);

            if(managerClassAnnotation.managersBefore().length > 0) {
                Stream.of(managerClassAnnotation.managersBefore())
                        .filter(((Minigame)minigame).getValidator(ManagerClassValidator.class).validate(minigame))
                        .forEach(clazz -> loadManager(minigame, clazz));
            }
            try {
                ((Minigame)minigame).registerManager((Manager) ReflectionUtils.instantiateObject(managerClass, true, new Object[]{minigame}));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}