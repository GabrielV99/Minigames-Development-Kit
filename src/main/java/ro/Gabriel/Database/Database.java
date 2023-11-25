package ro.Gabriel.Database;

import ro.Gabriel.Data.EntityDataService;
import ro.Gabriel.Database.DatabaseImpl.MySqlDatabase;
import ro.Gabriel.Database.DatabaseImpl.SqLiteDatabase;
import ro.Gabriel.Language.Keys.MessagesKeys;
import ro.Gabriel.Player.UserManager;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.BuildBattle.Main;
import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;

import java.sql.Connection;
import java.sql.ResultSet;

public abstract class Database {

    protected DatabaseType type;

    public static Database getDatabase(DataStorage config) {
//        try {
//            DatabaseType type = DatabaseType.valueOf(config.getString("Database.Type"));
//            return getDatabase(type, config);
//        } catch (Exception e) {
//            databaseFail();
//            return null;
//        }

        return null;
    }

    private static Database getDatabase(DatabaseType type, DataStorage config) {
//        try {
//            // aici (in loc de acest switch) o sa creez o clasa "DatabaseManager" in care o sa pun o lista sau hashmap in care vor intra toate clasele care extind sau
//            // implementeaza clasa "Database" (practic toate clasele care reprezinta o baza de date anume) si aatunci cand cineva creeaza o
//            // implementare proprie de baze de date, dupa ID-ul care va fi returnat la o functie implementata in clasa respectiva
//            // va putea fi gasit aici.
//            switch (type) {
//                case MySql: {
//                    //return new MySqlDatabase(config);
//                }
//                default:
//                case SqLite: {
//                   // String file = config.getString("Database.File");
//                    //return new SqLiteDatabase(!DataDefaultValues.equals(file, String.class) ? file : "SqLiteDatabase");
//                }
//            }
//        } catch (Exception e) {
//            try {
//                UserManager.getDefaultUser().sendMessage(MessagesKeys.DATABASE_COULD_NOT_CONNECT, config.getString("Database.Type"));
//
//                if(type.equals(DatabaseType.SqLite)) {
//                    //databaseFail();
//                    return null;
//                }
//                UserManager.getDefaultUser().sendMessage(MessagesKeys.DATABASE_SWITCH, DatabaseType.SqLite.name());
//
//                //return getDatabase(DatabaseType.SqLite, config);
//            } catch (Exception e1) {
//                //databaseFail();
//                return null;
//            }
//        }
        return null;
    }

    public abstract Connection getConnection();

    public abstract ResultSet select(String select, String table, String condition);

    public boolean isConnected() {
        try {
            return getConnection() != null && !getConnection().isClosed();
        } catch (Exception e) {
            return false;
        }
    }

    public EntityDataService getEntityDataService(String table) {
        return this.type.getEntityDataService(table);
    }

    private static void databaseFail() {
        UserManager.getDefaultUser().sendMessage(MessagesKeys.DATABASE_FAILED_TO_CONNECT);
        Main.stop();
    }
}