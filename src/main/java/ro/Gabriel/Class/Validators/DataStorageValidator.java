package ro.Gabriel.Class.Validators;

import ro.Gabriel.Class.ClassUtils;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Storage.Annotations.StorageType;
import ro.Gabriel.Class.ClassValidator;

import java.util.function.Predicate;

public class DataStorageValidator implements ClassValidator {
    @Override
    public Predicate<? super Class<?>> validate(Object... parameters) {
        return clazz -> parameters != null && parameters.length == 1 && parameters[0] instanceof String
                && ClassUtils.isAnnotated(clazz, StorageType.class) &&
                parameters[0].equals(clazz.getAnnotation(StorageType.class).extension()) &&
                ClassUtils.extendsClass(clazz, DataStorage.class);
    }
}