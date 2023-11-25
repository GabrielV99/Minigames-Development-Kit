package ro.Gabriel.DatabaseN.Structures;

import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;

public class DatabaseField<T> {

    private final String id;
    private final Class<T> type;
    private final T defaultValue;

    public DatabaseField(String id, Class<T> type, T defaultValue) {
        this.id = id;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public DatabaseField(String id, Class<T> type) {
        this(id, type, DataDefaultValues.get(type));
    }

    public String getId() {
        return this.id;
    }

    public Class<T> getType() {
        return this.type;
    }

    public T getDefaultValue() {
        return this.defaultValue;
    }

    public static <T> DatabaseField<T> create(String id, Class<T> type, T defaultValue) {
        return new DatabaseField<>(id, type, defaultValue);
    }

    public static <T> DatabaseField<T> create(String id, Class<T> type) {
        return new DatabaseField<>(id, type);
    }
}