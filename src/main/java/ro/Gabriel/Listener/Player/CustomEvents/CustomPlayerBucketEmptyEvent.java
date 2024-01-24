package ro.Gabriel.Listener.Player.CustomEvents;

import ro.Gabriel.User.SpigotUser;

import org.bukkit.inventory.ItemStack;
import org.bukkit.event.HandlerList;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.Material;

public class CustomPlayerBucketEmptyEvent<PlayerType extends SpigotUser> extends CustomPlayerBucketEvent<PlayerType> {
    private static final HandlerList handlers = new HandlerList();

    public CustomPlayerBucketEmptyEvent(Player player, PlayerType spigotUser, ItemStack itemStack, Block blockClicked, BlockFace blockFace, Material bucket) {
        super(player, spigotUser, itemStack, blockClicked, blockFace, bucket);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}