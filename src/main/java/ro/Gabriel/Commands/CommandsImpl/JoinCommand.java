package ro.Gabriel.Commands.CommandsImpl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ro.Gabriel.BuildBattle.Main;
import ro.Gabriel.Commands.CustomCommand;
import ro.Gabriel.Commands.Annotations.CommandClass;
import ro.Gabriel.Placeholder.test2.PlaceholderManager;

@CommandClass(group = "BuildBattle", id = "join")
public class JoinCommand extends CustomCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = ((Player)sender);

        String message = "&a%player_name% &6has join the arena!";

        //sender.sendMessage(PlaceholderManager.makeReplacement(message, "playerPlaceholder", player));
        PlaceholderManager.makeReplacement(message, "playerPlaceholder", player);



        sender.sendMessage(message);


        Main.log("join " + args[1]);
    }
}
