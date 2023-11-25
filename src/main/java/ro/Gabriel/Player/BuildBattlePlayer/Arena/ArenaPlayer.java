package ro.Gabriel.Player.BuildBattlePlayer.Arena;

import ro.Gabriel.Player.BuildBattlePlayer.Lobby.LobbyPlayer;
import ro.Gabriel.Player.BuildBattlePlayer.BuildBattlePlayer;

import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class ArenaPlayer extends BuildBattlePlayer {

	protected BuildBattlePlayer basicPlayer;

	public ArenaPlayer(Player player) {
		super(player);
	}

	public ArenaPlayer(UUID uuid) {
		super(uuid);
	}

	public abstract int getSelectedSong();

	public abstract String getLoadOut();

	public BuildBattlePlayer getBasicPlayer() {
		return this.basicPlayer;
	}

	public void setBasicPlayer(BuildBattlePlayer basicPlayer) {
		this.basicPlayer = basicPlayer;
	}

	@Override
	public LobbyPlayer getLobbyPlayer() {
		return this.basicPlayer.getLobbyPlayer();
	}

	@Override
	public ArenaPlayer getArenaPlayer() {
		return this;
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