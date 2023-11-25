package ro.Gabriel.Placeholder.Placeholders;

import ro.Gabriel.Placeholder.Placeholder;

public class FailedPlaceholder implements Placeholder {
    @Override
    public String makeReplace(String text, Object replacementSource) {
        return text;
    }
}
