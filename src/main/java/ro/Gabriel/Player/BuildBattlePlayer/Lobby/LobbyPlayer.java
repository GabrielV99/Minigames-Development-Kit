package ro.Gabriel.Player.BuildBattlePlayer.Lobby;

import org.bukkit.entity.Player;
import ro.Gabriel.Player.BuildBattlePlayer.Arena.ArenaPlayer;
import ro.Gabriel.Player.BuildBattlePlayer.BuildBattlePlayer;

import java.util.UUID;

public abstract class LobbyPlayer extends BuildBattlePlayer {

	protected BuildBattlePlayer basicPlayer;

	public LobbyPlayer(Player player) {
		super(player);
	}

	public LobbyPlayer(UUID uuid) {
		super(uuid);
	}

	public abstract int getCoins();

	public abstract int getLeaderboard();

	public abstract int getSortOIF();

	public BuildBattlePlayer getBasicPlayer() {
		return this.basicPlayer;
	}

	public void setBasicPlayer(BuildBattlePlayer basicPlayer) {
		this.basicPlayer = basicPlayer;
	}

	@Override
	public LobbyPlayer getLobbyPlayer() {
		return this;
	}

	@Override
	public ArenaPlayer getArenaPlayer() {
		return this.basicPlayer.getArenaPlayer();
	}

	@Override
	public int getScore() {
		return this.basicPlayer.getScore();
	}

	@Override
	public int getSuperVotes() {
		return this.basicPlayer.getSuperVotes();
	}

	@Override
	public int getSelectedBackdropPicker() {
		return this.basicPlayer.getSelectedBackdropPicker();
	}

	@Override
	public int getSelectedVictoryDance() {
		return this.basicPlayer.getSelectedVictoryDance();
	}

	public int getSelectedSuit() {
		return this.basicPlayer.getSelectedSuit();
	}

	public int getSelectedHat() {
		return this.basicPlayer.getSelectedHat();
	}

	public int[] getSongsList() {
		return this.basicPlayer.getSongsList();
	}

	public int[] getBackdropPickersList() {
		return this.basicPlayer.getBackdropPickersList();
	}

	public int[] getVictoryDancesList() {
		return this.basicPlayer.getVictoryDancesList();
	}

	public int[] getSuitsList() {
		return this.basicPlayer.getSuitsList();
	}

	public int[] getHatsList() {
		return this.basicPlayer.getHatsList();
	}
}