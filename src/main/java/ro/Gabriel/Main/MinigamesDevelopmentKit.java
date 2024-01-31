package ro.Gabriel.Main;

import org.bukkit.plugin.java.JavaPlugin;

import ro.Gabriel.Language.Categories.CommandMessages;
import ro.Gabriel.Language.Categories.LanguageCategoryType;
import ro.Gabriel.Language.LanguageCategory;
import ro.Gabriel.Language.LanguagePath;
import ro.Gabriel.Listener.ListenerManager;
import ro.Gabriel.Managers.ManagersProcessor;
import ro.Gabriel.Misc.ReflectionUtils;
import ro.Gabriel.Misc.ServerVersion;
import ro.Gabriel.Repository.Repositories.UserRepository;
import ro.Gabriel.User.SpigotUser;
import ro.Gabriel.User.SpigotUserRepository;

import java.util.*;

public final class MinigamesDevelopmentKit extends Minigame {

    protected static MinigamesDevelopmentKit INSTANCE;

    private List<Minigame> minigames;

    protected ListenerManager listenerManager;
    private UserRepository<SpigotUser> userRepository;

    @Override
    public UserRepository<SpigotUser> getUserRepository() {
        return this.userRepository;
    }

    public void onEnable() {
        //System.out.println("MainThreadID: " + Thread.currentThread().getId());
        INSTANCE = this;

        this.log("&aStart plugin...");

        this.minigames = new ArrayList<>();

        this.userRepository = new SpigotUserRepository(this);

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

//        this.getServer().getScheduler().runTaskTimer(this, () -> {
//            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aserverul ruleaza!"));
//        }, 20L, 20L);


        /*Pentru tool-ul de lobby in care vei putea sa selectezi language-ul dintr-un meniu
        * - Daca acel tool se va afla pe un server nu 'n' pluginuri secundare in care se va afla si MDK(obligatoriu):
        *       * Fara necesitatea unei baze de date, se vor itera toate pluginurile(inclusiv MDK) si se vor putea identifica locale-urile si sursa lor(prin sursa se intelege pluginul in care au fost declarate)
        *       * In cazul in care avem o baza de date valabila, toate pluginurile(inclusiv MDK) vor trimite catre un table din baza de date locale-urile si sursa lor(prin sursa se intelege pluginul in care au fost declarate)
        *       aici vreau sa fac o precizare: Daca pluginul este bungee si de tip arena, sursa nu va fi serverul in sine, ci lobby-ul aferent acelui server de tip bungee si de tip arena(deoarece fiecare server va avea un obiect
        *       de tip 'Server" care va reprezenta lobby-ul... de Ex. un server de arene va avea un obiect de tip 'Server' care va reprezenta serverul de lobby doar pentru acel mod de joc, iar ma departe lobby-ul acelui mod de joc va
        *       avea si el la randul un un alt obiect de tip 'Server' care va reprezenta lobby-ul marec adica cel principal).
        *
        * */
    }

    public static MinigamesDevelopmentKit getInstance() {
        return INSTANCE;
    }

    public static List<Minigame> getMinigames() {
        return getInstance().minigames;
    }
}