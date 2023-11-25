package ro.Gabriel.Database.DatabaseImpl;

import ro.Gabriel.Database.DatabaseType;
import ro.Gabriel.Database.Database;
import ro.Gabriel.BuildBattle.Main;
import ro.Gabriel.Language.Keys.MessagesKeys;
import ro.Gabriel.Language.LanguageManager;
import ro.Gabriel.Player.UserManager;
import ro.Gabriel.Storage.FileUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class SqLiteDatabase extends Database {

    private Connection connection;

    public SqLiteDatabase(String databaseFile) throws Exception {
        this.type = DatabaseType.SqLite;

        UserManager.getDefaultUser().sendMessage(MessagesKeys.DATABASE_ATTEMPTING_TO_CONNECT, type.name());
        if (this.connection != null && !this.connection.isClosed()) {
            return;
        }

        Class.forName("org.sqlite.JDBC");
        FileUtils.createFile(databaseFile + ".db");

        this.connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile + ".db");

        UserManager.getDefaultUser().sendMessage(MessagesKeys.DATABASE_SUCCESSFULLY_CONNECTED, type.name());
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public ResultSet select(String select, String table, String condition) {
        return null;
    }
}