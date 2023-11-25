package ro.Gabriel.DatabaseN.Structures;

@DatabaseStructureId(id = "plm")
public class PlayersDatabaseStructure extends DatabaseStructure {

    @DatabaseStructurePrimaryKey
    public static DatabaseField<String> UUID = DatabaseField.create("uuid", String.class);

    public static DatabaseField<String> NAME = DatabaseField.create("name", String.class);
    public static DatabaseField<String> LOAD_OUT = DatabaseField.create("load_out", String.class);
    public static DatabaseField<String> DATA = DatabaseField.create("data", String.class);
    public static DatabaseField<String> PURCHASED_MUSICS = DatabaseField.create("purchased_items", String.class);
    public static DatabaseField<String> SORT_OIF = DatabaseField.create("sort_oif", String.class);
    public static DatabaseField<String> BACKDROP_PICKER = DatabaseField.create("backdrop_picker", String.class);
    public static DatabaseField<String> VICTORY_DANCES = DatabaseField.create("victory_dances", String.class);
    public static DatabaseField<String> HATS = DatabaseField.create("hats", String.class);
    public static DatabaseField<String> SUITS = DatabaseField.create("suits", String.class);
    public static DatabaseField<Boolean> IS_ONLINE = DatabaseField.create("is_online", boolean.class);
    public static DatabaseField<String> JOINED_ARENA = DatabaseField.create("joined_arena", String.class);
    public static DatabaseField<String> PARTY = DatabaseField.create("party", String.class);
    public static DatabaseField<String> QUEST_MASTER = DatabaseField.create("quest_master", String.class);
}