package ro.Gabriel.Command.Commands;

import ro.Gabriel.Command.Annotations.CommandClass;
import ro.Gabriel.Command.CustomCommand;
import ro.Gabriel.User.MinecraftUser;

@CommandClass(group = "BuildBattle", id = "cmd")
public class commandTest extends CustomCommand {
    @Override
    public void execute(MinecraftUser sender, String[] args) {
        sender.sendMessage("s-a executat comanda: " + args[0]);
        sender.sendMessage("locale-ul tau este: " + sender.getLanguage().getLocale());
    }
}