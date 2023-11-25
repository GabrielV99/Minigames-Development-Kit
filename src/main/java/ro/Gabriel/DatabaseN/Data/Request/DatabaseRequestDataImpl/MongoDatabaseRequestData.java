package ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestDataImpl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

import org.bson.Document;

import ro.Gabriel.DatabaseN.Data.DataPack;
import ro.Gabriel.DatabaseN.Structures.DatabaseField;
import ro.Gabriel.DatabaseN.Structures.DatabaseStructure;
import ro.Gabriel.Misc.SerializationUtils;
import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;

import javax.annotation.Nonnull;
import java.util.Iterator;

public class MongoDatabaseRequestData extends DatabaseRequestData {

    public MongoDatabaseRequestData(DatabaseStructure structure) {
        super(structure);
    }

    @Override
    public DatabaseRequestData select() {
        return null;
    }

    @Override
    public DatabaseRequestData select(DatabaseField<?>... fields) {
        return null;
    }

    @Override
    public DatabaseRequestData selectDistinct() {
        return null;
    }

    @Override
    public DatabaseRequestData selectDistinct(DatabaseField<?>... fields) {
        return null;
    }

    @Override
    public DatabaseRequestData top(int top) {
        return null;
    }

    @Override
    public DatabaseRequestData topPercent(int percent) {
        return null;
    }

    @Override
    public DatabaseRequestData from(Class<? extends DatabaseStructure> structure) {
        return null;
    }

    @Override
    public DatabaseRequestData where(DatabaseField<?> field) {
        return null;
    }

    @Override
    public DatabaseRequestData and(DatabaseField<?> field) {
        return null;
    }

    @Override
    public DatabaseRequestData or(DatabaseField<?> field) {
        return null;
    }

    @Override
    public DatabaseRequestData isEquals(Object value) {
        return null;
    }

    @Override
    public DatabaseRequestData isEquals() {
        return null;
    }

    @Override
    public DatabaseRequestData greaterThan(Object value) {
        return null;
    }

    @Override
    public DatabaseRequestData greaterThan() {
        return null;
    }

    @Override
    public DatabaseRequestData lowerThan(Object value) {
        return null;
    }

    @Override
    public DatabaseRequestData lowerThan() {
        return null;
    }

    @Override
    public DatabaseRequestData greaterOrEquals(Object value) {
        return null;
    }

    @Override
    public DatabaseRequestData greaterOrEquals() {
        return null;
    }

    @Override
    public DatabaseRequestData lowerOrEquals(Object value) {
        return null;
    }

    @Override
    public DatabaseRequestData lowerOrEquals() {
        return null;
    }

    @Override
    public DatabaseRequestData between(Object value1, Object value2) {
        return null;
    }

    @Override
    public DatabaseRequestData between() {
        return null;
    }

    @Override
    public DatabaseRequestData in(DatabaseField<?>... fields) {
        return null;
    }

    @Override
    public DatabaseRequestData notIn(DatabaseField<?>... fields) {
        return null;
    }

    @Override
    public DatabaseRequestData isNull() {
        return null;
    }

    @Override
    public DatabaseRequestData isNotNull() {
        return null;
    }

    @Override
    public DatabaseRequestData groupBy(DatabaseField<?> field) {
        return null;
    }

    @Override
    public DatabaseRequestData orderBy(DatabaseField<?> field) {
        return null;
    }

    @Override
    public DataPack build(Object... values) {
        return null;
    }

    private class MongoDBDataPack extends DataPack {

        private final FindIterable<Document> iterable;

        private MongoCursor<Document> cursor;

        private Document currentDocument;

        public MongoDBDataPack(FindIterable<Document> iterable) {
            this.iterable = iterable;
            this.cursor = iterable.cursor();
        }

        @Nonnull
        @Override
        public Iterator<DataPack> iterator() {
            this.cursor = this.iterable.cursor();
            this.currentDocument = null;
            return this;
        }

        @Override
        public boolean hasNext() {
            boolean hasNext = cursor.hasNext();
            if(!hasNext) {
                this.cursor.close();
            }
            return hasNext;
        }

        @Override
        public DataPack next() {
            this.currentDocument = this.cursor.next();
            return this;
        }

        @Override
        public boolean getBoolean(String label) {
            try {
                return this.currentDocument.getBoolean(label);
            } catch (Exception e) {
                return DataDefaultValues.get(boolean.class);
            }
        }

        @Override
        public String getString(String label) {
            try {
                String value = this.currentDocument.getString(label);
                return value != null ? value : DataDefaultValues.get(String.class);
            } catch (Exception e) {
                return DataDefaultValues.get(String.class);
            }
        }

        @Override
        public char getChar(String label) {
            try {
                return this.currentDocument.getString(label).charAt(0);
            } catch (Exception e) {
                return DataDefaultValues.get(char.class);
            }
        }

        @Override
        public int getInt(String label) {
            try {
                return this.currentDocument.getInteger(label);
            } catch (Exception e) {
                return DataDefaultValues.get(int.class);
            }
        }

        @Override
        public double getDouble(String label) {
            try {
                return this.currentDocument.getDouble(label);
            } catch (Exception e) {
                return DataDefaultValues.get(double.class);
            }
        }

        @Override
        public float getFloat(String label) {
            try {
                return (float) (double) this.currentDocument.getDouble(label);
            } catch (Exception e) {
                return DataDefaultValues.get(float.class);
            }
        }

        @Override
        public long getLong(String label) {
            try {
                return this.currentDocument.getLong(label);
            } catch (Exception e) {
                return DataDefaultValues.get(long.class);
            }
        }

        @Override
        public short getShort(String label) {
            try {
                return (short) (int) this.currentDocument.getInteger(label);
            } catch (Exception e) {
                return DataDefaultValues.get(short.class);
            }
        }

        @Override
        public byte getByte(String label) {
            try {
                return (byte) (int) this.currentDocument.getInteger(label);
            } catch (Exception e) {
                return DataDefaultValues.get(byte.class);
            }
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
