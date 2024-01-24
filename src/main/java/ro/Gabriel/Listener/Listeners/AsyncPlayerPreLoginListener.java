package ro.Gabriel.Listener.Listeners;

import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;

import ro.Gabriel.Repository.Repositories.UserRepository;
import ro.Gabriel.Main.MinigamesDevelopmentKit;
import ro.Gabriel.Listener.CustomListener;
import ro.Gabriel.User.SpigotUser;

public class AsyncPlayerPreLoginListener extends CustomListener<AsyncPlayerPreLoginEvent> {

    @Override
    @EventHandler(priority = EventPriority.MONITOR)
    public void run(AsyncPlayerPreLoginEvent event) {
        try {
            SpigotUser user = this.getPlugin().getUserRepository().getById(event.getUniqueId());// for MKD
            if(user == null) {
                this.disallow(event);
                return;
            }

            MinigamesDevelopmentKit.getMinigames().forEach(plugin -> {
                UserRepository<? extends SpigotUser> repository = plugin.getUserRepository();
                if(repository != null) {
                    if(repository.getById(event.getUniqueId()) == null) {
                        this.disallow(event);
                        return;
                    }
                }
            });// for other mini games
        } catch (Exception ignored) {
            this.disallow(event);
        }
    }

    private void disallow(AsyncPlayerPreLoginEvent event) {
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "player fail load data...");
    }
}