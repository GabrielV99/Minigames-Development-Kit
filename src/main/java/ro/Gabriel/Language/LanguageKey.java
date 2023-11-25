package ro.Gabriel.Language;

import ro.Gabriel.Placeholder.Placeholder;

public interface LanguageKey {
    String getId();
    String getDirectoryPath();

    Placeholder<?> getPlaceholder();
}