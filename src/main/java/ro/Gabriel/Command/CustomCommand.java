package ro.Gabriel.Command;

import org.bukkit.command.CommandSender;
import ro.Gabriel.User.MinecraftUser;

public abstract class CustomCommand {
    private String id;

    private String syntax;
    private String[] args;

    private String description;

    private CommandExecutorType executorType;
    private String permission;

    private CommandGroup group;

    private boolean clickable;

    public CustomCommand() { }

    public CustomCommand(String id, String syntax, String description, CommandExecutorType executorType, String permission, CommandGroup group, boolean clickable) {
        this.id = id;

        this.syntax = syntax;

        this.args = syntax.replace(("/" + group.getId() + " "), "").split(" ");

        this.description = description;
        this.executorType = executorType;
        this.permission = permission;
        this.group = group;

        this.clickable = clickable;
    }

    public abstract void execute(MinecraftUser sender, String[] args);

    public boolean verifyMatching(String[] args) {
        if(this.args.length != args.length) {
            return false;
        }

        boolean result = true;

        for(int i = 0; i < args.length; i++) {
            result = ((this.args[i].startsWith("<") && this.args[i].endsWith(">")) || this.args[i].equals(args[i])) && result;//(this.args[i].startsWith("<") && this.args[i].endsWith(">")) || (this.args[i].equals(args[i]) && result)
        }

        return result;
    }

    public String getGroupID() {
        return this.group.getId();
    }

    public String getId() {
        return this.id;
    }

    public String getSyntax() {
        return syntax;
    }

    public String[] getArgs() {
        return this.args;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPermission() {
        return this.permission;
    }

    public CommandExecutorType getExecutorType() {
        return this.executorType;
    }

    public boolean isClickable() {
        return clickable;
    }
}