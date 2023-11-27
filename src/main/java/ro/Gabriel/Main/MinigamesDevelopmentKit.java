package ro.Gabriel.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.SimplePluginManager;
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

public class MinigamesDevelopmentKit extends Minigame {

    private static MinigamesDevelopmentKit INSTANCE;

    private List<Minigame> minigames;

    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4S-A ACTIVAT MDK!!!!!!!!!!!!!!!!!!!!! NAME: " + this.getDescription().getName()));
        INSTANCE = this;
        this.minigames = new ArrayList<>();

        ServerVersion.load();

        Arrays.stream(this.getServer().getPluginManager().getPlugins()).filter(plugin -> plugin.getDescription().getDepend().contains(INSTANCE.getDescription().getName()))
                .filter(plugin -> {
                    String name = plugin.getName();

                    this.log("&aTrying to load &e" + name + " &aminigame...");
                    try {
                        boolean extendsMinigame, isField, isMethod;

                        Class<?> clazz = Class.forName(plugin.getDescription().getMain());

                        if(!(extendsMinigame = Minigame.class.isAssignableFrom(clazz))) {
                            this.log("&cYour &4" + name + " &cmain class not extends &4Minigame &cclass!");
                            return false;
                        }

                        if(!(isField = ReflectionUtils.fieldExist(clazz, "instance", true, true) && clazz.getDeclaredField("instance").getType().equals(clazz))) {
                            this.log("&cYour &4" + name + " &cmain class must contain a static field with name 'instance' of the main class type!");
                            return false;
                        }

                        if(!(isMethod = ReflectionUtils.methodExist(clazz, "getInstance", true, true) && clazz.getDeclaredMethod("getInstance").getReturnType().equals(clazz))) {
                            this.log("&cYour &4" + name + " &cmain class must contain a static method with name 'getInstance' that return main class type!");
                            return false;
                        }

                        return extendsMinigame && isField && isMethod;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
        }).forEach(plugin -> {
            try {
                ReflectionUtils.invokeMethod(plugin, JavaPlugin.class, "setEnabled", true, new Object[]{true});
                this.log("&aThe &e" + plugin.getName() + " &aminigame successfully enabled!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static MinigamesDevelopmentKit getInstance() {
        return INSTANCE;
    }
}