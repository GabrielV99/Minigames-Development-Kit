package ro.Gabriel.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import ro.Gabriel.Main.IPluginInstance;
import ro.Gabriel.Main.Minigame;
import ro.Gabriel.User.MinecraftUser;
import ro.Gabriel.User.SpigotUser;

import java.util.List;

public abstract class BukkitCommandGroup extends Command implements IPluginInstance {

    private final Minigame plugin;

    protected BukkitCommandGroup(Minigame plugin, String name, String description, String usageMessage, List<String> aliases) {
        super(name, description, usageMessage, aliases);
        this.plugin = plugin;
    }

    public abstract boolean execute(MinecraftUser user, String commandLabel, String[] args);

    @Override
    public Minigame getPlugin() {
        return this.plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(sender instanceof Player) {
            SpigotUser user = this.plugin.getUserRepository().findById( ((Player)sender).getUniqueId() );
            if(user != null) {
                return this.execute(user, commandLabel, args);
            }
        }

        if(sender instanceof ConsoleCommandSender) {
            return this.execute(plugin.getDefaultUser(), commandLabel, args);
        }

        return false;
    }
}