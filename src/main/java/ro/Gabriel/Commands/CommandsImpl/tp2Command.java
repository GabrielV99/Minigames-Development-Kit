package ro.Gabriel.Commands.CommandsImpl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ro.Gabriel.Commands.CustomCommand;
import ro.Gabriel.Commands.CommandExecutorType;
import ro.Gabriel.Commands.CommandGroup;
import ro.Gabriel.Placeholder.test.placeholderTest;

//@CommandClass(syntax = "/bb tp test", defaultDescription = "&6this is 2 test command", executorType = CommandExecutorType.PLAYER)
public class tp2Command extends CustomCommand {

    public tp2Command(String id, String syntax, String description, CommandExecutorType executorType, String permission, CommandGroup group) {
        super(id, syntax, description, executorType, permission, group, false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        //sender.sendMessage(ChatColor. translateAlternateColorCodes('&', "&acommand 2: &6" + getArgs()[0] + "&a was successfully executed!"));

        //Main.log("hammingDistance: &c" + Utils.calculateStringArraySimilarity(new String[]{"a", "b"}, new String[]{"c", "b"}));
        placeholderTest.test( ((Player)sender), "!{name}! este un jucator" );
    }
}