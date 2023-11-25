package ro.Gabriel.DatabaseN.Enums;

public enum DatabaseType {
    MySQL, SQLite, MongoDB;

    public static DatabaseType getDefaultType() {
        return DatabaseType.SQLite;
    }

    public static DatabaseType get(String type) {
        type = type.toUpperCase();

        DatabaseType[] types = DatabaseType.values();

        for(DatabaseType databaseType : types) {
            if(databaseType.name().toUpperCase().equals(type)) {
                return databaseType;
            }
        }

        return getDefaultType();
    }
}