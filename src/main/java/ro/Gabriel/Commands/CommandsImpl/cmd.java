package ro.Gabriel.Commands.CommandsImpl;

import org.bukkit.command.CommandSender;
import ro.Gabriel.BuildBattle.Main;
import ro.Gabriel.Commands.Annotations.CommandClass;
import ro.Gabriel.Commands.CustomCommand;

@CommandClass(group = "BuildBattle", id = "cmd")
public class cmd extends CustomCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        Main.log("s-a executat CMD");
    }
}
