package ro.Gabriel.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ro.Gabriel.Class.ClassScanner;
import ro.Gabriel.Class.ClassValidator;
import ro.Gabriel.Messages.MessageUtils;
import ro.Gabriel.Misc.ReflectionUtils;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Storage.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class Minigame extends JavaPlugin {

    public static Minigame MDK_INSTANCE;

    private final HashMap<Class<? extends ClassValidator>, ClassValidator> validators;

    private final String mainStoragePath;

    private final ServerType serverType;
    private final boolean bungeeCord;

    private final char altColorCode;

    private final String prefix;

    public Minigame() {
        if(MDK_INSTANCE == null) {
            MDK_INSTANCE = this;
        }

        this.mainStoragePath = getDataFolder() + "\\";

        this.validators = new HashMap<>();

        ClassScanner.getAllClassesByPlugin(this, clazz -> !ReflectionUtils.isInterface(clazz) && ReflectionUtils.extendsClass(clazz, ClassValidator.class)).forEach(clazz -> {
            try {
                this.registerValidator((ClassValidator) clazz.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();// de sters dupa testare
            }
        });

        boolean configResource = this.resourceFileExist("config.yml");
        boolean configExist = FileUtils.fileExist(this,"config.yml");

        DataStorage config = DataStorage.getStorage(this, "config.yml", configResource);

        if(!configResource && !configExist) {
            // add some data in config
            config.set("server-type", "MULTI_ARENA", false);
            config.set("bungee-cord", false, false);
            config.set("alt-color-code", '&', false);
            config.set("is-prefix", true, false);
            config.set("prefix", ("&e[&6" + getName() + "&e] "), false);

            config.set("database.type", "SQLite", false);
            config.set("database.database", "database", false);
            config.set("database.host", "host", false);
            config.set("database.user", "user", false);
            config.set("database.password", "password", false);
            config.set("database.port", 3306, false);
            config.set("database.ssl", true, false);
            config.set("database.local-database-file", "database", false);
            config.set("database.database-fail-event", "CONNECT_TO_DEFAULT_DISABLE", false);

            config.save();
        }

        this.serverType = ServerType.of(config.getString("server-type"));
        this.bungeeCord = config.getBoolean("bungee-cord");

        this.altColorCode = config.getChar("alt-color-code");

        this.prefix = config.getBoolean("is-prefix") ? MessageUtils.colorString(this.altColorCode, config.getString("prefix")) : "";
    }

    public String getMainStoragePath() {
        return this.mainStoragePath;
    }

    public ServerType getServerType() {
        return this.serverType;
    }

    public boolean isBungeeCord() {
        return this.bungeeCord;
    }

    public String getPrefix() {
        return prefix != null ? prefix : "";
    }



    public void log(String message, String prefix) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
    }

    public void log(String message) {
        log(message, getPrefix());
    }

    public void sendMessage(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }



    private void registerValidator(ClassValidator validator) {
        this.validators.put(validator.getClass(), validator);
    }

    private ClassValidator getValidator(Class<? extends ClassValidator> validatorType) {
        return this.validators.get(validatorType);
    }

    public List<Class<?>> searchClasses(Class<? extends ClassValidator> validatorClass, Object ... parameters) {
        return ClassScanner.getAllClassesByPlugin(this, this.getValidator(validatorClass).validate(parameters));
    }

    public List<Class<?>> searchClasses(Plugin plugin, Class<? extends ClassValidator> validatorClass, Object ... parameters) {
        return ClassScanner.getAllClassesByPlugin(plugin, this.getValidator(validatorClass).validate(parameters));
    }

    public <T> List<Class<? extends T>> searchClasses(Plugin plugin, Class<? extends ClassValidator> validatorClass, Class<T> type, Object ... parameters) {
        return ClassScanner.getAllTypeClassesByPlugin(plugin, this.getValidator(validatorClass).validate(parameters), type);
    }

    private boolean resourceFileExist(String path) {
        return getResource(path + (!path.contains(".") ? FileUtils.getFileExtension(path) : "")) != null;
    }

    public static Minigame getMDKInstance() {
        return MDK_INSTANCE;
    }
}