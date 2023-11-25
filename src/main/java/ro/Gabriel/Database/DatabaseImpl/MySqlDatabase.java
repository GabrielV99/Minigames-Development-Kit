package ro.Gabriel.Database.DatabaseImpl;

import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Language.Keys.MessagesKeys;
import ro.Gabriel.Database.DatabaseType;
import ro.Gabriel.Player.UserManager;
import ro.Gabriel.Database.Database;

import java.sql.*;

public class MySqlDatabase extends Database {

    private Connection connection;

    public MySqlDatabase(DataStorage config) throws Exception {
        this.type = DatabaseType.MySql;

        UserManager.getDefaultUser().sendMessage(MessagesKeys.DATABASE_ATTEMPTING_TO_CONNECT, type.name());
        if (this.connection != null && !this.connection.isClosed()) {
            return;
        }
        Class.forName("com.mysql.jdbc.Driver");

        this.connection = DriverManager.getConnection(
                "jdbc:mysql://" + config.getString("Database.Host") +
                        ":" + config.getInteger("Database.Port") +
                        "/" + config.getString("Database.Database") +
                        "?autoReconnect=true&user=" + config.getString("Database.User") +
                        "&password=" + config.getString("Database.Password") +
                        "&useSSL=" + config.getBoolean("Database.SSL")
        );

        UserManager.getDefaultUser().sendMessage(MessagesKeys.DATABASE_SUCCESSFULLY_CONNECTED, type.name());
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public ResultSet select(String select, String table, String condition) {
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT " + select + " FROM " + table + " " + condition + ";")){
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getData(String structureID, String entityID) {
        // "get (a, b, c, d, e, f, g) where: "

        /*
        *
        * public abstract class DatabaseRequest {
        *
        *       private String Id;
        *
        *       public DatabaseRequest(String Id) {
        *           this.Id = Id;
        *       }
        *
        *       public abstract void execute();
        *
        *       public String getId() {
        *           return this.Id;
        *       }
        * }
        *
        *
        *
        * */
    }
}