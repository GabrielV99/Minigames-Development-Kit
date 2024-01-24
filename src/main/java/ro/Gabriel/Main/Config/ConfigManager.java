package ro.Gabriel.Main.Config;

import ro.Gabriel.Managers.Manager;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Main.MinigamesDevelopmentKit;
import ro.Gabriel.Storage.FileUtils;
import ro.Gabriel.Main.ServerType;
import ro.Gabriel.Main.Minigame;

public class ConfigManager implements Manager {

    private final Minigame plugin;

    private final ServerType serverType;
    private final boolean bungeeCord;
    private final boolean commandsMessagesFromMainPlugin;

    private char altColorCode;

    private final String prefix;

    public ConfigManager(Minigame minigame, DataStorage MDKConfigFile) {
        this.plugin = minigame;

        boolean isMDKPlugin = minigame.getClass().equals(MinigamesDevelopmentKit.class);

        // algorithm to avoid similar paths
        if(!isMDKPlugin) {
            FilePath[] paths = FilePath.values();
            for(int i = 0; i < paths.length; i++) {
                for(int j = i+1; j < paths.length; j++) {
                    if( i != j && paths[i].isSimilar(minigame, MDKConfigFile, paths[j])) {
                        paths[i].resetToDefault(minigame, MDKConfigFile);
                        paths[j].resetToDefault(minigame, MDKConfigFile);
                        MDKConfigFile.save();
                        j=-1;
                    }
                }
            }
        }

        DataStorage config = isMDKPlugin ? MDKConfigFile : this.getStorage(FilePath.config, "config.yml");

        this.serverType = ServerType.of(this.getValue(config, "server-type", "MULTI_ARENA"));
        this.bungeeCord = this.getValue(config, "bungee-cord", false);
        this.commandsMessagesFromMainPlugin = this.getValue(config, "commands-messages-from-main-plugin", true);
        this.altColorCode = this.getValue(config, "alt-color-code", "&").charAt(0);
        //Character s = this.getValue(config, "alt-color-code", '&').charValue();
//        try {
//            this.altColorCode = this.getValue(config, "alt-color-code", '&');
//        } catch (Exception e) {
//            Minigame.MDK_INSTANCE.sendMessage("&4" + e.getMessage());
//            Object o = this.getValue(config, "alt-color-code", '&');
//
//            if(this.getValue(config, "alt-color-code", '&') instanceof String) {
//
//            }
//
//            e.printStackTrace();
//        }

        this.prefix = this.getValue(config, "is-prefix", true)
                ? this.getValue(config, "prefix", ("&e[&6" + minigame.getName() + "&e] "))
                : "";

        config.save();
    }

    @Override
    public Minigame getPlugin() {
        return this.plugin;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(DataStorage file, String path, T defaultValue) {
        T value = (T) file.get(path);

        if(value != null) {
            return value;
        } else {
            file.set(path, defaultValue, false);
            return defaultValue;
        }
    }

    public DataStorage getStorage(FilePath path, String storage) {
        String storagePath = path.getPath();
        storagePath = storagePath + ((!storagePath.replace(" ", "").equals("") && !storagePath.endsWith("\\")) ? "\\" : "");

        boolean fileResource = plugin.getResource(storagePath + (storage + (!storage.contains(".") ? FileUtils.getFileExtension(plugin.getMainStoragePath() + storagePath + storage) : ""))) != null;

        return DataStorage.getStorage(
                plugin,
                storagePath + storage,
                fileResource
        );
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

    public char getAltColorCode() {
        return altColorCode != 0 ? altColorCode : '&';
    }

    public boolean isCommandsMessagesFromMainPlugin() {
        return this.commandsMessagesFromMainPlugin;
    }
}

/*    @SuppressWarnings("unchecked")
    public <T> T getValue(DataStorage file, String path, T defaultValue) {
        T value = (T) file.get(path);

        if(value != null) {
            return value;
        } else {
            file.set(path, defaultValue, false);
            return defaultValue;
        }
    }*/