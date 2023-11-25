package ro.Gabriel.Language.Keys;

import ro.Gabriel.Placeholder.Placeholders.CommandAccessorPlaceholder;
import ro.Gabriel.Placeholder.Placeholders.DatabasePlaceholder;
import ro.Gabriel.Placeholder.Placeholders.HelpCommandsPlaceholder;
import ro.Gabriel.Placeholder.Placeholders.PlayerPlaceholder;
import ro.Gabriel.Placeholder.PlaceholderManager;
import ro.Gabriel.Placeholder.Placeholder;
import ro.Gabriel.Language.LanguageKey;

public enum MessagesKeys implements LanguageKey {

    JOIN_ARENA("player-join-arena", PlayerPlaceholder.class),
    QUIT_ARENA("player-quit-arena", JOIN_ARENA.getPlaceholder()),
    PLAYER_DISCONNECT("player-disconnect", JOIN_ARENA.getPlaceholder()),

    DONT_HAVE_PERMISSION("dont-have-permission"),

    DATABASE_ATTEMPTING_TO_CONNECT("database-attempting-to-connect", DatabasePlaceholder.class),
    DATABASE_SUCCESSFULLY_CONNECTED("database-successfully-connected", DATABASE_ATTEMPTING_TO_CONNECT.getPlaceholder()),
    DATABASE_COULD_NOT_CONNECT("database-could-not-connect", DATABASE_ATTEMPTING_TO_CONNECT.getPlaceholder()),
    DATABASE_SWITCH("database-switch", DATABASE_ATTEMPTING_TO_CONNECT.getPlaceholder()),
    DATABASE_FAILED_TO_CONNECT("database-failed-to-connect"),



    PARTY_IS_TOO_BIG("party-is-too-big"),
    PARTY_IS_TOO_SMALL("party-is-too-small"),
    NOT_PARTY_LEADER("not-party-leader"),


    COMMAND_ACCESSED_BY("command-accessed-by", CommandAccessorPlaceholder.class),

    COMMAND_ACCESSOR_PLAYER("command-accessor.player"),
    COMMAND_ACCESSOR_CONSOLE("command-accessor.console"),

    COMMAND_HELP_FORMAT("command-help-format"),
    COMMANDS_HELP_FORMAT("commands-help-format", HelpCommandsPlaceholder.class),

    ;

    MessagesKeys(String id) {
        this.id = id;
    }

    <T extends Placeholder<?>> MessagesKeys(String id, Class<T> placeholder) {
        this.id = id;
        this.placeholder = PlaceholderManager.getPlaceholder(placeholder);
    }

    MessagesKeys(String id, Placeholder<?> placeholder) {
        this.id = id;
        this.placeholder = placeholder;
    }

    private final String id;
    private Placeholder<?> placeholder;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDirectoryPath() {
        return "plugins/BuildBattle/Messages/";
    }

    @Override
    public Placeholder<?> getPlaceholder() {
        return this.placeholder;
    }
}