package ro.Gabriel.Class.Validators;

import ro.Gabriel.Main.MinigamesDevelopmentKit;
import ro.Gabriel.Managers.Annotations.ManagerClass;
import ro.Gabriel.Class.ClassValidator;
import ro.Gabriel.Misc.Utils;

import java.util.function.Predicate;

public class ManagerClassValidator implements ClassValidator {
    @Override
    public Predicate<? super Class<?>> validate(Object... parameters) {
        return clazz -> clazz.isAnnotationPresent(ManagerClass.class)
                && MinigamesDevelopmentKit.getServerType().match(clazz.getAnnotation(ManagerClass.class))
                && Utils.isMethodPresent(clazz, "load");
    }
}