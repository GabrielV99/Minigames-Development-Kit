package ro.Gabriel.Commands.Annotations;

import ro.Gabriel.Commands.CommandExecutorType;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandClass {
    String group();
    String id();

    String syntax() default "defaultcommand";
    String defaultDescription() default "This is default command!";
    CommandExecutorType executorType() default CommandExecutorType.BOTH;
    String permission() default "defaultpermission";
}
