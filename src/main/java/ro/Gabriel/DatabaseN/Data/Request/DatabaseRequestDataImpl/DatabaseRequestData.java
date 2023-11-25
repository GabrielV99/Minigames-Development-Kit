package ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestDataImpl;

import ro.Gabriel.DatabaseN.Data.Request.DatabaseRequest;
import ro.Gabriel.DatabaseN.Structures.DatabaseField;
import ro.Gabriel.DatabaseN.Structures.DatabaseStructure;
import ro.Gabriel.DatabaseN.Data.DataPack;

public abstract class DatabaseRequestData extends DatabaseRequest {

    protected boolean primaryKeyRequest;

    public DatabaseRequestData(DatabaseStructure structure) {
        this.structure = structure;
    }

    public DatabaseRequestData primaryKeyRequest(boolean primaryKeyRequest) {
        this.primaryKeyRequest = primaryKeyRequest;
        return this;
    }

    public abstract DatabaseRequestData select();
    public abstract DatabaseRequestData select(DatabaseField<?>... fields);

    public abstract DatabaseRequestData selectDistinct();
    public abstract DatabaseRequestData selectDistinct(DatabaseField<?> ... fields);

    public abstract DatabaseRequestData top(int top);
    public abstract DatabaseRequestData topPercent(int percent);

    public abstract DatabaseRequestData from(Class<? extends DatabaseStructure> structure);

    public abstract DatabaseRequestData where(DatabaseField<?> field);

    public abstract DatabaseRequestData and(DatabaseField<?> field);
    public abstract DatabaseRequestData or(DatabaseField<?> field);

    public abstract DatabaseRequestData isEquals(Object value);
    public abstract DatabaseRequestData isEquals();

    public abstract DatabaseRequestData greaterThan(Object value);
    public abstract DatabaseRequestData greaterThan();

    public abstract DatabaseRequestData lowerThan(Object value);
    public abstract DatabaseRequestData lowerThan();

    public abstract DatabaseRequestData greaterOrEquals(Object value);
    public abstract DatabaseRequestData greaterOrEquals();

    public abstract DatabaseRequestData lowerOrEquals(Object value);
    public abstract DatabaseRequestData lowerOrEquals();

    public abstract DatabaseRequestData between(Object value1, Object value2);
    public abstract DatabaseRequestData between();

    public abstract DatabaseRequestData in(DatabaseField<?> ... fields);
    public abstract DatabaseRequestData notIn(DatabaseField<?> ... fields);

    public abstract DatabaseRequestData isNull();
    public abstract DatabaseRequestData isNotNull();

    public abstract DatabaseRequestData groupBy(DatabaseField<?> field);
    public abstract DatabaseRequestData orderBy(DatabaseField<?> field);

    public abstract DataPack build(Object... values);
}