package ro.Gabriel.Placeholder.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PlaceholderClass {
    boolean bungee();
    boolean lobby();
    boolean arena();
}
