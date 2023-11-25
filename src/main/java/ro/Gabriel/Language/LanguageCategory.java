package ro.Gabriel.Language;

public enum LanguageCategory {
    MESSAGES("messages"),
    GUI("gui"),
    SCOREBOARD("scoreboard"),
    LEADERBOARD("leaderboard"),
    COMMANDS("commands");

    private final String path;

    LanguageCategory(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}