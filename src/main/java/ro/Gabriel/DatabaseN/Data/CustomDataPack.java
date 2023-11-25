package ro.Gabriel.DatabaseN.Data;

import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CustomDataPack extends DataPack {

    private Map<String, Object> values;

    public CustomDataPack() {
        this.values = new HashMap<>();
    }

    public CustomDataPack(Map<String, Object> values) {
        this.values = values;
    }

    public void addValue(String key, Object value) {
        if(this.values == null) {
            this.values = new HashMap<>();
        }
        this.values.put(key, value);
    }

    @Override
    public boolean getBoolean(String label) {
        if(values != null) {
            Object value = this.values.get(label);
            if(value instanceof Boolean) {
                return (boolean) value;
            }
        }

        return DataDefaultValues.get(boolean.class);
    }

    @Override
    public String getString(String label) {
        if(values != null) {
            Object value = this.values.get(label);
            if(value instanceof String) {
                return (String) value;
            }
        }

        return DataDefaultValues.get(String.class);
    }

    @Override
    public char getChar(String label) {
        if(values != null) {
            Object value = this.values.get(label);
            if(value instanceof Character) {
                return (char) value;
            }
        }

        return DataDefaultValues.get(char.class);
    }

    @Override
    public int getInt(String label) {
        if(values != null) {
            Object value = this.values.get(label);
            if(value instanceof Integer) {
                return (int) value;
            }
        }

        return DataDefaultValues.get(int.class);
    }

    @Override
    public double getDouble(String label) {
        if(values != null) {
            Object value = this.values.get(label);
            if(value instanceof Double) {
                return (double) value;
            }
        }

        return DataDefaultValues.get(double.class);
    }

    @Override
    public float getFloat(String label) {
        if(values != null) {
            Object value = this.values.get(label);
            if(value instanceof Float) {
                return (float) value;
            }
        }

        return DataDefaultValues.get(float.class);
    }

    @Override
    public long getLong(String label) {
        if(values != null) {
            Object value = this.values.get(label);
            if(value instanceof Long) {
                return (long) value;
            }
        }

        return DataDefaultValues.get(long.class);
    }

    @Override
    public short getShort(String label) {
        if(values != null) {
            Object value = this.values.get(label);
            if(value instanceof Short) {
                return (short) value;
            }
        }

        return DataDefaultValues.get(short.class);
    }

    @Override
    public byte getByte(String label) {
        if(values != null) {
            Object value = this.values.get(label);
            if(value instanceof Byte) {
                return (byte) value;
            }
        }

        return DataDefaultValues.get(byte.class);
    }

    @Override
    public int[] getIntArray(String label) {
        return new int[0];
    }

    @Override
    public Iterator<DataPack> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return this.values.values().iterator().hasNext();
    }

    @Override
    public DataPack next() {
        return null;
    }
}
