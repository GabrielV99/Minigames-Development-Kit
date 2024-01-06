package ro.Gabriel.Placeholder;

public interface Placeholder<ObjectSource> {
    String makeReplace(String text, ObjectSource replacementSource);
}