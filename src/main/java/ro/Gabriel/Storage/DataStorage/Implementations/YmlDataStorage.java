package ro.Gabriel.Storage.DataStorage.Implementations;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.ConfigurationSection;

import ro.Gabriel.Storage.Annotations.StorageType;
import ro.Gabriel.Storage.DataStorage.DataStorage;

import java.io.File;
import java.io.IOException;
import java.util.Set;

@StorageType(extension = "yml")
public class YmlDataStorage extends DataStorage {

    private ConfigurationSection fileConfiguration;

    public YmlDataStorage(File file) {
        super(file);
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public boolean contains(String path) {
        return fileConfiguration.contains(path);
    }

    private YmlDataStorage(DataStorage baseFileStorage, String path) throws Exception {
        super(baseFileStorage.getFile());
        this.fileConfiguration = ((YmlDataStorage)baseFileStorage).fileConfiguration.getConfigurationSection(path);
        if(this.fileConfiguration == null) {
            throw new Exception("path is null...");
            //this.fileConfiguration = ((YmlDataStorage) baseFileStorage).fileConfiguration;
        }
    }

    @Override
    public DataStorage getSection(String path) {
        try {
            return new YmlDataStorage(this, path);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object get(String path) {
        return this.fileConfiguration.get(path);
    }

    @Override
    public String getString(String path) {
        return this.fileConfiguration.getString(path);
    }

    @Override
    public char getChar(String path) {
        return this.fileConfiguration.getString(path).charAt(0);
    }

    @Override
    public int getInteger(String path) {
        return this.fileConfiguration.getInt(path);
    }

    @Override
    public long getLong(String path) {
        return this.fileConfiguration.getLong(path);
    }

    @Override
    public double getDouble(String path) {
        return this.fileConfiguration.getDouble(path);
    }

    @Override
    public float getFloat(String path) {
        return (float) this.fileConfiguration.getDouble(path);
    }

    @Override
    public boolean getBoolean(String path) {
        return this.fileConfiguration.getBoolean(path);
    }

    @Override
    public Set<String> getKeys(boolean deep) {
        return fileConfiguration.getKeys(deep);
    }

    @Override
    public void set(String path, Object value, boolean saveAfter) {
        this.fileConfiguration.set(path, value);
        if(saveAfter) {
            this.save();
        }
    }

    @Override
    public void save() {
        try {
            ((FileConfiguration)this.fileConfiguration).save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getExtension() {
        StorageType storageTypeAnnotation = YmlDataStorage.class.getAnnotation(StorageType.class);
        return storageTypeAnnotation != null ? storageTypeAnnotation.extension().replace(".", "") : "";
    }
}