package ro.Gabriel.Commands.CommandsImpl;

import org.bukkit.command.CommandSender;
import ro.Gabriel.Commands.CustomCommand;
import ro.Gabriel.Commands.CommandExecutorType;
import ro.Gabriel.Commands.CommandGroup;

//@CommandClass(syntax = "/bb tp test another", defaultDescription = "&6this is Another test command", executorType = CommandExecutorType.PLAYER)
public class AnotherTestCommand extends CustomCommand {
    public AnotherTestCommand(String id, String syntax, String description, CommandExecutorType executorType, String permission, CommandGroup group) {
        super(id, syntax, description, executorType, permission, group, false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("&canother test command");
    }
}
