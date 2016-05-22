package tbsc.techy.api.pipes.impl;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import tbsc.techy.api.pipes.IPipePacket;

/**
 * Implementation for item pipes, transfers items in the form of an {@link ItemStack}
 *
 * Created by tbsc on 5/17/16.
 */
public class ItemPipePacket implements IPipePacket<ItemStack> {

    private BlockPos origin, destination;
    private ItemStack routedStack;

    private ItemPipePacket(BlockPos origin, BlockPos destination, ItemStack routedStack) {
        this.origin = origin;
        this.destination = destination;
        this.routedStack = routedStack;
    }

    @Override
    public BlockPos getDestination() {
        return destination;
    }

    @Override
    public BlockPos getOrigin() {
        return origin;
    }

    @Override
    public ItemStack getPacketContents() {
        return routedStack;
    }

}
