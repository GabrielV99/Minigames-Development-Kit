package ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestUpdateImpl;

import ro.Gabriel.DatabaseN.Structures.DatabaseField;
import ro.Gabriel.DatabaseN.Structures.DatabaseStructure;

public class MongoDatabaseRequestUpdate extends DatabaseRequestUpdate {


    @Override
    public DatabaseRequestUpdate insertInto(Class<? extends DatabaseStructure> structure) {
        return null;
    }

    @Override
    public DatabaseRequestUpdate insertInto(Class<? extends DatabaseStructure> structure, DatabaseStructure... data) {
        return null;
    }

    @Override
    public DatabaseRequestUpdate values(Object... values) {
        return null;
    }

    @Override
    public DatabaseRequestUpdate update(DatabaseStructure structure) {
        return null;
    }

    @Override
    public DatabaseRequestUpdate set(DatabaseField<?> data, Object value) {
        return null;
    }

    @Override
    public DatabaseRequestUpdate set(DatabaseField<?>... data) {
        return null;
    }

    @Override
    public DatabaseRequestUpdate set() {
        return null;
    }

    @Override
    public DatabaseRequestUpdate deleteFrom(Class<? extends DatabaseStructure> structure) {
        return null;
    }

    @Override
    public DatabaseRequestUpdate where(DatabaseField<?> field) {
        return null;
    }

    @Override
    public DatabaseRequestUpdate and(DatabaseField<?> field) {
        return null;
    }

    @Override
    public DatabaseRequestUpdate or(DatabaseField<?> field) {
        return null;
    }

    @Override
    public DatabaseRequestUpdate isEquals(Object value) {
        return null;
    }

    @Override
    public DatabaseRequestUpdate isEquals() {
        return null;
    }

    @Override
    public DatabaseRequestUpdate greaterThan(Object value) {
        return null;
    }

    @Override
    public DatabaseRequestUpdate greaterThan() {
        return null;
    }

    @Override
    public DatabaseRequestUpdate lowerThan(Object value) {
        return null;
    }

    @Override
    public DatabaseRequestUpdate lowerThan() {
        return null;
    }

    @Override
    public DatabaseRequestUpdate greaterOrEquals(Object value) {
        return null;
    }

    @Override
    public DatabaseRequestUpdate greaterOrEquals() {
        return null;
    }

    @Override
    public DatabaseRequestUpdate lowerOrEquals(Object value) {
        return null;
    }

    @Override
    public DatabaseRequestUpdate lowerOrEquals() {
        return null;
    }

    @Override
    public DatabaseRequestUpdate between(Object value1, Object value2) {
        return null;
    }

    @Override
    public DatabaseRequestUpdate between() {
        return null;
    }

    @Override
    public DatabaseRequestUpdate isNull() {
        return null;
    }

    @Override
    public DatabaseRequestUpdate isNotNull() {
        return null;
    }

    @Override
    public void build(Object... parameters) {

    }
}
