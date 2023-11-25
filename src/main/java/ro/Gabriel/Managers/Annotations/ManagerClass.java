package ro.Gabriel.Managers.Annotations;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ManagerClass {
    boolean bungee();
    boolean lobby();
    boolean arena();

    Class<?>[] managersBefore() default {};
}