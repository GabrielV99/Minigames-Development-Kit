package ro.Gabriel.Class.Annotations;

import ro.Gabriel.Managers.Manager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ObjectId {
    String id();
}
