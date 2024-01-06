package ro.Gabriel.Main;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;

import ro.Gabriel.Class.Validators.CustomListenerValidator;
import ro.Gabriel.Listener.CustomListener;
import ro.Gabriel.Misc.ReflectionUtils;
import ro.Gabriel.Repository.Repository;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Main.Config.ConfigManager;
import ro.Gabriel.Language.LanguageManager;
import ro.Gabriel.Class.ClassValidator;
import ro.Gabriel.Class.ClassScanner;
import ro.Gabriel.Storage.FileUtils;
import ro.Gabriel.Managers.Manager;
import ro.Gabriel.Class.ClassUtils;
import ro.Gabriel.User.SpigotUser;

import java.util.*;

public abstract class Minigame extends DevelopmentMinigame {
    private final Map<Class<? extends ClassValidator>, ClassValidator> validators;
    private final Map<Class<? extends Manager>, Manager> managers;
    private final List<CustomListener<?>> listeners;

    private final ConfigManager configManager;
    private final LanguageManager languageManager;

    private final String mainStoragePath;

    public Minigame() {
        this.mainStoragePath = getDataFolder() + "\\";

        this.validators = new HashMap<>();
        this.managers = new HashMap<>();
        this.listeners = new ArrayList<>();

        ClassScanner.getAllTypeClassesByPlugin(this, clazz -> !ClassUtils.isInterface(clazz), ClassValidator.class).forEach(clazz -> {
            try {
                this.registerValidator(clazz.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();// de sters dupa testare
            }
        });


        DataStorage MDKConfigFile = DataStorage.getStorage(super.getBasePluginInstance(), "config.yml", true);

        this.configManager = new ConfigManager(this, MDKConfigFile);
        this.languageManager = new LanguageManager(this);

        this.getBasePluginInstance().getListenerManager().registerListeners(this);


//        this.searchClasses(CustomListenerValidator.class).forEach(clazz -> {
//            try {
//                CustomListener<?> listener = (CustomListener<?>) clazz.getConstructor().newInstance();
//                ReflectionUtils.invokeMethod(listener, CustomListener.class, true, "registerListener", Minigame.this);
//                this.listeners.add(listener);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
    }

    public abstract Repository<UUID, ? extends SpigotUser> getUserRepository();



    public String getMainStoragePath() {
        return this.mainStoragePath;
    }


    public ServerType getServerType() {
        return this.configManager.getServerType();
    }

    public boolean isBungeeCord() {
        return this.configManager.isBungeeCord();
    }

    public String getPrefix() {
        return configManager.getPrefix();
    }

    public char getAltColorCode() {
        return this.configManager.getAltColorCode();
    }


    public void log(String message, String prefix) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes(this.getAltColorCode(), prefix + message));
    }

    public void log(String message) {
        log(message, this.getPrefix());
    }

    public void sendMessage(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes(this.getAltColorCode(), message));
    }




    private void registerValidator(ClassValidator validator) {
        this.validators.put(validator.getClass(), validator);
    }

    public ClassValidator getValidator(Class<? extends ClassValidator> validatorType) {
        ClassValidator validator = this.validators.get(validatorType);
        return validator != null ? validator : super.getBasePluginInstance().getValidator(validatorType);
    }

    public void registerManager(Manager manager) {
        log("&aFound &2" + manager.getClass().getName() + " &aManagerClass in &2" + getName() + " &aplugin!");
        this.managers.put(manager.getClass(), manager);
    }

    @SuppressWarnings("unchecked")
    public <T extends Manager> T getManager(Class<T> managerClass) {
        return (T) managers.get(managerClass);
    }

    public int getManagersCount() {
        return this.managers.size();
    }


    public List<Class<?>> searchClasses(Class<? extends ClassValidator> validatorClass, Object ... parameters) {
        return ClassScanner.getAllClassesByPlugin(this, this.getValidator(validatorClass).validate(parameters));
    }

    public List<Class<?>> searchClasses(Minigame validatorSource, Class<? extends ClassValidator> validatorClass, Object ... parameters) {
        return ClassScanner.getAllClassesByPlugin(this, validatorSource.getValidator(validatorClass).validate(parameters));
    }

    public <T> List<Class<? extends T>> searchClasses(Minigame validatorSource, Class<? extends ClassValidator> validatorClass, Class<T> type, Object ... parameters) {
        return ClassScanner.getAllTypeClassesByPlugin(this, validatorSource.getValidator(validatorClass).validate(parameters), type);
    }

    public <T> List<Class<? extends T>> searchClasses(Class<? extends ClassValidator> validatorClass, Class<T> type, Object ... parameters) {
        return this.searchClasses(this, validatorClass, type, parameters);
    }

    private boolean resourceFileExist(String path) {
        return super.getResource(path + (!path.contains(".") ? FileUtils.getFileExtension(path) : "")) != null;
    }


    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public LanguageManager getLanguageManager() {
        return this.languageManager;
    }

    static {
        //FilePath configPath = new FilePath("config", "config.yml");
        //FilePath languagePath = new FilePath("language", "Language");

        /*
        * ConfigManager configManager = new ConfigManager(this, new FilePath("config", ""), new FilePath("language", "Language"))
        *
        * */
    }
}

/*@SuppressWarnings("unchecked")
    public <T extends Manager> T getManager(Class<T> managerClass) throws ClassNotFoundException {
        Manager manager = managers.get(managerClass);
        if(manager == null) {
            ManagerClass managerClassAnnotation = ReflectionUtils.getAnnotation(managerClass, ManagerClass.class);
            if(managerClassAnnotation != null
                    && this.getServerType().match(this, managerClassAnnotation)
                    && ReflectionUtils.getConstructor(managerClass, true, Class.forName(this.getDescription().getMain())) != null) {

                if(managerClassAnnotation.managersBefore().length > 0) {
                    Stream.of(managerClassAnnotation.managersBefore()).forEach(clazz -> {
                        try {
                            this.getManager(clazz);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }

                log("Constructor parameter Type: " + ReflectionUtils.getClass(this.getDescription().getMain()));

                try {
                    manager = (Manager) ReflectionUtils.instantiateObject(
                            managerClass,
                            true,
                            new Object[]{ReflectionUtils.getField(
                                    ReflectionUtils.getClass(this.getDescription().getMain()),
                                    true,
                                    "INSTANCE"
                            )}
                    );
                    this.managers.put(managerClass, manager);
                    log("&aFound &2" + managerClass.getName() + " &aManagerClass in &2" + this.getName() + " &aplugin!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return (T) manager;
    }*/