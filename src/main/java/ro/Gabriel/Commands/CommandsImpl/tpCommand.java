package ro.Gabriel.Commands.CommandsImpl;

import org.bukkit.command.CommandSender;
import ro.Gabriel.Commands.CustomCommand;
import ro.Gabriel.Commands.CommandExecutorType;
import ro.Gabriel.Commands.CommandGroup;

//@CommandClass(syntax = "/bb tp getFileName <path>", defaultDescription = "&6this is test command", executorType = CommandExecutorType.BOTH)
public class tpCommand extends CustomCommand {

    public tpCommand(String id, String syntax, String description, CommandExecutorType executorType, String permission, CommandGroup group) {
        super(id, syntax, description, executorType, permission, group, false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        //Main.log("PATH: &c" + Main.getInstance().CONFIG.getString(args[2]));

        //Main.USER.language = LanguageManager.getLanguage(args[2]);


        /*
        PlayerManager.getDefaultUser().sendMessage(MessagesKeys.valueOf(args[2]), PlaceholderManager.getPlaceholder(ro.Gabriel.Placeholder.Placeholders.placeholderTest.class), 20);


        SpigotUser a = new BasicPlayer((UUID) null, 0, 0, 0, 0, 0, 0, null, null, null, null, null, null, null){

            @Override
            public String getName() {
                return "a";
            }

            @Override
            public void sendMessage(String message) {
                PlayerManager.getDefaultUser().sendMessage(getName() + " a primit mesajul \" " + message + " \"");
            }
        };
        a.language = LanguageManager.getLanguage("f");
        SpigotUser b = new BasicPlayer((UUID) null, 0, 0, 0, 0, 0, 0, null, null, null, null, null, null, null){

            @Override
            public String getName() {
                return "b";
            }

            @Override
            public void sendMessage(String message) {
                PlayerManager.getDefaultUser().sendMessage(getName() + " a primit mesajul \" " + message + " \"");
            }
        };
        b.language = LanguageManager.getLanguage("a");
        SpigotUser c = new BasicPlayer((UUID) null, 0, 0, 0, 0, 0, 0, null, null, null, null, null, null, null){

            @Override
            public String getName() {
                return "c";
            }

            @Override
            public void sendMessage(String message) {
                PlayerManager.getDefaultUser().sendMessage(getName() + " a primit mesajul \" " + message + " \"");
            }
        };
        c.language = LanguageManager.getLanguage("a");
        SpigotUser d = new BasicPlayer((UUID) null, 0, 0, 0, 0, 0, 0, null, null, null, null, null, null, null){

            @Override
            public String getName() {
                return "d";
            }

            @Override
            public void sendMessage(String message) {
                PlayerManager.getDefaultUser().sendMessage(getName() + " a primit mesajul \" " + message + " \"");
            }
        };
        d.language = LanguageManager.getLanguage("a");
        SpigotUser e = new BasicPlayer((UUID) null, 0, 0, 0, 0, 0, 0, null, null, null, null, null, null, null){

            @Override
            public String getName() {
                return "e";
            }

            @Override
            public void sendMessage(String message) {
                PlayerManager.getDefaultUser().sendMessage(getName() + " a primit mesajul \" " + message + " \"");
            }
        };
        e.language = LanguageManager.getLanguage("a");
        SpigotUser f = new BasicPlayer((UUID) null, 0, 0, 0, 0, 0, 0, null, null, null, null, null, null, null){

            @Override
            public String getName() {
                return "f";
            }

            @Override
            public void sendMessage(String message) {
                PlayerManager.getDefaultUser().sendMessage(getName() + " a primit mesajul \" " + message + " \"");
            }
        };
        f.language = LanguageManager.getLanguage("f");

        List<SpigotUser> users = Arrays.asList(a, b, c, d, e, f);

        //PlayerManager.getDefaultUser().sendMessage(users, MessagesKeys.valueOf(args[2]), PlaceholderManager.getPlaceholder(ro.Gabriel.Placeholder.Placeholders.placeholderTest.class), 20);

        PlayerManager.getDefaultUser().sendMessage(users, MessagesKeys.valueOf(args[2]), PlaceholderManager.getPlaceholder(PlayerPlaceholder.class));
        */

        //Player player = ((Player)sender);



        //TextComponent message = new TextComponent("Mesaj hover");
        //message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Acesta este mesajul hover!").create()));
        //player.spigot().sendMessage(message);

    }
}
