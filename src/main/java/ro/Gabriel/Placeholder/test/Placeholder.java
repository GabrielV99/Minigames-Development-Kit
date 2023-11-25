package ro.Gabriel.Placeholder.test;

import java.util.function.Function;

public class Placeholder<T> {
    private final String placeholder;
    private final Function<T, String> valueFunction;

    public Placeholder(String placeholder, Function<T, String> consumer) {
        this.placeholder = placeholder;
        this.valueFunction = consumer;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public String apply(T source) {
        return valueFunction.apply(source);
    }
}