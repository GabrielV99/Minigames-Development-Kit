package ro.Gabriel.Class.Validators;

import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Storage.Annotations.StorageType;
import ro.Gabriel.Class.ClassValidator;
import ro.Gabriel.Misc.ReflectionUtils;

import java.util.function.Predicate;

public class DataStorageValidator implements ClassValidator {
    @Override
    public Predicate<? super Class<?>> validate(Object... parameters) {
        return clazz -> parameters != null && parameters.length == 1 && parameters[0] instanceof String
                && ReflectionUtils.isAnnotated(clazz, StorageType.class) &&
                parameters[0].equals(clazz.getAnnotation(StorageType.class).extension()) &&
                ReflectionUtils.extendsClass(clazz, DataStorage.class);
    }
}