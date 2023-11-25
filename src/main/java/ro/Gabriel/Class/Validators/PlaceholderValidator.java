package ro.Gabriel.Class.Validators;

import ro.Gabriel.Placeholder.Annotations.PlaceholderClass;
import ro.Gabriel.Placeholder.Placeholder;
import ro.Gabriel.Class.ClassValidator;
import ro.Gabriel.Misc.Utils;

import java.util.function.Predicate;

public class PlaceholderValidator implements ClassValidator {
    @Override
    public Predicate<? super Class<?>> validate(Object... parameters) {
        return clazz -> Utils.isAnnotated(clazz, PlaceholderClass.class)
                &&  Main.getServerType().match(Utils.getAnnotation(clazz, PlaceholderClass.class))
                && Utils.isExtends(clazz, Placeholder.class);
    }
}