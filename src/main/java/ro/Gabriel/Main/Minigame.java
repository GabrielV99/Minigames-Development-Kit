package ro.Gabriel.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ro.Gabriel.Class.ClassScanner;
import ro.Gabriel.Class.ClassValidator;
import ro.Gabriel.Misc.ReflectionUtils;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Storage.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class Minigame extends JavaPlugin {

    private final HashMap<Class<? extends ClassValidator>, ClassValidator> validators;

    private final String mainStoragePath;

    private final ServerType serverType;
    private boolean bungeeCord;

    private char altColorCode;

    private String prefix;

    public Minigame() {
        this.mainStoragePath = getDataFolder() + "\\";

        this.validators = new HashMap<>();
        //this.prefix = "&e[&6" + this.getName() + "&e] ";

        ClassScanner.getAllClassesByPlugin(this, clazz -> !ReflectionUtils.isInterface(clazz) && ReflectionUtils.extendsClass(clazz, ClassValidator.class)).forEach(clazz -> {
            try {
                this.registerValidator((ClassValidator) clazz.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();// de sters dupa testare
            }
        });

        DataStorage config = DataStorage.getStorage(this, "config.yml", true);

        this.serverType = ServerType.of(config.getString("server-type"));
        this.bungeeCord = config.getBoolean("bungee-cord");

        this.altColorCode = config.getChar("alt-color-code");

        if(getName().equals("Minigames-Development-Kit")) {
            log("serverType: " + config.getString("server-type"));
            log("bungeeCord: " + config.getBoolean("bungee-cord"));
            log("alterCode: " + config.getChar("alt-color-code"));
        }

        //this.prefix = config.getBoolean("is-prefix") ? MessageUtils.colorString(this.altColorCode, config.getString("prefix")) : "";

    }

    public String getMainStoragePath() {
        return this.mainStoragePath;
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
}