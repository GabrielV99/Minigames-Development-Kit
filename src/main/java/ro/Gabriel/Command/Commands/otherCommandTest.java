package ro.Gabriel.Command.Commands;

import ro.Gabriel.Command.Annotations.CommandClass;
import ro.Gabriel.Command.CustomCommand;
import ro.Gabriel.User.MinecraftUser;

@CommandClass(group = "BedWars", id = "cmd2")
public class otherCommandTest extends CustomCommand {
    @Override
    public void execute(MinecraftUser sender, String[] args) {
        sender.sendMessage("s-a executat comanda VEDWARS ");
    }
}