package tbsc.techy.tile.pipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import tbsc.techy.api.PositionUtil;
import tbsc.techy.api.pipes.IPipeRoutable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base tile entity for pipes.
 *
 * Created by tbsc on 5/9/16.
 */
public abstract class TilePipeBase extends TileEntity implements ITickable {

    /**
     * Map for checking what is going to be routed.
     * The value is the amount of ticks elapsed to route.
     */
    public Map<IPipeRoutable, Integer> toBeRouted = new HashMap<>();
    /**
     * Map that contains all of the pipes in the network by position, and
     * (sq) distance from this pipe
     */
    public Map<BlockPos, Integer> pipeNetwork = new HashMap<>();
    /**
     * Delay between object entering the pipe, until exiting, in ticks.
     */
    public int transferDelay;

    /**
     * Used to know the type of class a block needs to be a sub type of to connect to
     */
    public Class<?> connectiblePipeClass;

    protected TilePipeBase(int transferDelay, Class<?> connectiblePipeClass) {
        this.transferDelay = transferDelay;
        this.connectiblePipeClass = connectiblePipeClass;
    }

    @Override
    public void update() {
        for (IPipeRoutable routable : toBeRouted.keySet()) {
            int ticksElapsed = toBeRouted.get(routable);
            ++ticksElapsed;
            if (ticksElapsed >= transferDelay) {
                if (!PositionUtil.isNeighbor(pos, routable.getDestination())) {
                    sendPipeRoutable(routable, PipePathFinder.findNextPipe(pos, routable.getDestination()));
                } else {
                    toBeRouted.remove(routable);
                    insertRoutable(routable);
                }
            }
        }

        attemptToExtract();
    }

    /**
     * Used by the pipe to send stuff to the next pipe
     * @param routable what to send
     * @param receiver where to send, NOTE: it should be the adjacent pipe!
     */
    protected abstract void sendPipeRoutable(IPipeRoutable routable, BlockPos receiver);

    /**
     * Used by other pipes to make pipes receive stuff
     * @param routable what to receive
     * @param sender who sent it, NOTE: it should be the adjacent pipe!
     */
    protected abstract void receivePipeRoutable(IPipeRoutable routable, BlockPos sender);

    /**
     * This method is called every tick, and what it does is attempt to extract from nearby
     * inventories stuff. Since this is a base class, I need the implementation to extract
     * whatever it needs to.
     */
    protected abstract void attemptToExtract();

    /**
     * The routable has reached its destination, so let the pipe insert it into the destination.
     * @param routable the routable
     */
    protected abstract void insertRoutable(IPipeRoutable routable);

    /**
     * Called by the block itself when a neighbor block change has happened, and it notifies all of the other
     * pipes in the network that this pipe was added.
     * @param addedPipe position of the added pipe
     */
    public void forwardPipeInNetwork(BlockPos addedPipe) {
        this.pipeNetwork.put(addedPipe, (int) pos.distanceSq(addedPipe));
        notifyNeighborsOfNetworkChange(addedPipe);
    }

    /**
     * Notifies (pipe!) neighbors of this pipe of the added pipe
     * @param addedPipe position of the added pipe
     */
    protected void notifyNeighborsOfNetworkChange(BlockPos addedPipe) {
        // Loops through the neighbor pipes, and notifies them of the added pipe.
        // Only if they don't already know about it let it know
        for (BlockPos pos : PositionUtil.getApplicableNeighbors(worldObj, this.pos, connectiblePipeClass)) {
            TilePipeBase pipe = (TilePipeBase) worldObj.getTileEntity(pos);
            if (!pipe.hasPipeInNetwork(addedPipe)) {
                pipe.forwardPipeInNetwork(addedPipe);
            }
        }
    }

    /**
     * Checks if the network (this pipe knows) already has the added pipe
     * @param pos
     * @return
     */
    public boolean hasPipeInNetwork(BlockPos pos) {
        return pipeNetwork.containsKey(pos);
    }

    /**
     * Looks for any possible destination, and returns it. If not found, returns null.
     * @return possible destination
     */
    protected BlockPos findDestination() {
        List<BlockPos> possibleDestinations = new ArrayList<>();
        for (BlockPos networkPipe : pipeNetwork.keySet()) {
            for (BlockPos validNeighbor : PositionUtil.getTileApplicableNeighbors(worldObj, networkPipe, IInventory.class)) {
                if (canInsertTo(validNeighbor)) {
                    possibleDestinations.add(validNeighbor);
                }
            }
        }
        return PositionUtil.getClosest(pos, possibleDestinations);
    }

    /**
     * Checks if in the position given is a connectible pipe.
     * @param block is a pipe
     * @return if it is a pipe
     */
    protected abstract boolean isConnectiblePipe(BlockPos block);

    /**
     * Checks if *any* neighbors are connectible, and therefore can transfer stuff through them
     * @return is any neighbor a pipe
     */
    protected abstract boolean isAnyNeighborConnectiblePipe(BlockPos pos);

    /**
     * Checks if it can insert whatever the pipe inserts to the block given.
     * @param pos of the block
     * @return if it can insert stuff to it
     */
    protected abstract boolean canInsertTo(BlockPos pos);

}
