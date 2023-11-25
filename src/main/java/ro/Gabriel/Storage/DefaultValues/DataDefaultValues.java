package ro.Gabriel.Storage.DefaultValues;

import com.google.gson.internal.Primitives;

import java.util.*;

public class DataDefaultValues {

    private final static Map<Class<?>, Object> DEFAULT_PRIMITIVES_MAPPING;

    public static<T> T get(Class<T> type) {
        return Primitives.wrap(type).cast(DEFAULT_PRIMITIVES_MAPPING.get(type));
    }

    public static boolean equals(Object value, Class<?> clazz) {
        return get(clazz) == value;
    }

    static {
        DEFAULT_PRIMITIVES_MAPPING = new HashMap<Class<?>, Object>() {{
            //put(Object.class, new Object());

            put(String.class, "");
            put(char.class, ' ');

            put(int.class, 0);
            put(double.class, 0.0d);
            put(float.class, 0.0f);
            put(long.class, 0L);
            put(short.class, (short) 0);
            put(byte.class, (byte) 0);
            put(boolean.class, false);

            put(int[].class, new int[0]);

            put(Set.class, new HashSet<>());
        }};
    }
}
