package ro.Gabriel.Commands.CommandsImpl;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ro.Gabriel.Commands.CustomCommand;
import ro.Gabriel.Commands.CommandExecutorType;
import ro.Gabriel.Commands.CommandGroup;
import ro.Gabriel.Language.Keys.MessagesKeys;
import ro.Gabriel.Player.UserManager;

//@CommandClass(syntax = "/bb tp hoverMessage <message> <show>", defaultDescription = "hvrm", executorType = CommandExecutorType.PLAYER)
public class HoverMessageCommand extends CustomCommand {

    public HoverMessageCommand(String id, String syntax, String description, CommandExecutorType executorType, String permission, CommandGroup group) {
        super(id, syntax, description, executorType, permission, group, false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = ((Player)sender);

        //SpigotUser.test(player, "[h12]ceva[h22]", null, null);
        //SpigotUser.test(player, args[2], null, null);

        //Main.log("length: " + args[2].split("\\[").length);

        //SpigotUser f = new BasicPlayer(player, 0, 0, 0, 0, 0, 0, null, null, null, null, null, null, null){};
        //f.sendMessage(MessagesKeys.JOIN_ARENA);

        UserManager.getDefaultUser().sendMessage(MessagesKeys.JOIN_ARENA);
        //sendMessage(player, args[2], args[3], "hover2", "hover3", "hover4");

        player.sendMessage("aaaaaaaaaaa\nbbbbbbbbbbbbbbbbbb");
        UserManager.getDefaultUser().sendMessage("aaaaaaaaaaa\nbbbbbbbbbbbbbbbbbb");

        TextComponent t = new TextComponent("aaaa\nbbbbbbbbbbbbbbb\nccccccccc");
        TextComponent t1 = new TextComponent("CEVA");
        t1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("aici se afla ceva\n2222").create()));
        t.addExtra(t1);

        player.spigot().sendMessage(t);
    }



    public void sendMessage(Player player, String message, String hoverText) {
        TextComponent textComponent = new TextComponent();
        int startIndex = message.indexOf('[');
        int endIndex = message.indexOf(']');
        if (startIndex >= 0 && endIndex >= 0 && startIndex < endIndex) {
            textComponent.setText(ChatColor.translateAlternateColorCodes('&', message.substring(0, startIndex)));

            TextComponent hoverComponent = new TextComponent(ChatColor.translateAlternateColorCodes('&', message.substring(startIndex + 1, endIndex)));
            hoverComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', hoverText)).create()));
            textComponent.addExtra(hoverComponent);

            textComponent.addExtra(ChatColor.translateAlternateColorCodes('&', message.substring(endIndex + 1)));
        } else {
            textComponent.setText(message);
        }
        player.spigot().sendMessage(textComponent);
    }

    public void sendMessage(Player player, String message, String... hoverText) {
        TextComponent textComponent = new TextComponent();

        String[] parts = message.split("\\[");//[h1] tralalal blalala [h2] sdsds [h3]
        textComponent.setText(ChatColor.translateAlternateColorCodes('&', parts[0]));
        for (int i = 1; i < parts.length; i++) {
            String[] subParts = parts[i].split("]", 2);
            TextComponent hoverComponent = new TextComponent(ChatColor.translateAlternateColorCodes('&', subParts[0]));
            if (hoverText.length >= i) {
                hoverComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', hoverText[i - 1])).create()));
            }
            textComponent.addExtra(hoverComponent);
            textComponent.addExtra(ChatColor.translateAlternateColorCodes('&', subParts[1]));
        }
        player.spigot().sendMessage(textComponent);
    }
}