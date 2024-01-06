package ro.Gabriel.Class;

import ro.Gabriel.Class.Annotations.ObjectId;

import java.lang.reflect.AnnotatedElement;
import java.lang.annotation.Annotation;

public class ClassUtils {

    public static boolean extendsClass(Class<?> clazz, Class<?> extension) {
        return clazz != extension && extension.isAssignableFrom(clazz);
    }

    public static boolean isInterface(Class<?> clazz) {
        return clazz.isInterface();
    }

    public static boolean isAnnotated(AnnotatedElement clazz, Class<? extends Annotation> annotation) {
        return clazz.isAnnotationPresent(annotation);
    }

    public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annotation) {
        return clazz.getAnnotation(annotation);
    }

    public static <T> T getEnumValue(Class<T> enumClass, String id) {
        if(enumClass == null) {
            return null;
        }

        T[] values = enumClass.getEnumConstants();
        for (T value : values) {
            if ( ((Enum<?>) value).name().equals(id) || (value instanceof EnumId && ((EnumId<?>) value).getId().equals(id))) {
                return value;
            }
        }

        return null;
    }

    public static String getObjectClassId(Class<?> clazz) {
        ObjectId id = ClassUtils.getAnnotation(clazz, ObjectId.class);
        return id != null ? id.id() : clazz.getSimpleName();
    }

    public interface EnumId<T> {
        T getId();
    }
}