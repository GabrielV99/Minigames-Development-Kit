package ro.Gabriel.DatabaseN.Types;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.ImmutableList;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.*;
import org.bson.Document;

import ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestUpdateImpl.MongoDatabaseRequestUpdate;
import ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestDataImpl.MongoDatabaseRequestData;
import ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestUpdateImpl.DatabaseRequestUpdate;
import ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestDataImpl.DatabaseRequestData;
import ro.Gabriel.Class.Validators.DatabaseStructureValidator;
import ro.Gabriel.DatabaseN.Structures.DatabaseStructure;
import ro.Gabriel.DatabaseN.Enums.DatabaseType;
import ro.Gabriel.DatabaseN.DatabaseUtils;
import ro.Gabriel.Class.ClassManager;
import ro.Gabriel.DatabaseN.Database;

import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

public class MongoDBDatabase extends Database  {

    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public MongoDBDatabase(JavaPlugin plugin, String database, String host, int port, String user, String password, boolean ssl) throws Exception {
        super(plugin);
        //this.mongoClient = MongoClients.create("mongodb://" + user + ":" + password + "@" + host + ":" + port + "/?maxPoolSize=20&w=majority&useSsl=" + ssl);
        this.mongoClient = MongoClients.create("mongodb+srv://" + user + ":" + password + "@spigotcluster.kcvpmsj.mongodb.net/?retryWrites=true&w=majority");
        this.database = mongoClient.getDatabase(database);

        //ClassManager.searchClasses(plugin, DatabaseEntityValidator.class).forEach(cl -> Main.log("&aENTITY CLAZZZZZZZZ: &c" + cl));

        ClassManager.searchClasses(plugin, DatabaseStructureValidator.class, DatabaseStructure.class).forEach(structure -> {
            String id = DatabaseUtils.getStructureId(structure);

            if(!ImmutableList.copyOf(MongoDBDatabase.this.database.listCollectionNames().iterator()).contains(id)) {
                MongoDBDatabase.this.database.createCollection(id);
            }

            MongoCollection<Document> collection = MongoDBDatabase.this.database.getCollection(id);

            List<String> indexes = ImmutableList.copyOf(collection.listIndexes().iterator())
                    .stream().map(document -> document.getString("name")).collect(Collectors.toList());

            Arrays.asList(structure.getEnumConstants()).forEach(field -> {
                if(!indexes.contains(field.getId() + "_1")) {
                    collection.createIndex(Indexes.ascending(field.getId()));
                }
            });
        });
    }

    @Override
    public DatabaseRequestData requestData(Class<? extends DatabaseStructure> structure) {
        return new MongoDatabaseRequestData(structures.get(structure));
    }

    @Override
    public DatabaseRequestUpdate requestUpdateData() {
        return new MongoDatabaseRequestUpdate();
    }

    @Override
    public DatabaseType getType() {
        return DatabaseType.MongoDB;
    }
}