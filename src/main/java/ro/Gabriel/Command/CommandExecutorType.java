package ro.Gabriel.Command;

public enum CommandExecutorType {
    PLAYER, CONSOLE, BOTH;

    public static CommandExecutorType getExecutor(String name) {
        try {
            return CommandExecutorType.valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }
}