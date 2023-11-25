package ro.Gabriel.DatabaseN;

import org.bukkit.plugin.java.JavaPlugin;
import ro.Gabriel.BuildBattle.Main;
import ro.Gabriel.Class.ClassManager;
import ro.Gabriel.Class.Validators.DatabaseStructureValidator;
import ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestDataImpl.DatabaseRequestData;
import ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestUpdateImpl.DatabaseRequestUpdate;
import ro.Gabriel.DatabaseN.Enums.DatabaseFailEvents;
import ro.Gabriel.DatabaseN.Enums.DatabaseType;
import ro.Gabriel.DatabaseN.Structures.DatabaseStructure;
import ro.Gabriel.DatabaseN.Types.MongoDBDatabase;
import ro.Gabriel.DatabaseN.Types.MySQLDatabase;
import ro.Gabriel.DatabaseN.Types.SQLiteDatabase;
import ro.Gabriel.Language.Keys.MessagesKeys;
import ro.Gabriel.Misc.ReflectionUtils;
import ro.Gabriel.Player.UserManager;
import ro.Gabriel.Storage.DataStorage.DataStorage;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Database {

    protected String database, host, user, password;
    protected int port;
    protected boolean ssl;

    protected Map<Class<? extends DatabaseStructure>, DatabaseStructure> structures;

    public Database(JavaPlugin plugin) {
        this.structures = ClassManager.searchClasses(plugin, DatabaseStructureValidator.class).stream()
                .collect(Collectors.toMap(k -> (Class<? extends DatabaseStructure>)k, v ->  {
                    try {
                        return (DatabaseStructure) ReflectionUtils.instantiateObject(v, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }));
    }

    public static Database connect(String database, String host, int port, String user, String password, boolean ssl, DatabaseType type, String localDatabaseFile, DatabaseFailEvents failEvents) {
        try {
            UserManager.getDefaultUser().sendMessage(MessagesKeys.DATABASE_ATTEMPTING_TO_CONNECT, type.name());

            /*StackTraceElement[] elements = new Exception().getStackTrace();
            Main.log("LENGTH: " + elements.length);
            for(StackTraceElement e : elements) {
                Main.log("&a1.Element: &3" + e);
                Main.log("&a2.ElementClassName: &3" + e.getClassName());
                Main.log("&a3.ElementFileName: &3" + e.getFileName());
            }*/


            Class<?> main = Arrays.stream(new Exception().getStackTrace())
                    .map(stackTraceElement -> {
                        try {
                            String clazz = stackTraceElement.toString().split("\\(")[0];
                            return Class.forName(clazz.substring(0, clazz.lastIndexOf(".")));
                        } catch (ClassNotFoundException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(c -> c.getSuperclass() != null && c.getSuperclass().equals(JavaPlugin.class))
                    .collect(Collectors.toList()).get(0);

            Main.log("&eMain: &3" + main);

            JavaPlugin plugin = JavaPlugin.getPlugin((Class<? extends JavaPlugin>)main);

            Database database1 = null;
            switch (type) {
                case MySQL:
                    database1 = new MySQLDatabase(plugin, database, host, port, user, password, ssl);
                    break;
                case SQLite:
                    database1 = new SQLiteDatabase(plugin, localDatabaseFile);
                    break;
                case MongoDB:
                    database1 = new MongoDBDatabase(plugin, database, host, port, user, password, ssl);
                    break;

                default:
                    break;
            }

            UserManager.getDefaultUser().sendMessage(MessagesKeys.DATABASE_SUCCESSFULLY_CONNECTED, type.name());

            database1.database = database;
            database1.host = host;
            database1.user = user;
            database1.password = password;
            database1.port = port;
            database1.ssl = ssl;

            return database1;
        } catch (Exception e) {
            e.printStackTrace();

            if(type != DatabaseType.getDefaultType()) {
                UserManager.getDefaultUser().sendMessage(MessagesKeys.DATABASE_COULD_NOT_CONNECT, type.name());
            }

            switch (failEvents) {
                case CONNECT_TO_DEFAULT_STOP:
                    if(type != DatabaseType.getDefaultType()) {
                        UserManager.getDefaultUser().sendMessage(MessagesKeys.DATABASE_SWITCH, DatabaseType.SQLite.name());
                        return connect(database, host, port, user, password, ssl, DatabaseType.SQLite, localDatabaseFile, failEvents);
                    }
                    UserManager.getDefaultUser().sendMessage(MessagesKeys.DATABASE_FAILED_TO_CONNECT);
                    Main.stop();
                    break;
                case CONNECT_TO_DEFAULT_DISABLE:
                    if(type != DatabaseType.getDefaultType()) {
                        UserManager.getDefaultUser().sendMessage(MessagesKeys.DATABASE_SWITCH, DatabaseType.SQLite.name());
                        return connect(database, host, port, user, password, ssl, DatabaseType.SQLite, localDatabaseFile, failEvents);
                    }
                    UserManager.getDefaultUser().sendMessage(MessagesKeys.DATABASE_FAILED_TO_CONNECT);
                    Main.disable();
                    break;
                case STOP:
                    UserManager.getDefaultUser().sendMessage(MessagesKeys.DATABASE_FAILED_TO_CONNECT);
                    Main.stop();
                    break;
                case DISABLE:
                    UserManager.getDefaultUser().sendMessage(MessagesKeys.DATABASE_FAILED_TO_CONNECT);
                    Main.disable();
                    break;
                case CONTINUE:
                    if(type == DatabaseType.getDefaultType()) {
                        UserManager.getDefaultUser().sendMessage(MessagesKeys.DATABASE_COULD_NOT_CONNECT, type.name());
                    }
                    break;
            }
        }

        return null;
    }
    public static Database connect(String localDatabaseFile, DatabaseFailEvents failEvents) {
        return connect("database", "host", 0, "user", "password", true, DatabaseType.SQLite, localDatabaseFile, failEvents);
    }

    public static Database connect(String database, int port, String user, String password, boolean ssl, DatabaseType type, String localDatabaseFile, DatabaseFailEvents failEvents) { return connect(database, "localhost", port, user, password, ssl, type, localDatabaseFile, failEvents); }
    public static Database connect(String database, String host, int port, String user, String password, DatabaseType type, String localDatabaseFile, DatabaseFailEvents failEvents) { return connect(database, host, port, user, password, false, type, localDatabaseFile, failEvents); }

    public static Database connect(String database, String host, int port, String user, String password, boolean ssl, String localDatabaseFile, DatabaseFailEvents failEvents) { return connect(database, host, port, user, password, ssl, DatabaseType.SQLite, localDatabaseFile, failEvents); }
    public static Database connect(String database, int port, String user, String password, boolean ssl, String localDatabaseFile, DatabaseFailEvents failEvents) { return connect(database, "localhost", port, user, password, ssl, localDatabaseFile, failEvents); }
    public static Database connect(String database, String host, int port, String user, String password, String localDatabaseFile, DatabaseFailEvents failEvents) { return connect(database, host, port, user, password, false, localDatabaseFile, failEvents); }

    public static Database connect(DataStorage dataStorage) {
        return connect(
                dataStorage.getString("database.database"),
                dataStorage.getString("database.host"),
                DatabaseUtils.getPort(dataStorage.get("database.port")),
                dataStorage.getString("database.user"),
                dataStorage.getString("database.password"),
                DatabaseUtils.getSsl(dataStorage.get("database.ssl")),
                DatabaseType.get(dataStorage.getString("database.type")),
                dataStorage.getString("database.local-database-file"),
                DatabaseFailEvents.get(dataStorage.get("database.database-fail-event"))
        );
    }

    public abstract DatabaseRequestData requestData(Class<? extends DatabaseStructure> structure);
    public abstract DatabaseRequestUpdate requestUpdateData();

    public String getDatabase() {
        return this.database;
    }

    public String getHost() {
        return this.host;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

    public int getPort() {
        return this.port;
    }

    public boolean ssl() {
        return this.ssl;
    }

    public abstract DatabaseType getType();
}