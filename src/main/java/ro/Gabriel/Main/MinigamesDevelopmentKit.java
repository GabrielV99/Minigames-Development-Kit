package ro.Gabriel.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import ro.Gabriel.Misc.ReflectionUtils;
import ro.Gabriel.Misc.ServerVersion;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MinigamesDevelopmentKit extends JavaPlugin {

    private static MinigamesDevelopmentKit INSTANCE;

    private String prefix;

    private List<Minigame> minigames;

    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4S-A ACTIVAT MDK!!!!!!!!!!!!!!!!!!!!! NAME: " + this.getDescription().getName()));
        INSTANCE = this;
        this.minigames = new ArrayList<>();

        ServerVersion.load();

        Arrays.stream(this.getServer().getPluginManager().getPlugins()).filter(plugin ->
                plugin.getDescription().getDepend().contains(INSTANCE.getDescription().getName())
        ).forEach(plugin -> {
            //plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&1S-a activat pluginul &5" + plugin.getName()));
            //this.minigames.add(new Minigame().load(plugin));
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&1S-a activat pluginul &5" + plugin.getName() + "isEnabled: &e" + this.getServer().getPluginManager().isPluginEnabled(plugin)));

            try {
//                Class<?> c = Class.forName("org.bukkit.plugin.java.PluginClassLoader");
//
//                Method m = c.getDeclaredMethod("initialize", JavaPlugin.class);
//                m.setAccessible(true);
//                m.invoke(plugin.getClass().getClassLoader(), (JavaPlugin)plugin);
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&1S-a putut obtine clasa final &5PluginClassLoader"));

                Class<?> c = Class.forName("org.bukkit.plugin.java.PluginClassLoader");
                ClassLoader classLoader = plugin.getClass().getClassLoader();

                Field loader = c.getDeclaredField("loader");
                loader.setAccessible(true);

                Field description = c.getDeclaredField("description");
                description.setAccessible(true);

                Field dataFolder = c.getDeclaredField("dataFolder");
                dataFolder.setAccessible(true);

                Field file = c.getDeclaredField("file");
                file.setAccessible(true);


                Method m = JavaPlugin.class.getDeclaredMethod("init", PluginLoader.class, Server.class, PluginDescriptionFile.class, File.class, File.class, ClassLoader.class);
                m.setAccessible(true);

                Object loaderValue = loader.get(classLoader);
                Object descriptionValue = description.get(classLoader);
                Object dataFolderValue = dataFolder.get(classLoader);
                Object fileValue = file.get(classLoader);

                //m.invoke(plugin, loader.get(classLoader), plugin.getServer(), description.get(classLoader), dataFolder.get(classLoader), file.get(classLoader), classLoader);
            } catch (Exception e) {
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&1Nu s-a putut obtine clasa final &cPluginClassLoader"));

                e.printStackTrace();
            }

            /*try {
                ReflectionUtils.invokeMethod(plugin.getClass().getClassLoader(), false, "initialize", plugin);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }*/

            //this.minigames.add(new Minigame().load(plugin));
        });
    }

    public static MinigamesDevelopmentKit getInstance() {
        return INSTANCE;
    }

    public static String getPluginName() {
        return getInstance().getName();
    }

    public static PluginLoader getLoader() {
        return INSTANCE.getPluginLoader();
    }

    public static String getPrefix() {
        return INSTANCE.prefix != null ? INSTANCE.prefix : "";
    }

    public static void log(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(getPrefix() + ChatColor.translateAlternateColorCodes('&', message));
    }
    public static void sendMessage(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }


    public static void stop() {
        MinigamesDevelopmentKit.log("&cStop the server...");
        getInstance().getServer().savePlayers();
        getInstance().getServer().shutdown();
    }

    public static void disable() {
        MinigamesDevelopmentKit.log("&cDisable this plugin...");
        getInstance().getPluginLoader().disablePlugin(MinigamesDevelopmentKit.getInstance());
    }


    public static void runTaskAsynchronously(Runnable task) {
        INSTANCE.getServer().getScheduler().runTaskAsynchronously(MinigamesDevelopmentKit.getInstance(), task);
    }

    private Stream<Plugin> get() {
        return Arrays.stream(Bukkit.getPluginManager().getPlugins()).filter(plugin ->
                plugin.getDescription().getDepend().contains(this.getDescription().getName()) || plugin.equals(this)
        );
    }
}