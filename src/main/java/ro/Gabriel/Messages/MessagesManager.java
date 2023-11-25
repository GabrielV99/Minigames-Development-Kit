package ro.Gabriel.Messages;

import ro.Gabriel.Managers.Annotations.ManagerClass;
import ro.Gabriel.Storage.DataStorage.DataStorage;

import java.util.HashMap;

@ManagerClass(bungee = true, lobby = true, arena = true)
public class MessagesManager {

    private static volatile MessagesManager INSTANCE;

    private HashMap<String, HoverText> hoverTexts;

    public static MessagesManager getInstance() {
        synchronized (MessagesManager.class) {
            if(INSTANCE == null) {
                return (INSTANCE = new MessagesManager());
            }
            return INSTANCE;
        }
    }

    public static void load() {
        MessagesManager instance = getInstance();
        instance.hoverTexts = new HashMap<>();

        DataStorage hovers = DataStorage.getStorage("hover messages", true);

        hovers.getSection("").getKeys(false).forEach(hover -> registerHoverText(hover, hovers.getString("text-path"), hovers.getString("hover-text-path"), hovers.getString("command"), hovers.getBoolean("execute-command")));
    }

    public static void registerHoverText(String id, String text, String hoverText, String command, boolean executeCommand) {
        getInstance().hoverTexts.put(id, new HoverText(text, hoverText, command, executeCommand));
    }

    public static HoverText getHoverText(String id) {
        return getInstance().hoverTexts.get(id);
    }

    public static void sendMessage(MessageSender sender, String message) {
        sender.sendMessage(MessageUtils.colorString(message));
    }
}