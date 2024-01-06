package ro.Gabriel.Language.Categories;

import ro.Gabriel.Language.LanguageCategory;
import ro.Gabriel.Placeholder.Placeholder;

public enum Gui implements LanguageCategory {

    NAME("name"),
    TITLE("title"),
    DESCRIPTION("description");


    String path;

    Gui(String path) {
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
