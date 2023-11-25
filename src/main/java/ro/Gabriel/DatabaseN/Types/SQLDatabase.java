package ro.Gabriel.DatabaseN.Types;

import org.bukkit.plugin.java.JavaPlugin;

import ro.Gabriel.BuildBattle.Main;
import ro.Gabriel.DatabaseN.Database;
import ro.Gabriel.DatabaseN.DatabaseUtils;
import ro.Gabriel.DatabaseN.Structures.DatabaseField;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Predicate;

public abstract class SQLDatabase extends Database {

    protected Connection connection;

    public SQLDatabase(JavaPlugin plugin, Connection connection, t predicate) throws Exception {
        this(plugin, connection, predicate.accept());
    }

    SQLDatabase(JavaPlugin plugin, Connection connection, boolean accept) throws Exception {
        super(plugin);
        if(!accept) {
            return;
        }
        this.connection = connection;

        Statement statement = this.connection.createStatement();

        super.structures.forEach((k, v) -> {
            StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS " + v.getId() + " (");

            for(int i = 0; i < v.getFields().length; i++) {
                DatabaseField<?> field = v.getFields()[i];
                query.append(field.getId()).append(" ").append(DatabaseUtils.typeToSQLData(field.getType())).append((i < v.getFields().length - 1) ? ", " : ");");
            }

            try {
                Main.log("&5SQLITE QUERY: &a" + query.toString());
                statement.executeUpdate(query.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
