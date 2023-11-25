package ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestUpdateImpl;

import ro.Gabriel.BuildBattle.Main;
import ro.Gabriel.DatabaseN.DatabaseUtils;
import ro.Gabriel.DatabaseN.Structures.DatabaseField;
import ro.Gabriel.DatabaseN.Structures.DatabaseStructure;

public class SQLDatabaseRequestUpdate extends DatabaseRequestUpdate {

    private final StringBuilder query = new StringBuilder();

    int startIndex = 0, endIndex = 0;

    @Override
    public DatabaseRequestUpdate insertInto(Class<? extends DatabaseStructure> structure) {
        //this.structure = structure;
        this.query.append("INSERT INTO ").append(DatabaseUtils.getStructureId(structure)).append(" VALUES(");

        DatabaseStructure[] fields = DatabaseUtils.getDatabaseFields(structure);

        for(int i = 0; i < fields.length; i++) {
            this.query.append("%value_").append(i).append("%").append(i < fields.length - 1 ? ", " : ") ");
        }
        this.endIndex += fields.length;

        return this;
    }

    @Override
    public DatabaseRequestUpdate insertInto(Class<? extends DatabaseStructure> structure, DatabaseStructure... fields) {
        this.query.append("INSERT INTO ").append(DatabaseUtils.getStructureId(structure)).append(" (");

        StringBuilder values = new StringBuilder("VALUES(");

        for(int i = 0; i < fields.length; i++) {
            DatabaseStructure field = fields[i];
            if(field.getClass().isEnum()) {
                this.query.append((this.startIndex = Math.abs(i - Math.abs(this.endIndex))) > 0 ? ", " : "")
                        .append(field.getId())
                        .append(i == fields.length - 1 ? ") " : "");
                values.append("%value_").append(this.startIndex).append("%").append(i < fields.length - 1 ? ", " : ") ");
            } else {
                this.endIndex--;
                if(i == fields.length - 1) {
                    this.query.append(") ");
                    values.append(") ");
                }
            }
        }
        this.startIndex = 0;
        this.endIndex += fields.length;

        this.query.append(values);

        return this;
    }

    @Override
    public DatabaseRequestUpdate values(Object... values) {
        int startPlaceholderIndex;
        for(Object value : values) {
            if(this.startIndex == this.endIndex) {
                break;
            }
            startPlaceholderIndex = this.query.indexOf("%value_" + this.startIndex + "%");
            this.query.replace(
                    startPlaceholderIndex,
                    startPlaceholderIndex + ("%value_" + this.startIndex + "%").length(),
                    (String) value
            );
            this.startIndex++;
        }
        return this;
    }

    @Override
    public DatabaseRequestUpdate update(DatabaseStructure structure) {
        this.structure = structure;
        this.query.append("UPDATE ").append(structure.getId()).append(" ");
        return this;
    }

    @Override
    public DatabaseRequestUpdate set(DatabaseField<?> data, Object value) {
        this.query.append(!this.query.toString().contains("SET") ? "SET " : ", ")
                .append(data.getId()).append("=").append(value);
        return this;
    }

    @Override
    public DatabaseRequestUpdate set(DatabaseField<?>... data) {
        for (DatabaseField<?> field : data) {
            this.set(field, "%value_" + endIndex++ + "%");
        }
        return this;
    }

    @Override
    public DatabaseRequestUpdate set() {
        if(this.structure != null) {
            this.set(this.structure.getFields());
        }
        return this;
    }

    @Override
    public DatabaseRequestUpdate deleteFrom(Class<? extends DatabaseStructure> structure) {
        this.query.append("DELETE FROM ").append(DatabaseUtils.getStructureId(structure));
        return this;
    }

    @Override
    public DatabaseRequestUpdate where(DatabaseField<?> field) {
        this.query.append(" WHERE ").append(field.getId());
        return this;
    }

    @Override
    public DatabaseRequestUpdate and(DatabaseField<?> field) {
        this.query.append(" AND ").append(field.getId());
        return this;
    }

    @Override
    public DatabaseRequestUpdate or(DatabaseField<?> field) {
        this.query.append(" OR ").append(field.getId());
        return this;
    }

    @Override
    public DatabaseRequestUpdate isEquals(Object value) {
        this.query.append("=").append(value.toString());
        return this;
    }

    @Override
    public DatabaseRequestUpdate isEquals() {
        return this.isEquals("%value_" + this.endIndex++ + "%");
    }

    @Override
    public DatabaseRequestUpdate greaterThan(Object value) {
        this.query.append(">").append(value.toString());
        return this;
    }

    @Override
    public DatabaseRequestUpdate greaterThan() {
        return this.greaterThan("%value_" + this.endIndex++ + "%");
    }

    @Override
    public DatabaseRequestUpdate lowerThan(Object value) {
        this.query.append("<").append(value.toString());
        return this;
    }

    @Override
    public DatabaseRequestUpdate lowerThan() {
        return this.lowerThan("%value_" + this.endIndex++ + "%");
    }

    @Override
    public DatabaseRequestUpdate greaterOrEquals(Object value) {
        this.query.append(">=").append(value.toString());
        return this;
    }

    @Override
    public DatabaseRequestUpdate greaterOrEquals() {
        return this.greaterOrEquals("%value_" + this.endIndex++ + "%");
    }

    @Override
    public DatabaseRequestUpdate lowerOrEquals(Object value) {
        this.query.append("<=").append(value.toString());
        return this;
    }

    @Override
    public DatabaseRequestUpdate lowerOrEquals() {
        return this.lowerOrEquals("%value_" + this.endIndex++ + "%");
    }

    @Override
    public DatabaseRequestUpdate between(Object value1, Object value2) {
        this.query.append(" BETWEEN ").append(value1.toString()).append(" AND ").append(value2.toString());
        return this;
    }

    @Override
    public DatabaseRequestUpdate between() {
        return this.between("%value_" + this.endIndex++ + "%", "%value_" + this.endIndex++ + "%");
    }

    @Override
    public DatabaseRequestUpdate isNull() {
        this.query.append(" IS NULL");
        return this;
    }

    @Override
    public DatabaseRequestUpdate isNotNull() {
        this.query.append(" IS NOT NULL");
        return this;
    }

    @Override
    public void build(Object... values) {
        int replaceValues = Math.max((this.endIndex - this.startIndex), 0);

        if(replaceValues >= values.length) {
            return;
        }

        String query = this.query.toString();
        int index = 0;
       for(int i = this.startIndex; i < this.endIndex; i++) {
           query = query.replace(("%value_" + i + "%"), values[index++].toString());
       }

        Main.log("&3Query: &c" + query);
    }
}
/*@Override
    public DatabaseRequestUpdate insertInto(Class<? extends DatabaseStructure> structure, DatabaseStructure... fields) {
        this.query.append("INSERT INTO ").append(DatabaseUtils.getStructureId(structure)).append(" (");

        StringBuilder values = new StringBuilder("VALUES(");
        for(int i = 0; i < fields.length; i++) {
            DatabaseStructure field = fields[i];
            if(field.getClass().isEnum()) {
                this.query.append(field.getId())
                        .append(i < fields.length - 1 ? ", " : ") ");
                values.append("%value_").append(i).append("%").append(i < fields.length - 1 ? ", " : ") ");
            } else {
                this.endIndex--;
                if(i == fields.length - 1) {

                }
            }
        }
        this.endIndex += fields.length;

        this.query.append(values);

        return this;
    }*/



    /*@Override
    public DatabaseRequestUpdate values(Object... values) {
        Main.log("&cStartIndex: &5" + this.startIndex + "&e, &eEndIndex: &5" + this.endIndex);


        if(this.query.toString().contains("VALUES")) {

            int startPlaceholderIndex;
            for(int i = this.startIndex; i < values.length; i++) {
                startPlaceholderIndex = this.query.indexOf("%value_" + i + "%");
                this.query.replace(
                        startPlaceholderIndex,
                        startPlaceholderIndex + ("%value_" + i + "%").length(),
                        (String) values[i]
                );
            }
            this.startIndex = values.length;

//            for(int i = 0; i < values.length; i++) {
//                startPlaceholderIndex = this.query.indexOf("%value_" + i + "%");
//                this.query.replace(
//                        startPlaceholderIndex,
//                        startPlaceholderIndex + ("%value_" + i + "%").length(),
//                        (String) values[i]
//                );
//            }
            //this.endIndex = values.length;
        } else {
            this.query.append("VALUES (");

            for(int i = 0; i < values.length; i++) {
                Object value = values[i];
                this.query.append(value)
                        .append(i < values.length - 1 ? ", " : ")");
            }
        }
        Main.sendMessage("CurrentIndex: " + this.endIndex);
        return this;
    }*/