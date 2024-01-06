package ro.Gabriel.Class;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;

import ro.Gabriel.Main.MinigamesDevelopmentKit;
import ro.Gabriel.Misc.ReflectionUtils;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.jar.JarFile;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class ClassScanner {
    public static List<Class<?>> getAllClasses(Predicate<? super Class<?>> predicate) { // OK
        return getAllClassesByPlugin(MinigamesDevelopmentKit.getInstance(), predicate);
    }

    public static List<Class<?>> getAllClassesByPlugin(Plugin plugin, Predicate<? super Class<?>> predicate) { // OK
        return ClassScanner.getAllClassesByPlugin(plugin).stream().filter(predicate).collect(Collectors.toList());
    }

    public static <T> List<Class<? extends T>> getAllTypeClassesByPlugin(Plugin plugin, Predicate<? super Class<?>> predicate, Class<T> type) { // OK
        return ClassScanner.getAllClassesByPlugin(plugin, type).stream().filter(predicate).collect(Collectors.toList());
    }

    public static List<Class<?>> getAllClassesByPlugin(Plugin plugin) {// OK
        return getAllClassesByPlugin(plugin, Object.class);
    }

    public static <T> List<Class<? extends T>> getAllClassesByPlugin(Plugin plugin, Class<T> type) {// OK
        List<Class<? extends T>> classes = new ArrayList<>();

        try {
            JarFile jarFile = new JarFile((File) ReflectionUtils.invokeMethod(plugin, JavaPlugin.class, true, "getFile"));
            jarFile.stream().forEach(entry -> {
                if (!entry.isDirectory()) {
                    String name = entry.getName();
                    String className = name.replace('/', '.').substring(0, name.length() - 6);
                    try {
                        Class<? extends T> clazz = (Class<? extends T>) ReflectionUtils.getClass(className, false, plugin.getClass().getClassLoader());
                        if(ClassUtils.extendsClass(clazz, type)) {
                            classes.add(clazz);
                        }
                    } catch (ClassNotFoundException ignored) {  }
                }
            });
        } catch (Exception ignored) { }

        return classes;
    }
}