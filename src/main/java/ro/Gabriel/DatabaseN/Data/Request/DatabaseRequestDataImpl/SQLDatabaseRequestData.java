package ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestDataImpl;

import ro.Gabriel.DatabaseN.Data.Request.DatabaseRequest;
import ro.Gabriel.DatabaseN.Structures.DatabaseField;
import ro.Gabriel.DatabaseN.Structures.DatabaseStructure;
import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;
import ro.Gabriel.DatabaseN.Data.DataPack;
import ro.Gabriel.DatabaseN.DatabaseUtils;
import ro.Gabriel.Misc.SerializationUtils;
import ro.Gabriel.BuildBattle.Main;

import javax.annotation.Nonnull;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Iterator;

public class SQLDatabaseRequestData extends DatabaseRequestData {

    private final Connection connection;

    private final StringBuilder query = new StringBuilder();

    int startIndex = 0, endIndex = 0;

    public SQLDatabaseRequestData(Connection connection, DatabaseStructure structure) {
        super(structure);
        this.connection = connection;

    }

    @Override
    public DatabaseRequestData select() {
        this.query.append("SELECT * ");
        return this;
    }

    @Override
    public DatabaseRequestData select(DatabaseField<?>... fields) {
        this.query.append("SELECT ");

        for(int i = 0; i < fields.length; i++) {
            DatabaseField<?> field = fields[i];

            if(field.getClass().isEnum()) {
                this.query.append((this.startIndex = Math.abs(i - Math.abs(this.endIndex))) > 0 ? ", " : "")
                        .append(field.getId());
            } else {
                this.endIndex--;
            }
        }

        this.query.append(" ");

        return this;
    }

    @Override
    public DatabaseRequestData selectDistinct() {
        this.query.append("SELECT DISTINCT *");
        return this;
    }

    @Override
    public DatabaseRequestData selectDistinct(DatabaseField<?>... fields) {
        this.query.append("SELECT DISTINCT ");

        for(int i = 0; i < fields.length; i++) {
            DatabaseField<?> field = fields[i];

            if(field.getClass().isEnum()) {
                this.query.append((this.startIndex = Math.abs(i - Math.abs(this.endIndex))) > 0 ? ", " : "")
                        .append(field.getId());
            } else {
                this.endIndex--;
            }
        }

        this.query.append(" ");

        return this;
    }

    @Override
    public DatabaseRequestData top(int top) {
        String query = this.query.toString();
        if(query.startsWith("SELECT")) {
            this.query.replace(6, 6, " TOP " + top);
        } else if (query.startsWith("SELECT DISTINCT")){
            this.query.replace(15, 15, " TOP " + top);
        }
        return this;
    }

    @Override
    public DatabaseRequestData topPercent(int percent) {
        String query = this.query.toString();
        if(query.startsWith("SELECT")) {
            this.query.replace(6, 6, " TOP PERCENT " + percent);
        } else if (query.startsWith("SELECT DISTINCT")){
            this.query.replace(15, 15, " TOP PERCENT " + percent);
        }
        return this;
    }

    @Override
    public DatabaseRequestData from(Class<? extends DatabaseStructure> structure) {
        this.query.append("FROM ").append(DatabaseUtils.getStructureId(structure));
        return this;
    }

    @Override
    public DatabaseRequestData where(DatabaseField<?> field) {
        this.query.append(" WHERE ").append(field.getId());
        return this;
    }

    @Override
    public DatabaseRequestData and(DatabaseField<?> field) {
        this.query.append(" AND ").append(field.getId());
        return this;
    }

    @Override
    public DatabaseRequestData or(DatabaseField<?> field) {
        this.query.append(" OR ").append(field.getId());
        return this;
    }

    @Override
    public DatabaseRequestData isEquals(Object value) {
        this.query.append("=").append(value);
        return this;
    }

    @Override
    public DatabaseRequestData isEquals() {
        return this.isEquals("'%value_" + this.endIndex++ + "%'");
    }

    @Override
    public DatabaseRequestData greaterThan(Object value) {
        this.query.append(">").append(value.toString());
        return this;
    }

    @Override
    public DatabaseRequestData greaterThan() {
        return this.greaterThan("%value_" + this.endIndex++ + "%");
    }

    @Override
    public DatabaseRequestData lowerThan(Object value) {
        this.query.append("<").append(value.toString());
        return this;
    }

    @Override
    public DatabaseRequestData lowerThan() {
        return this.lowerThan("%value_" + this.endIndex++ + "%");
    }

    @Override
    public DatabaseRequestData greaterOrEquals(Object value) {
        this.query.append(">=").append(value.toString());
        return this;
    }

    @Override
    public DatabaseRequestData greaterOrEquals() {
        return this.greaterOrEquals("%value_" + this.endIndex++ + "%");
    }

    @Override
    public DatabaseRequestData lowerOrEquals(Object value) {
        this.query.append("<=").append(value.toString());
        return this;
    }

    @Override
    public DatabaseRequestData lowerOrEquals() {
        return this.lowerOrEquals("%value_" + this.endIndex++ + "%");
    }

    @Override
    public DatabaseRequestData between(Object value1, Object value2) {
        this.query.append(" BETWEEN ").append(value1.toString()).append(" AND ").append(value2.toString());
        return this;
    }

    @Override
    public DatabaseRequestData between() {
        return this.between("%value_" + this.endIndex++ + "%", "%value_" + this.endIndex++ + "%");
    }

    @Override
    public DatabaseRequestData in(DatabaseField<?>... fields) {
        this.query.append(" IN (");

        int endIndex = 0;
        for(int i = 0; i < fields.length; i++) {
            DatabaseField<?> field = fields[i];
            if(field.getClass().isEnum()) {
                this.query.append(Math.abs(i - Math.abs(endIndex)) > 0 ? ", " : "")
                        .append(field.getId())
                        .append(i == fields.length - 1 ? ")" : "");
            } else {
                endIndex--;
                if(i == fields.length - 1) {
                    this.query.append(")");
                }
            }
        }
        return this;
    }

    @Override
    public DatabaseRequestData notIn(DatabaseField<?>... fields) {
        this.query.append(" NOT IN (");

        int endIndex = 0;
        for(int i = 0; i < fields.length; i++) {
            DatabaseField<?> field = fields[i];
            if(field.getClass().isEnum()) {
                this.query.append(Math.abs(i - Math.abs(endIndex)) > 0 ? ", " : "")
                        .append(field.getId())
                        .append(i == fields.length - 1 ? ")" : "");
            } else {
                endIndex--;
                if(i == fields.length - 1) {
                    this.query.append(")");
                }
            }
        }
        return this;
    }

    @Override
    public DatabaseRequestData isNull() {
        this.query.append(" IS NULL");
        return this;
    }

    @Override
    public DatabaseRequestData isNotNull() {
        this.query.append(" IS NOT NULL");
        return this;
    }

    @Override
    public DatabaseRequestData groupBy(DatabaseField<?> field) {
        this.query.append(" GROUP BY ").append(field.getId());
        return this;
    }

    @Override
    public DatabaseRequestData orderBy(DatabaseField<?> field) {
        this.query.append(" ORDER BY ").append(field.getId());
        return this;
    }

    @Override
    public DataPack build(Object... values) {
        int replaceValues = Math.max((this.endIndex - this.startIndex), 0);

        String query = this.query.toString();

        if(replaceValues >= values.length) {
            int index = 0;
            for(int i = this.startIndex; i < this.endIndex; i++) {
                query = query.replace(("%value_" + i + "%"), values[index++].toString());
            }
        }
        Main.log("&6Query: &c" + query);

        try (ResultSet resultSet = this.connection.prepareStatement(query).executeQuery()) {
            boolean exist = resultSet.next();
            if(exist) {
                resultSet.previous();
                return new ResultSetDataPack(resultSet);
            } /*else {
                if(query.startsWith("SELECT *") || query.startsWith("SELECT DISTINCT *")) {

                } else {
                    return new ResultSetDataPack(resultSet);
                }

            }//Realtek 8821CE Wireless LAN 802.11ac PCI-E NIC*/

            return structure.getDefaultDataPack();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class ResultSetDataPack extends DataPack {

        private final ResultSet resultSet;

        public ResultSetDataPack(ResultSet resultSet) {
            this.resultSet = resultSet;
        }

        @Nonnull
        @Override
        public Iterator<DataPack> iterator() {
            try {
                boolean previous;
                do {
                    previous = this.resultSet.previous();
                } while (previous);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return this;
        }

        @Override
        public boolean hasNext() {
            try {
                boolean hasNext = this.resultSet.next();
                if(!hasNext) {
                    this.resultSet.close();
                }
                return hasNext;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public DataPack next() {
            return this;
        }

        @Override
        public boolean getBoolean(String label) {
            try {
                return this.resultSet.getBoolean(label);
            } catch (SQLException ignored) { return DataDefaultValues.get(boolean.class); }
        }

        @Override
        public String getString(String label) {
            try {
                String value = this.resultSet.getString(label);
                return value != null ? value : DataDefaultValues.get(String.class);
            } catch (SQLException ignored) { return DataDefaultValues.get(String.class); }
        }

        @Override
        public char getChar(String label) {
            try {
                return this.resultSet.getString(label).charAt(0);
            } catch (SQLException ignored) { return DataDefaultValues.get(char.class); }
        }

        @Override
        public int getInt(String label) {
            try {
                return this.resultSet.getInt(label);
            } catch (SQLException ignored) { return 0; }
        }

        @Override
        public double getDouble(String label) {
            try {
                return this.resultSet.getDouble(label);
            } catch (SQLException ignored) { return 0.0; }
        }

        @Override
        public float getFloat(String label) {
            try {
                return this.resultSet.getFloat(label);
            } catch (SQLException ignored) { return 0.0f; }
        }

        @Override
        public long getLong(String label) {
            try {
                return this.resultSet.getLong(label);
            } catch (SQLException ignored) { return 0L; }
        }

        @Override
        public short getShort(String label) {
            try {
                return this.resultSet.getShort(label);
            } catch (SQLException ignored) { return 0; }
        }

        @Override
        public byte getByte(String label) {
            try {
                return this.resultSet.getByte(label);
            } catch (SQLException ignored) { return 0; }
        }

        @Override
        public int[] getIntArray(String label) {
            try {
                return SerializationUtils.deserialize(this.getString(label), int[].class);
            } catch (Exception e) {
                return DataDefaultValues.get(int[].class);
            }
        }
    }
}