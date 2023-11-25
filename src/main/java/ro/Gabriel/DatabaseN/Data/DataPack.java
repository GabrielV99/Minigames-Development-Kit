package ro.Gabriel.DatabaseN.Data;

import java.util.Iterator;

public abstract class DataPack implements Iterable<DataPack>, Iterator<DataPack> {

    public abstract boolean getBoolean(String label);

    public abstract String getString(String label);

    public abstract char getChar(String label);

    public abstract int getInt(String label);

    public abstract double getDouble(String label);

    public abstract float getFloat(String label);

    public abstract long getLong(String label);

    public abstract short getShort(String label);

    public abstract byte getByte(String label);

    public abstract int[] getIntArray(String label);
}