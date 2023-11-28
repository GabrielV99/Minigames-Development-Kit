package ro.Gabriel.Storage.DataStorage.Implementations;

import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;
import ro.Gabriel.Storage.DataStorage.DataStorage;

import java.util.Set;

public class FailedDataStorage extends DataStorage {
    public FailedDataStorage() {
        super(null);
    }

    @Override
    public boolean contains(String path) {
        return false;
    }

    @Override
    public DataStorage getSection(String path) {
        return this;
    }

    @Override
    public Object get(String path) {
        return this;
    }

    @Override
    public String getString(String path) {
        return DataDefaultValues.get(String.class);
    }

    @Override
    public char getChar(String path) {
        return DataDefaultValues.get(char.class);
    }

    @Override
    public int getInteger(String path) {
        return DataDefaultValues.get(int.class);
    }

    @Override
    public long getLong(String path) {
        return DataDefaultValues.get(long.class);
    }

    @Override
    public double getDouble(String path) {
        return DataDefaultValues.get(double.class);
    }

    @Override
    public float getFloat(String path) {
        return DataDefaultValues.get(float.class);
    }

    @Override
    public boolean getBoolean(String path) {
        return DataDefaultValues.get(boolean.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<String> getKeys(boolean deep) {
        return DataDefaultValues.get(Set.class);
    }

    @Override
    public void set(String path, Object value, boolean saveAfter) { }

    @Override
    public void save() { }
}