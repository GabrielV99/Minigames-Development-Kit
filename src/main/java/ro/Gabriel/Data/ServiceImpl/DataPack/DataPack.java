package ro.Gabriel.Data.ServiceImpl.DataPack;

import ro.Gabriel.Repository.BaseEntity;

import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Field;

public abstract class DataPack {

    public void fetchData(Object entity) {
        List<Field> fields = new ArrayList<>();

        Class<?> clazz = entity.getClass();

        while (clazz != BaseEntity.class) {


            clazz = clazz.getSuperclass();
        }
    }

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