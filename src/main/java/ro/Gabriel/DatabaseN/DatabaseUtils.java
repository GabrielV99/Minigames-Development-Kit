package ro.Gabriel.DatabaseN;

import ro.Gabriel.DatabaseN.Structures.DatabaseField;
import ro.Gabriel.DatabaseN.Structures.DatabaseStructure;
import ro.Gabriel.DatabaseN.Structures.DatabaseStructureId;
import ro.Gabriel.Misc.ReflectionUtils;
import ro.Gabriel.Misc.Utils;
import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;

import java.lang.reflect.Field;

public class DatabaseUtils {

    public static String getStructureId(Class<? extends DatabaseStructure> structureClass) {
        if(!Utils.isExtends(structureClass, DatabaseStructure.class)) {
            return DataDefaultValues.get(String.class);
        }

        DatabaseStructureId structureName = structureClass.getAnnotation(DatabaseStructureId.class);

        if(structureName != null) {
            return structureName.id();
        }

        return structureClass.getSimpleName();
    }

//    public static String getStructureComponent(DatabaseStructure structureComponent) {
//        if(!(structureComponent instanceof Enum)) {
//            return DataDefaultValues.get(String.class);
//        }
//
//        return ((Enum<?>) structureComponent).name();
//    }

    public static DatabaseStructure[] getDatabaseFields(Class<? extends DatabaseStructure> structure) {
        return structure.getEnumConstants();
    }

    public static DatabaseField<?> getField(Field field) {
        try {
            return (DatabaseField<?>)ReflectionUtils.getValue(null, true, field.getDeclaringClass(), field.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String typeToSQLData(Class<?> type) {
        return (type == String.class) ? "VARCHAR(255)"
                : (type == Character.class || type == char.class) ? "CHAR(255)"
                : (type == Integer.class || type == int.class) ? "INT"
                : (type == Long.class || type == long.class) ? "BIGINT"
                : (type == Double.class || type == double.class) ? "DOUBLE"
                : (type == Float.class || type == float.class) ? "FLOAT"
                : (type == Byte.class || type == byte.class) ? "TINYINT"
                : (type == Short.class || type == short.class) ? "SMALLINT"
                : (type == Boolean.class || type == boolean.class) ? "BOOLEAN"
                : "VARCHAR(255)";
    }

    public static int getPort(Object port) {
        if(port instanceof Integer) {
            return (int) port;
        } else if(port instanceof String) {
            try {
                return Integer.parseInt((String) port);
            } catch (NumberFormatException nfe) {
                return DataDefaultValues.get(int.class);
            }
        }
        return DataDefaultValues.get(int.class);
    }

    public static boolean getSsl(Object ssl) {
        if(ssl instanceof Boolean) {
            return (boolean) ssl;
        } else if(ssl instanceof String) {
            try {
                return Boolean.parseBoolean((String) ssl);
            } catch (NumberFormatException nfe) {
                return DataDefaultValues.get(boolean.class);
            }
        }
        return DataDefaultValues.get(boolean.class);
    }
}
