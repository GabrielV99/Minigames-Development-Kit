package ro.Gabriel.Data.ServiceImpl.DataPack;

import ro.Gabriel.Misc.SerializationUtils;
import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetDataPack extends DataPack {

    private final ResultSet resultSet;

    public ResultSetDataPack(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public String getString(String label) {
        try {
            String value = this.resultSet.getString(label);;
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