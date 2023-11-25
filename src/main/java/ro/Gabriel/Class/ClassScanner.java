package ro.Gabriel.Class;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;

import ro.Gabriel.Misc.ReflectionUtils;
import ro.Gabriel.BuildBattle.Main;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.jar.JarFile;
import java.io.File;
import java.util.*;

public class ClassScanner {
    public static List<Class<?>> getAllTypeClasses(Predicate<? super Class<?>> predicate) {
        List<Class<?>> classes = new ArrayList<>();

        Arrays.stream(Bukkit.getPluginManager().getPlugins()).filter(plugin ->
                plugin.getDescription().getDepend().contains(Main.getPluginDescription()) || plugin.equals(Main.getInstance())
        ).forEach(plugin -> classes.addAll(ClassScanner.getAllClassesByPlugin(plugin).stream().filter(predicate).collect(Collectors.toList())));
        return classes;
    }

    public static List<Class<?>> getAllTypeClassesByPlugin(Plugin plugin, Predicate<? super Class<?>> predicate) {
        return ClassScanner.getAllClassesByPlugin(plugin).stream().filter(predicate).collect(Collectors.toList());
    }

    public static <T> List<Class<? extends T>> getAllTypeClassesByPlugin(Plugin plugin, Predicate<? super Class<?>> predicate, Class<T> type) {
        return ClassScanner.getAllClassesByPlugin(plugin, type).stream().filter(predicate).collect(Collectors.toList());
    }

    public static List<Class<?>> getAllClassesByPlugin(Plugin plugin) {
        return getAllClassesByPlugin(plugin, Object.class);
    }

    public static <T> List<Class<? extends T>> getAllClassesByPlugin(Plugin plugin, Class<T> type) {
        List<Class<? extends T>> classes = new ArrayList<>();
    
        try {
            JarFile jarFile = new JarFile((File) ReflectionUtils.invokeMethod(plugin, true, JavaPlugin.class, "getFile"));
            jarFile.stream().forEach(entry -> {
                if (!entry.isDirectory()) {
                    String name = entry.getName();
                    String className = name.replace('/', '.').substring(0, name.length() - 6);
                    try {
                        classes.add((Class<? extends T>) Class.forName(className, false, plugin.getClass().getClassLoader()));
                    } catch (ClassNotFoundException ignored) { }
                }
            });
        } catch (Exception ignored) { }

        return classes;
    }
}