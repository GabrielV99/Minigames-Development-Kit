package ro.Gabriel.Class.Validators;

import ro.Gabriel.Command.Annotations.CommandClass;
import ro.Gabriel.Class.ClassValidator;

import java.util.function.Predicate;

public class CommandGroupValidator implements ClassValidator {
    @Override
    public Predicate<? super Class<?>> validate(Object... parameters) {
        return clazz -> parameters != null && parameters.length == 1 && clazz.isAnnotationPresent(CommandClass.class)
                && parameters[0] != null && parameters[0] instanceof String && clazz.getAnnotation(CommandClass.class).group().equals(parameters[0]);
    }
}