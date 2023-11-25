package ro.Gabriel.Class.Validators;

import ro.Gabriel.DatabaseN.Structures.DatabaseStructure;
import ro.Gabriel.Class.ClassValidator;
import ro.Gabriel.Misc.Utils;

import java.util.function.Predicate;

public class DatabaseStructureValidator implements ClassValidator {
    @Override
    public Predicate<? super Class<?>> validate(Object... parameters) {
        return clazz -> Utils.isExtends(clazz, DatabaseStructure.class);
    }
}