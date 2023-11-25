package ro.Gabriel.Repository.Impl;

import ro.Gabriel.DatabaseN.Data.Request.DatabaseRequestDataImpl.DatabaseRequestData;
import ro.Gabriel.Player.BuildBattlePlayer.Lobby.LobbyPlayerImpl;
import ro.Gabriel.Player.BuildBattlePlayer.Arena.ArenaPlayerImpl;
import ro.Gabriel.Player.BuildBattlePlayer.BuildBattlePlayer;
import ro.Gabriel.Player.BuildBattlePlayer.BasicPlayer;
import ro.Gabriel.DatabaseN.Data.DataPack;
import ro.Gabriel.BuildBattle.ServerType;
import ro.Gabriel.Repository.Repository;
import ro.Gabriel.BuildBattle.Main;

import java.util.UUID;

public class PlayerRepository extends Repository<UUID, BuildBattlePlayer> {

    DatabaseRequestData requestDataQuery;

    public PlayerRepository(DatabaseRequestData requestDataQuery) {
        this.requestDataQuery = requestDataQuery;
        this.requestDataQuery.primaryKeyRequest(true);
    }

    @Override
    public BuildBattlePlayer registerEntity(UUID id) {
        boolean bungeeCord = Main.isBungeeCord();
        ServerType serverType = Main.getServerType();

        DataPack dataPack = requestDataQuery.build(id.toString());

        BuildBattlePlayer player = new BasicPlayer(id,
                dataPack.getInt("Score"), dataPack.getInt("SuperVotes"),
                dataPack.getInt("SelectedBackdropPicker"), dataPack.getInt("selectedVictoryDance"), dataPack.getInt("selectedSuit"), dataPack.getInt("selectedHat"),
                dataPack.getIntArray("songsList"), dataPack.getIntArray("backdropPickersList"), dataPack.getIntArray("victoryDancesList"), dataPack.getIntArray("suitsList"), dataPack.getIntArray("hatsList"),

                ((bungeeCord && serverType == ServerType.LOBBY) || serverType == ServerType.MULTI_ARENA)
                        ? new LobbyPlayerImpl(id, dataPack.getInt("Coins"), dataPack.getInt("Leaderboard"), dataPack.getInt("SortOIF")) : null,
                ((bungeeCord && serverType == ServerType.ARENA) || serverType == ServerType.MULTI_ARENA)
                        ? new ArenaPlayerImpl(id, dataPack.getInt("SelectedSong"), dataPack.getString("Loadout")) : null
        );

        this.add(player);

        return player;
    }
}