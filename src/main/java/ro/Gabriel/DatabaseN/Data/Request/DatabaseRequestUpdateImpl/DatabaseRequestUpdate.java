package ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestUpdateImpl;

import ro.Gabriel.DatabaseN.Data.Request.DatabaseRequest;
import ro.Gabriel.DatabaseN.Structures.DatabaseField;
import ro.Gabriel.DatabaseN.Structures.DatabaseStructure;

//https://www.w3schools.com/sql/sql_orderby.asp
public abstract class DatabaseRequestUpdate extends DatabaseRequest {

    //protected Class<? extends DatabaseStructure> structure;

    /*public static DatabaseRequestUpdate create(Database database, DatabaseStructure structure) {
        DatabaseRequestUpdate request;
        switch (database.getType()) {
            case MySQL:
            case SQLite: {
                request = new SQLDatabaseRequestUpdate();
                break;
            }
            case MongoDB: {
                request = new MongoDatabaseRequestUpdate();
                break;
            }
            default: {
                return null;
            }
        }

        //request.update(null).where(null).equals(null).or(null).greaterThan();


        request.database = database;
        request.structure = structure;

        return request;
    }*/

    public abstract DatabaseRequestUpdate insertInto(Class<? extends DatabaseStructure> structure);
    public abstract DatabaseRequestUpdate insertInto(Class<? extends DatabaseStructure> structure, DatabaseStructure ... data);


    public abstract DatabaseRequestUpdate values(Object ... values);

    public abstract DatabaseRequestUpdate update(DatabaseStructure structure);

    public abstract DatabaseRequestUpdate set(DatabaseField<?> data, Object value);
    public abstract DatabaseRequestUpdate set(DatabaseField<?> ... data);
    public abstract DatabaseRequestUpdate set();// seteaza toate valorile din tabel

    public abstract DatabaseRequestUpdate deleteFrom(Class<? extends DatabaseStructure> structure);



    public abstract DatabaseRequestUpdate where(DatabaseField<?> field);
    public abstract DatabaseRequestUpdate and(DatabaseField<?> field);
    public abstract DatabaseRequestUpdate or(DatabaseField<?> field);

    public abstract DatabaseRequestUpdate isEquals(Object value);
    public abstract DatabaseRequestUpdate isEquals();

    public abstract DatabaseRequestUpdate greaterThan(Object value);
    public abstract DatabaseRequestUpdate greaterThan();

    public abstract DatabaseRequestUpdate lowerThan(Object value);
    public abstract DatabaseRequestUpdate lowerThan();

    public abstract DatabaseRequestUpdate greaterOrEquals(Object value);
    public abstract DatabaseRequestUpdate greaterOrEquals();

    public abstract DatabaseRequestUpdate lowerOrEquals(Object value);
    public abstract DatabaseRequestUpdate lowerOrEquals();

    public abstract DatabaseRequestUpdate between(Object value1, Object value2);
    public abstract DatabaseRequestUpdate between();

    public abstract DatabaseRequestUpdate isNull();
    public abstract DatabaseRequestUpdate isNotNull();


    /*
    public abstract DatabaseRequestUpdate between(Number low, Number high);
    public abstract DatabaseRequestUpdate in(DatabaseStructure ... values);


    public DatabaseRequestUpdate between() {
        return this;
    }

    public DatabaseRequestUpdate equals() {
        return this;
    }*/

    public abstract void build(Object ... parameters);

    //public abstract void build();

    /*
    *
    *
    * DatabaseRequestData<DataPack> request = DatabaseRequest.create(database, structure)
    *       .select(Players.Name, Players.Age, Players.Location)
    *       .where(Players.Wins).greaterThan(100)
    *       .and(Players.GamesPlayed).lowerThan(100);
    *
    * request.build();
    *
    *
    * */
}