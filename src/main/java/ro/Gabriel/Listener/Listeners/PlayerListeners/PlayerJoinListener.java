package ro.Gabriel.Listener.Listeners.PlayerListeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;
import ro.Gabriel.BuildBattle.Main;
import ro.Gabriel.Listener.CustomListener;
import ro.Gabriel.Player.BuildBattlePlayer.BuildBattlePlayer;
import ro.Gabriel.Player.UserManager;
import ro.Gabriel.Repository.RepositoryManager;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URL;

public class PlayerJoinListener extends CustomListener<PlayerJoinEvent> {

    @Override
    public void run(PlayerJoinEvent event) {
       // Player player = event.getPlayer();

        //Main.USER = new BasicPlayer(player, 0, 0, 0, 0, 0, 0, null, null, null, null, null, null, null);
        //Main.USER.sendMessage(MessagesKeys.JOIN_ARENA);

        /*
        //CraftPlayer craftPlayer = (CraftPlayer) player;
        player.sendMessage("");


        new BukkitRunnable(){

            @Override
            public void run() {
                Main.log("LOCALE: &c" + player.getLocale());

            }
        }.runTaskLater(Main.getInstance(), 15L);


        try {
            Main.log("TARA: &c" + getCountry(new InetSocketAddress("86.120.131.30", 0)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        PacketPlayInChat s;
        PacketPlayInSettings sss;
*/
        BuildBattlePlayer player = UserManager.getUser(event.getPlayer());
        player.sendMessage("&eBun venit pe server&6!");

        //Player player = event.getPlayer();

        //BuildBattlePlayer player1 = RepositoryManager.getPlayerRepository().getById(player.getUniqueId());

        //player1.sendMessage("&2ce faci?");

    }

    public static String getCountry(InetSocketAddress ip) throws Exception {
        URL url = new URL("http://ip-api.com/json/" + ip.getHostName());
        BufferedReader stream = new BufferedReader(new InputStreamReader(
                url.openStream()));
        StringBuilder entirePage = new StringBuilder();
        String inputLine;
        while ((inputLine = stream.readLine()) != null)
            entirePage.append(inputLine);
        stream.close();
        if(!(entirePage.toString().contains("\"country\":\"")))
            return null;
        return entirePage.toString().split("\"country\":\"")[1].split("\",")[0];
    }
}
