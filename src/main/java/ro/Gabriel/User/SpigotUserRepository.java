package ro.Gabriel.User;

import ro.Gabriel.Repository.Repositories.UserRepository;
import ro.Gabriel.Main.Minigame;

import java.util.UUID;

public class SpigotUserRepository extends UserRepository<SpigotUser> {
    public SpigotUserRepository(Minigame plugin) {
        super(plugin);
    }

    @Override
    protected SpigotUser registerEntity(UUID id) {
        /*StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            System.out.println("StackTrace with index: " + i + " is: " + stackTrace[i].toString());
        }
        System.out.println("StackTrace length: " + stackTrace.length);*/
        return new SpigotUser(this.getPlugin(), id);
    }
}