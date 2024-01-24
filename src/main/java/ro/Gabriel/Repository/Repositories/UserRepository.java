package ro.Gabriel.Repository.Repositories;

import ro.Gabriel.Main.Minigame;
import ro.Gabriel.Repository.Repository;
import ro.Gabriel.User.SpigotUser;

import java.util.UUID;

public abstract class UserRepository<UserType extends SpigotUser> extends Repository<UUID, UserType> {

    public UserRepository(Minigame plugin) {
        super(plugin);
    }

    @Override
    public final void remove(UUID id) {
        SpigotUser user = this.findById(id);
        if(user != null) {
            super.remove(id);
            user.getPlayer().kickPlayer("You have been removed from server!");
        }
    }
}