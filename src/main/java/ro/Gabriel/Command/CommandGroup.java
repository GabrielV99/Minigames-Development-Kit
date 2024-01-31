package ro.Gabriel.Command;

import org.bukkit.command.CommandSender;

import ro.Gabriel.Language.Categories.CommandMessages;
import ro.Gabriel.Language.Categories.LanguageCategoryType;
import ro.Gabriel.Language.Language;
import ro.Gabriel.Main.Minigame;
import ro.Gabriel.Messages.MessageUtils;
import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;
import ro.Gabriel.User.ConsoleUser;
import ro.Gabriel.User.MinecraftUser;
import ro.Gabriel.User.SpigotUser;

import java.util.*;
import java.util.stream.Collectors;

public class CommandGroup extends BukkitCommandGroup {
    private final List<CustomCommand> commands;

    protected CommandGroup(Minigame plugin, String name, List<String> aliases, String permission) {
        super(plugin, name, "", "/", aliases);
        this.commands = new ArrayList<>();

        super.setPermission(permission);
    }

    @Override
    public boolean execute(MinecraftUser user, String commandLabel, String[] args) {
        System.out.println("s-a incercat cautarea comenzii in pluginul: " + getPlugin());
        CustomCommand c = this.commands.stream().filter(command -> command.verifyMatching(args)).findFirst().orElse(null);

        if(c != null) {
            if((user instanceof SpigotUser && (c.getExecutorType() == CommandExecutorType.PLAYER || c.getExecutorType() == CommandExecutorType.BOTH)) ||
                    (user instanceof ConsoleUser && (c.getExecutorType() == CommandExecutorType.CONSOLE || c.getExecutorType() == CommandExecutorType.BOTH))) {
                boolean permission = user.hasPermission(this.getPermission()) || user.hasPermission(c.getPermission());

                if(permission) {
                    c.execute(user, args);
                } else {
                    Language language =
                            this.getPlugin().getConfigManager().isCommandsMessagesFromMainPlugin()
                                    ? this.getPlugin().getBasePluginInstance().getLanguageManager().getLanguage(user.getLanguage().getLocale())
                                    : user.getLanguage();

                    user.sendMessage(CommandMessages.DONT_HAVE_PERMISSION, language);
                }
            } else if (user != null) {
                Language language =
                        this.getPlugin().getConfigManager().isCommandsMessagesFromMainPlugin()
                                ? this.getPlugin().getBasePluginInstance().getLanguageManager().getLanguage(user.getLanguage().getLocale())
                                : user.getLanguage();
                String accessor = language.getString(user == this.getPlugin().getDefaultUser() ? CommandMessages.COMMAND_ACCESSOR_PLAYER : CommandMessages.COMMAND_ACCESSOR_CONSOLE);

                user.sendMessage(CommandMessages.COMMAND_ACCESSED_BY, accessor, language);
            }
        } else {
            Language language =
                    this.getPlugin().getConfigManager().isCommandsMessagesFromMainPlugin()
                            ? this.getPlugin().getBasePluginInstance().getLanguageManager().getLanguage(user.getLanguage().getLocale())
                            : user.getLanguage();
            user.sendMessage(CommandMessages.COMMANDS_HELP_FORMAT, new String[]{ makeCommands(user, commandLabel, args, language), this.getName()}, language);
        }
        return false;
    }


    public String getId() {
        return super.getName();
    }

    private String makeCommands(MinecraftUser sender, String label, String[] args, Language language) {
        StringBuilder commands = new StringBuilder();

        CommandUtils.getCommandsByArgs(args, this).forEach(cmd -> {
            String description = language.getString(LanguageCategoryType.COMMANDS, "commands." + this.getName() + "." + cmd.getId() + ".description");

            commands.append(language.getString(CommandMessages.COMMAND_HELP_FORMAT)
                    .replace("%accessColor%", (sender.hasPermission(cmd.getPermission()) || sender.hasPermission(super.getPermission())) ? "&a" : "&c")
                    .replace("%command%", cmd.isClickable() ? "[" + cmd.getId() + "]" : (super.getUsage() + label + " " + cmd.getSyntax()))
                    .replace("%description%", DataDefaultValues.equals(description, String.class) ? cmd.getDescription() : description)
            ).append("\n");
        });

        return MessageUtils.colorString(
                this.getPlugin().getConfigManager().isCommandsMessagesFromMainPlugin() ? this.getPlugin().getBasePluginInstance() : this.getPlugin(),
                commands.toString()
        );
    }

    public List<CustomCommand> getCommands() {
        return this.commands;
    }

    private List<CustomCommand> getCommands(String id) {
        return this.commands.stream().filter(command -> command.getId().equals(id)).collect(Collectors.toList());
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if(args.length == 1) {
            return this.commands.stream().map(cmd -> cmd.getArgs()[0]).collect(Collectors.toList());
        }

        List<CustomCommand> commands = getCommands(args[0]);
        if(commands != null && !commands.isEmpty()) {
            return CommandUtils.getCommandsByArgs(args, this).stream().map(cmd -> {
                if(cmd.getArgs().length >= args.length) {
                    return cmd.getArgs()[args.length - 1];
                }
                return "";
            }).collect(Collectors.toList());
        }
        return null;
    }
}