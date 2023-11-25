package ro.Gabriel.Class.Validators;

import ro.Gabriel.Storage.Annotations.StorageType;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Class.ClassValidator;
import ro.Gabriel.Misc.Utils;

import java.util.function.Predicate;

public class DataStorageValidator implements ClassValidator {
    @Override
    public Predicate<? super Class<?>> validate(Object... parameters) {
        return clazz -> parameters != null && parameters.length == 1 && parameters[0] instanceof String
                && Utils.isAnnotated(clazz, StorageType.class) &&
                ((String)parameters[0]).equals(clazz.getAnnotation(StorageType.class).extension()) &&
                Utils.isExtends(clazz, DataStorage.class);
    }
}