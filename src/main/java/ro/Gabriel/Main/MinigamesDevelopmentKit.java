package ro.Gabriel.Main;

import org.bukkit.plugin.java.JavaPlugin;

import ro.Gabriel.Listener.ListenerManager;
import ro.Gabriel.Managers.ManagersProcessor;
import ro.Gabriel.Misc.ReflectionUtils;
import ro.Gabriel.Misc.ServerVersion;
import ro.Gabriel.Repository.Repository;
import ro.Gabriel.User.SpigotUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public final class MinigamesDevelopmentKit extends Minigame {

    protected static MinigamesDevelopmentKit INSTANCE;

    private List<Minigame> minigames;

    protected ListenerManager listenerManager;

//    MinigamesDevelopmentKit() {
//        listenerManager = new ListenerManager(this);
//    }

    //private ListenerManager listenerManager = new ListenerManager();

    @Override
    public Repository<UUID, SpigotUser> getUserRepository() {
        return new Repository<UUID, SpigotUser>() {
            @Override
            public SpigotUser registerEntity(UUID id) {
                MinigamesDevelopmentKit.this.log("&4AsyncPlayerPreLoginEvent ... 100: " + id);

                return new SpigotUser(MinigamesDevelopmentKit.this, id, null);
            }
        };
    }

    public void onEnable() {
        INSTANCE = this;

        this.log("&aStart plugin...");

        this.minigames = new ArrayList<>();

        ServerVersion.load();
        ManagersProcessor.loadManagers(this);

        Arrays.stream(this.getServer().getPluginManager().getPlugins())
            .filter(plugin -> plugin.getDescription().getDepend().contains(INSTANCE.getDescription().getName()) && !plugin.getName().equals(this.getName()))
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
                ReflectionUtils.invokeMethod(plugin, JavaPlugin.class, true, "setEnabled", true);
                //ManagersProcessor.loadManagers(plugin);

                //load - pentru fiecare manager din pluginul unde se afla managerul respectiv
                //initialize - pentru unul sau mai multe managere care trebuie sa se
                this.log("&aThe &e" + plugin.getName() + " &aminigame successfully enabled!");
            } catch (Exception e) {
                this.log("&cThe &4" + plugin.getName() + " &cminigame was not enabled!");
                e.printStackTrace();
            }
        });
    }

    public static MinigamesDevelopmentKit getInstance() {
        return INSTANCE;
    }

    public static List<Minigame> getMinigames() {
        return getInstance().minigames;
    }
}