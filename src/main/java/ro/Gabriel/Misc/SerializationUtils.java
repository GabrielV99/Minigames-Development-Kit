package ro.Gabriel.Misc;

import com.google.gson.Gson;

public class SerializationUtils {

    private final static Gson GSON;

    public static <T> T deserialize(String json, Class<T> returnType) {
        return GSON.fromJson(json, returnType);
    }

    static {
        GSON = new Gson();
    }
}
