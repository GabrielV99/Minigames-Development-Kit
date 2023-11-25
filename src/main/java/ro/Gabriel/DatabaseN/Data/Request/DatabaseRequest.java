package ro.Gabriel.DatabaseN.Data.Request;

import ro.Gabriel.DatabaseN.Database;
import ro.Gabriel.DatabaseN.Structures.DatabaseField;
import ro.Gabriel.DatabaseN.Structures.DatabaseStructure;

public abstract class DatabaseRequest {
    protected Database database;
    protected DatabaseStructure structure;
}
