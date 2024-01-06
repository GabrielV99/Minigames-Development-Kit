package ro.Gabriel.Language.Categories;

import ro.Gabriel.Language.LanguageCategory;
import ro.Gabriel.Placeholder.Placeholder;

public enum Messages implements LanguageCategory {
    M1("message_1"),
    M2("message_2"),
    M3("message_3");

    String path;

    Messages(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Placeholder<?> getPlaceholder() {
        return null;
    }
}
