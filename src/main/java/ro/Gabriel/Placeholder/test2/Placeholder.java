package ro.Gabriel.Placeholder.test2;

public abstract class Placeholder <ObjectSource extends Object>{

    protected final String placeholder;

    public Placeholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public abstract String apply(String text, ObjectSource source);
}
