package ro.Gabriel.Player.BuildBattlePlayer;

import ro.Gabriel.Player.BuildBattlePlayer.Lobby.LobbyPlayer;
import ro.Gabriel.Player.BuildBattlePlayer.Arena.ArenaPlayer;
import ro.Gabriel.Player.SpigotUser;

import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class BuildBattlePlayer extends SpigotUser {

	public BuildBattlePlayer(Player player) {
		super(player);

		if(this.getLobbyPlayer() != null) {
			this.getLobbyPlayer().setBasicPlayer(this);
		}

		if(this.getArenaPlayer() != null) {
			this.getArenaPlayer().setBasicPlayer(this);
		}
	}

	public BuildBattlePlayer(UUID uuid) {
		super(uuid);
	}

	public abstract LobbyPlayer getLobbyPlayer();
	public abstract ArenaPlayer getArenaPlayer();

	public abstract int getScore();

	public abstract int getSuperVotes();

	public abstract int getSelectedBackdropPicker();

	public abstract int getSelectedVictoryDance();

	public abstract int getSelectedSuit();

	public abstract int getSelectedHat();

	public abstract int[] getSongsList();

	public abstract int[] getBackdropPickersList();

	public abstract int[] getVictoryDancesList();

	public abstract int[] getSuitsList();

	public abstract int[] getHatsList();
}