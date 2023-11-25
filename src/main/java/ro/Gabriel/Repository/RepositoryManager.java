package ro.Gabriel.Repository;

import ro.Gabriel.BuildBattle.ConfigManager;
import ro.Gabriel.DatabaseN.Structures.PlayersDatabaseStructure;
import ro.Gabriel.Managers.Annotations.ManagerClass;
import ro.Gabriel.Player.BuildBattlePlayer.BuildBattlePlayer;
import ro.Gabriel.Repository.Impl.PlayerRepository;

import java.util.UUID;

@ManagerClass(bungee = true, lobby = true, arena = true)
public class RepositoryManager {

    private static volatile RepositoryManager INSTANCE;

    private Repository<UUID, BuildBattlePlayer> playerRepository;

    public static void load() {
        RepositoryManager instance = getInstance();
        instance.playerRepository = new PlayerRepository(
                ConfigManager.getDatabase()
                        .requestData(PlayersDatabaseStructure.class).select().from(PlayersDatabaseStructure.class).where(PlayersDatabaseStructure.UUID).isEquals()
        );
    }

    private static RepositoryManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ConfigManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RepositoryManager();
                }
            }
        }
        return INSTANCE;
    }

    public static Repository<UUID, BuildBattlePlayer> getPlayerRepository() {
        return getInstance().playerRepository;
    }
}
