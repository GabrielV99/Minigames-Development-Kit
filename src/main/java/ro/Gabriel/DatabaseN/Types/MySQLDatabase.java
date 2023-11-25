package ro.Gabriel.DatabaseN.Types;

import org.bukkit.plugin.java.JavaPlugin;

import ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestUpdateImpl.SQLDatabaseRequestUpdate;
import ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestDataImpl.SQLDatabaseRequestData;
import ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestUpdateImpl.DatabaseRequestUpdate;
import ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestDataImpl.DatabaseRequestData;
import ro.Gabriel.Class.Validators.DatabaseStructureValidator;
import ro.Gabriel.DatabaseN.Structures.DatabaseField;
import ro.Gabriel.DatabaseN.Structures.DatabaseStructure;
import ro.Gabriel.DatabaseN.Enums.DatabaseType;
import ro.Gabriel.DatabaseN.DatabaseUtils;
import ro.Gabriel.DatabaseN.Database;
import ro.Gabriel.Class.ClassManager;
import ro.Gabriel.Misc.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class MySQLDatabase extends SQLDatabase {

    public MySQLDatabase(JavaPlugin plugin, String database, String host, int port, String user, String password, boolean ssl) throws Exception {
        super(plugin, DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&user=" + user + "&password=" + password + "&useSSL=" + ssl), () ->{
            Class.forName("com.mysql.jdbc.Driver");
            return true;
        });
    }

    @Override
    public DatabaseRequestData requestData(Class<? extends DatabaseStructure> structure) {
        return new SQLDatabaseRequestData(this.connection, structures.get(structure));
    }

    @Override
    public DatabaseRequestUpdate requestUpdateData() {
        return new SQLDatabaseRequestUpdate();
    }

    @Override
    public DatabaseType getType() {
        return DatabaseType.MySQL;
    }
}