package ro.Gabriel.Language.Categories;

import ro.Gabriel.Class.Annotations.ObjectId;
import ro.Gabriel.Language.LanguagePath;
import ro.Gabriel.Placeholder.Placeholder;

@ObjectId(id = "commands")
public enum CommandMessages implements LanguagePath {
    DONT_HAVE_PERMISSION("dont-have-permission"),
    COMMAND_ACCESSED_BY("command-accessed-by"),
    COMMAND_ACCESSOR_PLAYER("command-accessor.player"),
    COMMAND_ACCESSOR_CONSOLE("command-accessor.console"),
    COMMANDS_HELP_FORMAT("commands-help-format", (Placeholder<Object[]>) (text, replacementSource) -> {
        return text
                .replace("%commands%", replacementSource[0].toString())
                .replace("%group%", replacementSource[1].toString());
    }),
    COMMAND_HELP_FORMAT("command-help-format");

    String path;
    private Placeholder<?> placeholder;

    CommandMessages(String path) {
        this.path = path;
    }

    <T extends Placeholder<?>> CommandMessages(String path, Class<T> placeholder) {
        this.path = path;
        //this.placeholder = PlaceholderManager.getPlaceholder(placeholder);
    }

    CommandMessages(String path, Placeholder<?> placeholder) {
        this.path = path;
        this.placeholder = placeholder;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public Placeholder<?> getPlaceholder() {
        return this.placeholder;
    }
}