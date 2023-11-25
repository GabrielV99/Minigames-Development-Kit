package ro.Gabriel.Player;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import ro.Gabriel.BuildBattle.Main;
import ro.Gabriel.Language.Language;
import ro.Gabriel.Language.LanguageManager;
import ro.Gabriel.Managers.Annotations.ManagerClass;
import ro.Gabriel.Messages.MessageUtils;
import ro.Gabriel.Player.BuildBattlePlayer.BuildBattlePlayer;
import ro.Gabriel.Repository.Repository;
import ro.Gabriel.Repository.RepositoryManager;

import java.util.UUID;

@ManagerClass(bungee = true, lobby = true, arena = true)
public class UserManager {

    private static volatile UserManager INSTANCE;

    private final SpigotUser defaultSpigotUser;

    private Repository<UUID, BuildBattlePlayer> playersRepository;

    private UserManager() {
        this.defaultSpigotUser = new SpigotUser((UUID) null) {
            @Override
            public String getName() {
                return "Console";
            }

            @Override
            public Player getPlayer() {
                //this.sendMessage(LanguageManager.getDefaultLanguage());
                return null;
            }

            @Override
            public void sendMessage(String message) {
                Main.getInstance().getServer().getConsoleSender().sendMessage(Main.getPrefix() + MessageUtils.colorString(message));
            }

            @Override
            public boolean hasPermission(String permission) {
                return true;
            }

            @Override
            public Language getLanguage() {
                return LanguageManager.getDefaultLanguage();
            }

        };
    }

    public static void load() {
        //getInstance().playersRepository = new PlayerRepository(EntityDataService.getEntityDataService(""));

        Main.log("initialize player manager");
    }

    public static SpigotUser getDefaultUser() {
        return getInstance().defaultSpigotUser;
    }

    public static boolean isDefaultUser(SpigotUser user) {
        return user == getDefaultUser();
    }

    public static SpigotUser getUser(CommandSender sender) {
        return sender instanceof ConsoleCommandSender ? getDefaultUser() : null;
    }

    public static BuildBattlePlayer getUser(UUID uuid) {
        return RepositoryManager.getPlayerRepository().getById(uuid);
    }

    public static BuildBattlePlayer getUser(Player player) {
        return getUser(player.getUniqueId());
    }

    private static UserManager getInstance() {
        if (INSTANCE == null) {
            synchronized (UserManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserManager();
                }
            }
        }
        return INSTANCE;
    }
}
