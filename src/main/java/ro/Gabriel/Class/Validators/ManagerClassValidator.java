package ro.Gabriel.Class.Validators;

import ro.Gabriel.Class.ClassUtils;
import ro.Gabriel.Class.ClassValidator;
import ro.Gabriel.Main.Minigame;
import ro.Gabriel.Managers.Manager;
import ro.Gabriel.Managers.ManagerClass;
import ro.Gabriel.Misc.ReflectionUtils;

import java.util.function.Predicate;

public class ManagerClassValidator implements ClassValidator {
    @Override
    public Predicate<? super Class<?>> validate(Object... parameters) {
        return clazz -> {
            ManagerClass managerAnnotation = ClassUtils.getAnnotation(clazz, ManagerClass.class);
            Minigame minigame = parameters != null && parameters.length == 1 && parameters[0] != null && parameters[0] instanceof Minigame ? (Minigame) parameters[0] : null;

            try {
                return managerAnnotation != null && minigame != null && minigame.getServerType().match(minigame, managerAnnotation)
                        && ClassUtils.extendsClass(clazz, Manager.class) && ReflectionUtils.getConstructor(clazz, true, Class.forName(minigame.getDescription().getMain())) != null;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return false;
        };
    }
}