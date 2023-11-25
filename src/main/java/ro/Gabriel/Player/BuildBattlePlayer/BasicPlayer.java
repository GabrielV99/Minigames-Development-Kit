package ro.Gabriel.Player.BuildBattlePlayer;

import ro.Gabriel.Player.BuildBattlePlayer.Lobby.LobbyPlayer;
import ro.Gabriel.Player.BuildBattlePlayer.Arena.ArenaPlayer;

import org.bukkit.entity.Player;

import java.util.UUID;

public class BasicPlayer extends BuildBattlePlayer {

    private final LobbyPlayer lobbyPlayer;
    private final ArenaPlayer arenaPlayer;

	int score, superVotes;
	
	int selectedBackdropPicker, selectedVictoryDance, selectedSuit, selectedHat;

    int[] songsList, backdropPickersList, victoryDancesList, suitsList, hatsList;

    public BasicPlayer(Player player,
                       int score, int superVotes,
                       int selectedBackdropPicker, int selectedVictoryDance, int selectedSuit, int selectedHat,
                       int[] songsList, int[] backdropPickersList, int[] victoryDancesList, int[] suitsList, int[] hatsList,
                       LobbyPlayer lobbyPlayer, ArenaPlayer arenaPlayer) {
        this(player.getUniqueId(), score, superVotes, selectedBackdropPicker, selectedVictoryDance, selectedSuit, selectedHat, songsList, backdropPickersList, victoryDancesList, suitsList, hatsList, lobbyPlayer, arenaPlayer);
    }

    public BasicPlayer(UUID uuid,
                       int score, int superVotes,
                       int selectedBackdropPicker, int selectedVictoryDance, int selectedSuit, int selectedHat,
                       int[] songsList, int[] backdropPickersList, int[] victoryDancesList, int[] suitsList, int[] hatsList,
                       LobbyPlayer lobbyPlayer, ArenaPlayer arenaPlayer) {
        super(uuid);

        this.score = score;
        this.superVotes = superVotes;
        this.selectedBackdropPicker = selectedBackdropPicker;
        this.selectedVictoryDance = selectedVictoryDance;
        this.selectedSuit = selectedSuit;
        this.selectedHat = selectedHat;
        this.songsList = songsList;
        this.backdropPickersList = backdropPickersList;
        this.victoryDancesList = victoryDancesList;
        this.suitsList = suitsList;
        this.hatsList = hatsList;

        this.lobbyPlayer = lobbyPlayer;
        this.arenaPlayer = arenaPlayer;
    }

    @Override
    public LobbyPlayer getLobbyPlayer() {
        return this.lobbyPlayer != null ? this.lobbyPlayer : (LobbyPlayer) ((BuildBattlePlayer)this);
    }

    @Override
    public ArenaPlayer getArenaPlayer() {
        return this.arenaPlayer != null ? this.arenaPlayer : (ArenaPlayer) ((BuildBattlePlayer)this);
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public int getSuperVotes() {
        return this.superVotes;
    }

    @Override
    public int getSelectedBackdropPicker() {
        return this.selectedBackdropPicker;
    }

    @Override
    public int getSelectedVictoryDance() {
        return this.selectedVictoryDance;
    }

    @Override
    public int getSelectedSuit() {
        return this.selectedSuit;
    }

    @Override
    public int getSelectedHat() {
        return this.selectedHat;
    }

    @Override
    public int[] getSongsList() {
        return this.songsList;
    }

    @Override
    public int[] getBackdropPickersList() {
        return this.backdropPickersList;
    }

    @Override
    public int[] getVictoryDancesList() {
        return this.victoryDancesList;
    }

    @Override
    public int[] getSuitsList() {
        return this.suitsList;
    }

    @Override
    public int[] getHatsList() {
        return this.hatsList;
    }
}