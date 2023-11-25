package ro.Gabriel.Player.BuildBattlePlayer.Lobby;

import ro.Gabriel.Player.BuildBattlePlayer.Arena.ArenaPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LobbyPlayerImpl extends LobbyPlayer {
	
	private final int coins, leaderboard, sortOIF;
	
	public LobbyPlayerImpl(Player player, int coins, int leaderboard, int sortOIF) {
		this(player.getUniqueId(), coins, leaderboard, sortOIF);


	}

	public LobbyPlayerImpl(UUID uuid, int coins, int leaderboard, int sortOIF) {
		super(uuid);
		this.coins = coins;
		this.leaderboard = leaderboard;
		this.sortOIF = sortOIF;
	}
	
	@Override
	public ArenaPlayer getArenaPlayer() {
		return this.basicPlayer.getArenaPlayer();
	}

	@Override
	public int getCoins() {
		return this.coins;
	}

	@Override
	public int getLeaderboard() {
		return this.leaderboard;
	}

	@Override
	public int getSortOIF() {
		return this.sortOIF;
	}
}