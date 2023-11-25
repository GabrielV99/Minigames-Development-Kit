package ro.Gabriel.Data.ServiceImpl.DataPack;

import ro.Gabriel.Misc.SerializationUtils;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;

public class SectionDataPack extends DataPack {

    private final DataStorage configurationSection;

    public SectionDataPack(DataStorage configurationSection) {
        this.configurationSection = configurationSection;
    }

    @Override
    public String getString(String label) {
        String value = this.configurationSection.getString(label);
        return value != null ? value : DataDefaultValues.get(String.class);
    }

    @Override
    public char getChar(String label) {
        return this.configurationSection.getChar(label);
    }

    @Override
    public int getInt(String label) {
        return this.configurationSection.getInteger(label);
    }

    @Override
    public double getDouble(String label) {
        return this.configurationSection.getDouble(label);
    }

    @Override
    public float getFloat(String label) {
        return this.configurationSection.getFloat(label);
    }

    @Override
    public long getLong(String label) {
        return this.configurationSection.getLong(label);
    }

    @Override
    public short getShort(String label) {
        return (short) this.getInt(label);
    }

    @Override
    public byte getByte(String label) {
        return (byte) this.getInt(label);
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