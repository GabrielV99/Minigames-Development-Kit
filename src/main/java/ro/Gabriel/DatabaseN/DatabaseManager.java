package ro.Gabriel.DatabaseN;

import ro.Gabriel.DatabaseN.Enums.DatabaseFailEvents;
import ro.Gabriel.DatabaseN.Enums.DatabaseType;

//@ManagerClass(bungee = true, lobby = true, arena = true)
public class DatabaseManager {
    private Database database;// DatabaseDriver

    private static volatile DatabaseManager INSTANCE;

    private DatabaseManager() {
        //this.database = Database.connect();
    }

    public static void load() {
        //MySQL
        /*Database database = Database.connect(
                "db1", "localhost", 3306, "root", "", true, DatabaseType.MySQL, "bazadedate", DatabaseFailEvents.CONNECT_TO_DEFAULT_DISABLE
        );*/

        //SQLite
        Database database = Database.connect(
                "db1", "localhost", 3306, "root", "", true, DatabaseType.SQLite, "bazadedate", DatabaseFailEvents.CONNECT_TO_DEFAULT_DISABLE
        );

        //MongoDB
        /*Database database = Database.connect(
                "sample_weatherdata",
                "localhost",
                27017,
                "MongoDB",
                "USElZ1I8C0huq1SV",
                true,
                DatabaseType.MongoDB,
                "bazadedate",
                DatabaseFailEvents.CONNECT_TO_DEFAULT_DISABLE
        );*/
    }

    private void test() {

        Database database = Database.connect(
                "database", "host", 3306, "user", "password", true, DatabaseType.MySQL, "", DatabaseFailEvents.CONNECT_TO_DEFAULT_DISABLE
        );

    }

    private static DatabaseManager getInstance() {
        synchronized (DatabaseManager.class) {
            if (INSTANCE == null) {
                INSTANCE = new DatabaseManager();
            }
        }
        return INSTANCE;
    }
}
