package ro.Gabriel.Class.Validators;

import ro.Gabriel.Listener.CustomListener;
import ro.Gabriel.Class.ClassValidator;
import ro.Gabriel.Misc.Utils;

import java.util.function.Predicate;

public class CustomListenerValidator implements ClassValidator {
    @Override
    public Predicate<? super Class<?>> validate(Object... parameters) {
        return clazz -> Utils.isExtends(clazz, CustomListener.class);
    }
}