package ro.Gabriel.Main.Config;

import ro.Gabriel.Main.Minigame;
import ro.Gabriel.Storage.DataStorage.DataStorage;

public enum FilePath {
    config("config", ""),
    language("language", "Language");

    private final String file, defaultValue;
    private String value;

    FilePath(String file, String defaultValue) {
        this.file = file;
        this.defaultValue = defaultValue;
    }

    public String getPath() {
        return this.value != null ? this.value : this.defaultValue;
    }

    private String getPath(Minigame minigame, DataStorage MDKConfigFile) {
        String path = MDKConfigFile.getString("files-path.config-path-plugin." + minigame.getName() + "." + this.file + "-path");
        if(path == null) {
            path = MDKConfigFile.getString("files-path." + this.file + "-path");
        }
        if(path == null) {
            this.resetToDefault(minigame, MDKConfigFile);
            return this.defaultValue;
        }
        return (this.value = path);
    }

    public boolean isSimilar(Minigame minigame, DataStorage MDKConfigFile, FilePath path) {
        String path1 = this.getPath(minigame, MDKConfigFile);
        String path2 = path.getPath(minigame, MDKConfigFile);

        //Minigame.MDK_INSTANCE.sendMessage( path1.replace("/", "").replace("\\", "") + "==" + path2.replace("/", "").replace("\\", ""));

        return path1.replace("/", "").replace("\\", "").equals(path2.replace("/", "").replace("\\", ""));
    }

    public void resetToDefault(Minigame minigame, DataStorage MDKConfigFile) {
        MDKConfigFile.set("files-path.config-path-plugin." + minigame.getName() + "." + this.file + "-path", this.defaultValue, false);
        this.value = this.defaultValue;
    }
}
