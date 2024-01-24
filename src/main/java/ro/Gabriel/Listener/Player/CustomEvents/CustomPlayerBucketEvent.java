package ro.Gabriel.Listener.Player.CustomEvents;

import ro.Gabriel.Listener.Player.CustomPlayerEvent;
import ro.Gabriel.User.SpigotUser;

import org.bukkit.inventory.ItemStack;
import org.bukkit.event.Cancellable;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.Material;

public abstract class CustomPlayerBucketEvent<PlayerType extends SpigotUser> extends CustomPlayerEvent<PlayerType> implements Cancellable {

    private ItemStack itemStack;
    private boolean cancelled = false;
    private final Block blockClicked;
    private final BlockFace blockFace;
    private final Material bucket;

    public CustomPlayerBucketEvent(Player player, PlayerType spigotUser, ItemStack itemStack, Block blockClicked, BlockFace blockFace, Material bucket) {
        super(player, spigotUser);

        this.itemStack = itemStack;
        this.blockClicked = blockClicked;
        this.blockFace = blockFace;
        this.bucket = bucket;
    }

    /**
     * Returns the bucket used in this event
     *
     * @return the used bucket
     */
    public Material getBucket() {
        return bucket;
    }

    /**
     * Get the resulting item in hand after the bucket event
     *
     * @return Itemstack hold in hand after the event.
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Set the item in hand after the event
     *
     * @param itemStack the new held itemstack after the bucket event.
     */
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Return the block clicked
     *
     * @return the clicked block
     */
    public Block getBlockClicked() {
        return blockClicked;
    }

    /**
     * Get the face on the clicked block
     *
     * @return the clicked face
     */
    public BlockFace getBlockFace() {
        return blockFace;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}