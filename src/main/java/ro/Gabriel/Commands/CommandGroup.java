package ro.Gabriel.Commands;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import ro.Gabriel.Language.Keys.MessagesKeys;
import ro.Gabriel.Language.LanguageCategory;
import ro.Gabriel.Messages.MessageUtils;
import ro.Gabriel.Player.UserManager;
import ro.Gabriel.Player.SpigotUser;
import ro.Gabriel.Storage.DefaultValues.DataDefaultValues;

import java.util.*;
import java.util.stream.Collectors;

public class CommandGroup extends Command {
    private final List<CustomCommand> commands;

    public CommandGroup(String name, List<String> aliases, String permission) {
        super(name, "", "/", aliases);
        this.commands = new ArrayList<>();

        super.setPermission(permission);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        CustomCommand c = this.commands.stream().filter(command -> command.verifyMatching(args)).findFirst().orElse(null);

        SpigotUser user = UserManager.getUser(sender);
        user = new SpigotUser(((Player)sender).getUniqueId());// to delete
        if(c != null) {
            if((sender instanceof Player && (c.getExecutorType() == CommandExecutorType.PLAYER || c.getExecutorType() == CommandExecutorType.BOTH)) ||
                    (sender instanceof ConsoleCommandSender && (c.getExecutorType() == CommandExecutorType.CONSOLE || c.getExecutorType() == CommandExecutorType.BOTH))) {

                boolean permission = user.hasPermission(this.getPermission()) || user.hasPermission(c.getPermission());

                if(permission) {
                    c.execute(sender, args);
                } else {
                    user.sendMessage(MessagesKeys.DONT_HAVE_PERMISSION);
                }
            } else {
                String accessor = user.getLanguage().getString(UserManager.isDefaultUser(user) ? MessagesKeys.COMMAND_ACCESSOR_PLAYER : MessagesKeys.COMMAND_ACCESSOR_CONSOLE, LanguageCategory.MESSAGES);
                user.sendMessage(MessagesKeys.COMMAND_ACCESSED_BY, accessor);
            }
        } else {
            user.sendMessage(MessagesKeys.COMMANDS_HELP_FORMAT, new String[]{ makeCommands(user, commandLabel, args), this.getName()});
        }
        return false;
    }

    public String getId() {
        return super.getName();
    }

    private String makeCommands(SpigotUser sender, String label, String[] args) {
        StringBuilder commands = new StringBuilder();
        CommandUtils.getCommandsByArgs(args, this).forEach(cmd -> {

            String description = sender.getLanguage().getString("commands." + this.getName() + "." + cmd.getId() + ".description", LanguageCategory.MESSAGES);

            commands.append(sender.getLanguage().getString(MessagesKeys.COMMAND_HELP_FORMAT, LanguageCategory.MESSAGES)
                    .replace("%accessColor%", (sender.hasPermission(cmd.getPermission()) || sender.hasPermission(super.getPermission())) ? "&a" : "&c")
                    .replace("%command%", cmd.isClickable() ? "[" + cmd.getId() + "]" : (super.getUsage() + label + " " + cmd.getSyntax()))
                    .replace("%description%", DataDefaultValues.equals(description, String.class) ? cmd.getDescription() : description)
            ).append("\n");
        });
        return MessageUtils.colorString(commands.toString());
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