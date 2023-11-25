package ro.Gabriel.Player.BuildBattlePlayer.Arena;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ArenaPlayerImpl extends ArenaPlayer {
	
	private final int selectedSong;
	private final String loadOut;

	private Entity editedEntity;

	public ArenaPlayerImpl(Player player, int selectedSong, String loadOut) {
		this(player.getUniqueId(), selectedSong, loadOut);
	}

	public ArenaPlayerImpl(UUID uuid, int selectedSong, String loadOut) {
		super(uuid);
		this.selectedSong = selectedSong;
		this.loadOut = loadOut;
	}

	@Override
	public int getSelectedSong() {
		return this.selectedSong;
	}

	@Override
	public String getLoadOut() {
		return this.loadOut;
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

	@Override
	public int getSelectedSuit() {
		return this.basicPlayer.getSelectedSuit();
	}

	@Override
	public int getSelectedHat() {
		return this.basicPlayer.getSelectedHat();
	}

	@Override
	public int[] getSongsList() {
		return this.basicPlayer.getSongsList();
	}

	@Override
	public int[] getBackdropPickersList() {
		return this.basicPlayer.getBackdropPickersList();
	}

	@Override
	public int[] getVictoryDancesList() {
		return this.basicPlayer.getVictoryDancesList();
	}

	@Override
	public int[] getSuitsList() {
		return this.basicPlayer.getSuitsList();
	}

	@Override
	public int[] getHatsList() {
		return this.basicPlayer.getHatsList();
	}
}