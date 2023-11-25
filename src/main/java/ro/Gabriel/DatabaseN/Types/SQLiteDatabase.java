package ro.Gabriel.DatabaseN.Types;

import org.bukkit.plugin.java.JavaPlugin;

import ro.Gabriel.BuildBattle.Main;
import ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestUpdateImpl.SQLDatabaseRequestUpdate;
import ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestDataImpl.SQLDatabaseRequestData;
import ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestUpdateImpl.DatabaseRequestUpdate;
import ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestDataImpl.DatabaseRequestData;
import ro.Gabriel.Class.Validators.DatabaseStructureValidator;
import ro.Gabriel.DatabaseN.Structures.DatabaseField;
import ro.Gabriel.DatabaseN.Structures.DatabaseStructure;
import ro.Gabriel.DatabaseN.Enums.DatabaseType;
import ro.Gabriel.DatabaseN.DatabaseUtils;
import ro.Gabriel.Class.ClassManager;
import ro.Gabriel.DatabaseN.Database;
import ro.Gabriel.Misc.ReflectionUtils;
import ro.Gabriel.Storage.FileUtils;

import java.lang.reflect.Field;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SQLiteDatabase extends SQLDatabase {

    public SQLiteDatabase(JavaPlugin plugin, String databaseFile) throws Exception {
        super(plugin, DriverManager.getConnection("jdbc:sqlite:" + databaseFile + ".db"), () -> {
            Class.forName("org.sqlite.JDBC");
            FileUtils.createFile(databaseFile + ".db");
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
        return DatabaseType.SQLite;
    }
}