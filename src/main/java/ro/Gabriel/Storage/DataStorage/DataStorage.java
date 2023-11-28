package ro.Gabriel.Storage.DataStorage;

import ro.Gabriel.Storage.DataStorage.Implementations.FailedDataStorage;
import ro.Gabriel.Class.Validators.DataStorageValidator;
import ro.Gabriel.Misc.ReflectionUtils;
import ro.Gabriel.Storage.FileUtils;
import ro.Gabriel.Main.Minigame;

import java.io.File;
import java.util.Set;

public abstract class DataStorage {
    protected File file;

    private static DataStorage FAILED_DATA_STORAGE;

    private static String MAIN_STORAGE_PATH;

    public DataStorage(File file) {
        this.file = file;
    }

    public abstract boolean contains(String path);

    public abstract DataStorage getSection(String path);

    public abstract Object get(String path);

    public abstract String getString(String path);

    public abstract char getChar(String path);

    public abstract int getInteger(String path);

    public abstract long getLong(String path);

    public abstract double getDouble(String path);

    public abstract float getFloat(String path);

    public abstract boolean getBoolean(String path);

    public abstract Set<String> getKeys(boolean deep);

    public abstract void set(String path, Object value, boolean saveAfter);

    public abstract void save();

    public static DataStorage getStorage(Minigame minigame, String path) {
        return getStorage(minigame, path, false);
    }

    public static DataStorage getStorage(Minigame minigame, String path, boolean resource) {
        try {
            File file = FileUtils.getFile(minigame, path, resource);
            if(file != null) {
                return (DataStorage) ReflectionUtils.getConstructor(minigame.searchClasses(DataStorageValidator.class, file.toString().substring(file.toString().lastIndexOf(".") + 1)).get(0), false, File.class).newInstance(file);
            }
            return getFailedDataStorage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getFailedDataStorage();
    }

    private static DataStorage getFailedDataStorage() {
        return FAILED_DATA_STORAGE != null ? FAILED_DATA_STORAGE : (FAILED_DATA_STORAGE = new FailedDataStorage());
    }

    @Override
    public String toString() {
        return file.toString();
    }

    public File getFile() {
        return this.file;
    }

    public boolean isFailed() {
        return this instanceof FailedDataStorage;
    }
}