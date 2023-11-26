package ro.Gabriel.Class;

import java.util.function.Predicate;

public interface ClassValidator {
    Predicate<? super Class<?>> validate(Object ... parameters);
}
